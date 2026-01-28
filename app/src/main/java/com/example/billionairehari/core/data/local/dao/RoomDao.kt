package com.example.billionairehari.core.data.local.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.model.TenantRentRecord
import com.example.billionairehari.screens.TenantRentDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT name FROM sqlite_master WHERE type='table'")
    suspend fun getAllTables(): List<String>

    data class RoomIdAndName(
        val id:String,
        val name:String
    )
    @Query("SELECT id,name FROM rooms WHERE owner_id = :ownerId")
    fun getRoomNames(ownerId:String): Flow<List<RoomIdAndName>>

    @Upsert()
    suspend fun insertRoom(room: Room)

    @Query("SELECT * FROM rooms WHERE owner_id = :ownerId AND id = :roomId")
    suspend fun getRoom(ownerId:String,roomId:String): Room

    data class RoomWithTenantAndDueCount(
        val id:String = "",
        val name:String = "",
        val images:String = "",
        val bed_count:Int = 0,
        val rent_price:Int = 0,
        val deposit:Int = 0,
        val features:String = "",
        val due_day:Int= 0,
        val location:String = "",
        val owner_id:String = "",
        val paidCount:Int = 0,
        val tenantCount:Int = 0
    )

    @Query("""
        SELECT 
        r.id AS id,
        r.name AS name,
        r.images AS images,
        r.bed_count AS bed_count,
        r.rent_price AS rent_price,
        r.deposit AS deposit,
        r.features AS features,
        r.due_day AS due_day,
        r.location AS location,
        r.owner_id AS owner_id,
        COUNT(DISTINCT p.tenant_id) AS paidCount,
        COUNT(DISTINCT t.id) AS tenantCount
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        LEFT JOIN payments p ON 
            p.room_id = r.id 
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now') 
            AND p.tenant_id = t.id
        WHERE r.id = :roomId AND r.owner_id = :ownerId
    """)
    fun getRoomFlow(roomId:String,ownerId:String): Flow<RoomWithTenantAndDueCount>

    data class RoomTenantDetails(
        val id:String,
        val name:String,
        val image:String,
        val phoneNumber:String,
        val paymentStatus:Int,
        val amountToPay:Int
    )
    @Query("""
        SELECT
        t.id,
        t.name,
        t.image,
        t.phone_number AS phoneNumber,
        CASE 
            WHEN p.id IS NOT NULL AND SUM(p.amount) >= r.rent_price THEN 1 
            WHEN p.id IS NOT NULL AND SUM(p.amount) > 0 THEN 2
            ELSE 0 
        END AS paymentStatus,
        CASE
            WHEN r.rent_price - SUM(COALESCE(p.amount,0)) > 0 THEN r.rent_price - SUM(p.amount)
            ELSE 0
        END AS amountToPay
        FROM rooms r
        JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        LEFT JOIN payments p ON p.tenant_id = t.id AND p.due_date = strftime('%Y-%m','now') || '-' || r.due_day
        WHERE r.owner_id = :ownerId AND r.id = :roomId
        GROUP BY t.id
    """)
    fun getRoomTenantDetails(ownerId:String,roomId:String): Flow<List<RoomTenantDetails>>

    @Query("SELECT * FROM rooms")
    suspend fun getRooms(): List<Room>

    data class RoomCard(
        val id:String,
        val name:String,
        val bed_count:Int,
        val due_day:Int,
        val tenant_count:Int,
        val not_paid:Int
    )

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_day,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name,r.bed_count,r.due_day;
        """)
    fun getRoomsCardFlow(ownerId:String) : Flow<List<RoomCard>>

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_day,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name,r.bed_count,r.due_day;
    """)
    suspend fun getRoomsCard(ownerId:String) : List<RoomCard>

    @Query("""
        SELECT
        r.id,r.name,r.bed_count,r.due_day,
        COUNT(DISTINCT t.id) AS tenant_count,
        COUNT(DISTINCT CASE WHEN p.id IS NULL THEN t.id END) AS not_paid
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        LEFT JOIN payments p ON p.payment_date = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
            AND (r.name LIKE '%' || :query)
        GROUP BY r.id,r.name,r.bed_count,r.due_day
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
        val id:String,
        val name:String,
        val available_beds:Int
    )

    @Query("""
        SELECT
        r.id,
        r.name,
        r.bed_count - COALESCE(COUNT(t.id),0) as available_beds
        FROM rooms r
        LEFT JOIN tenants t ON t.room_id = r.id AND t.is_active = true
        WHERE r.owner_id = :ownerId
        GROUP BY r.id,r.name
        ORDER BY r.name ASC
    """)
    fun getRoomNameAndTenantCount(ownerId:String): Flow<List<RoomNameAndTenantCount>>
}