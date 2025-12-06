package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert
    suspend fun insertRoom(room: Room)

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
    fun getRoomsFlow(ownerId:String) : Flow<List<RoomCard>>
}