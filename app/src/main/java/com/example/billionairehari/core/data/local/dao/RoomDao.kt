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

    @Query("""
        SELECT 
            r.id,
            r.name,
            r.bed_count,
            r.due_date,
            COALESCE (tnp.not_paid,0) AS not_paid,
            COALESCE (count_tenant,0) AS tenant_count
        FROM rooms WHERE owner_id = :ownerId
        LEFT JOIN (
            SELECT t.room_id
            FROM tenants t
            WHERE r.id = t.room_id
        ) AS count_tenant
            ON count_tenant.room_id = r.id
        LEFT JOIN (
            SELECT
            t.room_id,
            COUNT(*) AS not_paid
            FROM tenants t
            AND NOT EXISTS (
                SELECT 1
                FROM payment p
                WHERE strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
            )
        ) AS tnp ON
            tnp.room_id = r.id
        WHERE r.owner_id = :ownerId
        """)
    fun getRoomsFlow(ownerId:String) : Flow<List<Room>>

    @Query("SELECT * FROM rooms WHERE owner_id = :ownerId")
    suspend fun getRooms(ownerId:String) : List<Room>
}