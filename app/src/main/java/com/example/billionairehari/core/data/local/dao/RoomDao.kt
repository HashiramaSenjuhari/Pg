package com.example.billionairehari.core.data.local.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT name FROM sqlite_master WHERE type='table'")
    suspend fun getAllTables(): List<String>

    @Query("SELECT name FROM rooms WHERE owner_id = :ownerId")
    fun getRoomNames(ownerId:String): Flow<List<String>>
    @Insert()
    suspend fun insertRoom(room: Room)

    data class RoomWithTenantAndDueCount(
        val id:String,
        val name:String,
        val images:String,
        val bed_count:Int,
        val rent_price:Int,
        val deposit:Int,
        val features:String,
        val due_date:String,
        val location:String,
        val owner_id:String,
        val dueCount:Int,
        val tenantCount:Int
    )

    @Query("""
        SELECT 
        COALESCE(r.id,'Unknown ID') AS id,
        COALESCE(r.name,'Unknown Room') AS name,
        COALESCE(r.images,'') AS images,
        COALESCE(r.bed_count,0) AS bed_count,
        COALESCE(r.rent_price,0) AS rent_price,
        COALESCE(r.deposit,0) AS deposit,
        COALESCE(r.features,'') AS features,
        COALESCE(r.due_date,'') AS due_date,
        COALESCE(r.location,'No Location') AS location,
        COALESCE(r.owner_id,'Unknown') AS owner_id,
        COALESCE(COUNT(p.id),0) AS dueCount,
        COALESCE(COUNT(t.id),0) AS tenantCount
        FROM rooms r
        LEFT JOIN payments p ON p.room_id = r.id
        LEFT JOIN tenants t ON t.room_id = r.id
        WHERE r.id = :roomId AND r.owner_id = :ownerId
    """)
    fun getRoomFlow(roomId:String,ownerId:String): Flow<RoomWithTenantAndDueCount>

    data class RoomTenantDetails(
        val id:String,
        val name:String,
        val phoneNumber:String,
        val paymentStatus:Int
    )
//    @Query("""
//        SELECT
//        t.id,
//        t.name,
//        t.phone_number AS phoneNumber,
//        CASE WHEN p.id IS NOT NULL THEN 1 ELSE 0 END AS paymentStatus
//        FROM rooms r
//        JOIN tenants t ON t.room_id = r.id
//        LEFT JOIN payments p ON p.tenant_id = t.id
//        WHERE r.owner_id = :ownerId AND r.id = :roomId
//        GROUP BY t.id
//    """)
//    fun getRoomTenantDetails(ownerId:String,roomId:String): Flow<List<RoomTenantDetails>>

    @Query("SELECT * FROM rooms")
    suspend fun getRooms(): List<Room>

    data class RoomCard(
        val id:String,
        val name:String,
        val bed_count:Int,
        val due_date:Long,
        val tenant_count:Int,
        val not_paid:Int
    )

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_date,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name,r.bed_count,r.due_date;
        """)
    fun getRoomsCardFlow(ownerId:String) : Flow<List<RoomCard>>

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_date,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name,r.bed_count,r.due_date;
    """)
    suspend fun getRoomsCard(ownerId:String) : List<RoomCard>

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_date,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id
        LEFT JOIN payments p ON p.payment_date = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
            AND (r.name LIKE '%' || :query)
        GROUP BY r.id,r.name,r.bed_count,r.due_date
    """)
    fun searchRooms(ownerId:String,query:String): Flow<List<RoomCard>>

    @Query("""
        DELETE FROM rooms WHERE id = :roomId AND owner_id = :ownerId
    """)
    suspend fun deleteRoom(ownerId:String,roomId:String)

    @Query("""
        DELETE FROM rooms
    """)
    suspend fun deleteRooms()

    data class RoomNameAndTenantCount(
        val name:String,
        val available_beds:Int
    )

    @Query("""
        SELECT
        r.name,
        r.bed_count - COALESCE(COUNT(t.id),0) as available_beds
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name
        ORDER BY r.name ASC
    """)
    fun getRoomNameAndTenantCount(ownerId:String): Flow<List<RoomNameAndTenantCount>>
}