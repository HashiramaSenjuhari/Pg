package com.example.billionairehari.core.data.local.dao

import android.health.connect.datatypes.units.Percentage
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.core.data.local.entity.PaymentStatus
import com.example.billionairehari.core.data.local.entity.PaymentType
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert
    suspend fun insertPayment(payment: Payment)

    // ###############################################################################################
    // Revenue Board
    // ###############################################################################################
    data class Revenue(val revenue:Int)
    @Query("""
        SELECT SUM(p.amount) AS revenue
        FROM payments p
        WHERE
            strftime('%Y-%m-%d',payment_date) = strftime('%Y-%m','now') || '-' || strftime('%d',payment_date)
            AND p.owner_id = :ownerId
    """)
    fun getTotalRevenue(ownerId:String): Flow<Revenue>


    @Query("""
        SELECT SUM(amount) AS revenue
         FROM payments
         WHERE 
            strftime('%Y-%m',payment_date) >= :startDate 
            AND strftime('%Y-%m',payment_date) < :endDate
            AND owner_id = :ownerId
    """)
    fun getLastNTotalRevenue(ownerId:String,startDate:String,endDate:String): Flow<Revenue>

    @Query("""
        SELECT SUM(amount) AS revenue
        FROM payments
        WHERE
            strftime('%Y-%m-%d',payment_date) = strftime('%Y-%m','now') || '-' || strftime('%d',payment_date)
            AND owner_id = :ownerId
    """)
    fun getCurrentTotalRevenue(ownerId:String): Flow<Revenue>

    @Query("""
        SELECT SUM(amount) AS revenue
        FROM payments
        WHERE
            payment_date BETWEEN :startDate AND :endDate
            AND owner_id = :ownerId
    """)
    fun getRangeTotalRevene(ownerId:String,startDate:String,endDate:String): Flow<Revenue>
    // ###############################################################################################

    data class Percentag(
        val prev_total:Int,
        val total: Float
    )

    @Query("""
        SELECT
        SUM(CASE WHEN strftime('%Y-%m',payment_date) = strftime('%Y-%m','now','-1 month') THEN amount END) AS prev_total,
        CASE 
            WHEN
                SUM(CASE WHEN strftime('%Y-%m',payment_date) = strftime('%Y-%m','now','-1 month') THEN amount END) > 0
                THEN 
                ROUND(
                    ((SUM(CASE WHEN strftime('%Y-%m',payment_date) = strftime('%Y-%m','now') THEN amount END) /
                    SUM(CASE WHEN strftime('%Y-%m',payment_date) = strftime('%Y-%m','now','-1 month') THEN amount END))
                    - 1) * 100
                ,1)
            ELSE
                0.0
        END AS total
        FROM payments
        WHERE
            owner_id = :ownerId
    """)
    fun getPercentage(ownerId:String):Flow<Percentag>

    data class PaymentCard(
        val id:String,
        val tenantName:String,
        val roomName:String,
        val dueDate:String,
        val paymentDate:String,
        val amount:Int
    )

    @Query("""
        SELECT
            p.id,
            t.name AS tenantName,
            r.name AS roomName,
            p.payment_date AS paymentDate,
            p.due_date AS dueDate,
            p.amount
        FROM payments p
        LEFT JOIN (
            SELECT id,name,room_id FROM tenants
        ) AS t ON t.id = p.tenant_id
        LEFT JOIN (
            SELECT id,name FROM rooms
        ) AS r ON r.id = t.room_id
        WHERE p.owner_id = :ownerId
        ORDER BY p.created_at DESC
    """)
    fun getPaymentHistory(ownerId:String): Flow<List<PaymentCard>>

    @Query("""
        SELECT
            p.id,
            t.name AS tenantName,
            r.name AS roomName,
            p.payment_date AS paymentDate,
            p.due_date AS dueDate,
            p.amount
        FROM payments p
        LEFT JOIN (
            SELECT id,name,room_id FROM tenants
        ) AS t ON t.id = p.tenant_id
        LEFT JOIN (
            SELECT id,name FROM rooms
        ) AS r ON r.id = t.room_id
        WHERE p.owner_id = :ownerId
        AND p.tenant_id = :id
        ORDER BY p.created_at DESC
    """)
    fun getTenantPaymentHistory(ownerId:String,id:String): Flow<List<PaymentCard>>

    data class PaymentDetail(
        val id:String = "",
        val amount:Int = 0,
        val payment_status: PaymentStatus = PaymentStatus.PAID,
        val payment_type: PaymentType = PaymentType.CASH,
        val payment_date:String = "",
        val due_date:String = "",
        val created_at:String = "",
        val tenantId:String = "",
        val tenantName:String = "",
        val roomName:String = ""
    )

    @Query("""
        SELECT
            p.id,
            t.id AS tenantId,
            t.name AS tenantName,
            r.name AS roomName,
            p.amount,
            p.payment_status,
            p.payment_type,
            p.payment_date,
            p.due_date,
            p.created_at
        FROM payments p
        LEFT JOIN (
            SELECT id,name,room_id FROM tenants
        ) AS t ON t.id = p.tenant_id
        LEFT JOIN (
            SELECT id,name FROM rooms
        ) AS r On r.id = t.room_id
        WHERE p.owner_id = :ownerId AND 
        p.id = :id
        ORDER BY p.payment_date DESC
    """)
    fun getPayment(id:String,ownerId:String): Flow<PaymentDetail>


}