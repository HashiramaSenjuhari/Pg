package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.core.data.local.entity.Tenant
import kotlinx.coroutines.flow.Flow

@RewriteQueriesToDropUnusedColumns
@Dao
interface TenantDao {
    @Insert
    suspend fun insertTenant(tenant: Tenant)

    //################################################################################################
    //                                    TenantRoomCard
    //################################################################################################
    data class TenantCardDetails(val id:String,val name:String,val phone_number:String,val roomName:String,val paymentStatus:Int)

    @Query("""
        SELECT
        t.id,t.name,t.phone_number,
        r.name AS roomName,
        CASE 
            WHEN p.id IS NOT NULL AND p.totalAmount >= r.rent_price THEN 1 
            WHEN p.id IS NOT NULL AND p.totalAmount > 0 THEN 2
            ELSE 0 
        END AS paymentStatus
        FROM tenants t
        LEFT JOIN rooms r ON t.room_id = r.id
        LEFT JOIN (
            SELECT
            p.*,
            SUM(p.amount) AS totalAmount
            FROM payments p
            WHERE strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
            GROUP BY p.tenant_id
        ) AS p ON p.tenant_id = t.id
        WHERE t.owner_id = :ownerId AND t.is_active = true
    """)
    fun getTenantsCardFlow(ownerId:String) : Flow<List<TenantCardDetails>>


    @Query("""
        SELECT
        t.id,t.name,t.phone_number,
        r.name AS roomName,
        CASE 
            WHEN p.id IS NOT NULL AND p.totalAmount >= r.rent_price THEN 1 
            WHEN p.id IS NOT NULL AND p.totalAmount > 0 THEN 2
            ELSE 0 
        END AS paymentStatus
        FROM tenants t
        LEFT JOIN rooms r ON t.room_id = r.id
        LEFT JOIN (
            SELECT
            p.*,
            SUM(p.amount) AS totalAmount
            FROM payments p
            WHERE strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
            GROUP BY p.tenant_id
        ) AS p ON p.tenant_id = t.id
        WHERE t.owner_id = :ownerId AND t.is_active = true
    """)
    suspend fun getTenantsCard(ownerId:String) :List<TenantCardDetails>
    // ###############################################################################################






    // ###############################################################################################
    // Tenant Details
    // ###############################################################################################

    data class PaymentDetailCard(val id:String,val payment_date:String,val due_date:String,val is_paid:Boolean,val amount:Boolean)
    data class TenantDetails(
        /** Tenant detail **/
        val id:String = "",
        val name:String = "",
        val image:String = "",
        val phone_number:String = "",
        val joining_date:String = "",
        val automatic_rent_remainder:Boolean = true,
        val is_active:Boolean = true,
        val created_at:String = "",

        /** Additional Info **/
        val dob:String? = null,
        val state:String? = null,
        val alternatePhone:String? =  null,
        val identityDocument:String? = null,

        /** Room details **/
        val room_id:String = "",
        val tenantRoomName:String = "",
        val dueDate: String = "",
        val paymentStatus:Int = 0
    )

    @Query("""
        SELECT
        t.*,
        COALESCE(r.name,'Unknown') AS tenantRoomName,
        COALESCE(r.due_day,'Unknown') AS dueDate,
        CASE 
            WHEN COALESCE(p.amountPaid,0) >= COALESCE(r.rent_price,0) THEN 1
            WHEN COALESCE(p.amountPaid,0) > 0 THEN 2
            ELSE 0
        END AS paymentStatus,
        a.dob as dob,
        a.state as state,
        a.alternate_phone as alternatePhone,
        a.identity_document as identityDocument
        FROM tenants t
        LEFT JOIN rooms r ON t.room_id = r.id
        LEFT JOIN additional_info a ON a.tenant_id = t.id
        LEFT JOIN (
            SELECT 
                id,
                tenant_id,
                payment_date,
                SUM(amount) AS amountPaid
            FROM payments
            WHERE strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
            GROUP BY tenant_id
        ) AS p ON p.tenant_id = t.id
        WHERE t.owner_id = :ownerId AND t.id = :tenantId
    """)
    fun getTenant(ownerId:String,tenantId:String) : Flow<TenantDetails>

    data class PaymentCard(
        val id:String,
        val paymentDate:String,
        val dueDate:String,
        val amountPaid:Int
    )

    @Query("""
        SELECT
        p.*
        FROM tenants t
        INNER JOIN (
            SELECT
                id,
                payment_date AS paymentDate,
                due_date AS dueDate,
                amount AS amountPaid,
                created_at,
                tenant_id
            FROM payments
        ) AS p ON p.tenant_id = t.id
        WHERE t.owner_id = :ownerId AND t.id = :tenantId
        ORDER BY strftime('%Y-%m-%d',p.created_at) DESC
        LIMIT 3
    """)
    fun getTenantRecentPaymentsDetail(ownerId:String,tenantId:String): Flow<List<PaymentCard>>


    @Query("""
        SELECT
        p.*
        FROM tenants t
        INNER JOIN (
            SELECT
                id,
                payment_date AS paymentDate,
                due_date AS dueDate,
                amount AS amountPaid,
                tenant_id,
                created_at
            FROM payments
        ) AS p ON p.tenant_id = t.id
        WHERE t.owner_id = :ownerId AND t.id = :tenantId
        ORDER BY strftime('%Y-%m-%d HH:mm',p.created_at) DESC
    """)
    fun getTenantPaymentDetails(ownerId:String,tenantId:String): Flow<List<PaymentCard>>

    // ###############################################################################################


    /** ###############################################################################################
    // Rent Paid Count
    // ############################################################################################### **/

    data class RentNotPaid(val notPaid:Int)
    data class RentPaid(val rentPaid:Int)

    @Query(
        """
        WITH prev_month AS (
            SELECT
            p.id,
            p.tenant_id
            FROM payments p
            WHERE strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now','-1 month') AND p.payment_date = 'PAID'
        )
        SELECT COUNT(DISTINCT t.id) AS notPaid
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id
        LEFT JOIN prev_month pmp ON pmp.tenant_id = t.id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) < strftime('%Y-%m','now') || '-' || r.due_day AND pmp.id IS NOT NULL
        WHERE r.owner_id = :ownerId AND t.is_active = true AND p.id IS NULL
    """
    )
    fun getRentNotPaidFlow(ownerId: String): Flow<RentNotPaid>

    @Query("""
        SELECT COUNT(DISTINCT t.id) AS rentPaid
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id
        INNER JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
    """)
    fun getRentPaidFlow(ownerId:String) : Flow<RentPaid>

    // ###############################################################################################

    data class TenantWithRoomRentCard(
        val id:String = "",
        val tenantName:String = "",
        val roomId:String = "",
        val roomName:String = "",
        val dueDay:Int = 0,
        val rentPrice: Int = 0,
        val paymentAmount:Int = 0
    )

    @Query("""
        SELECT
        t.id,
        t.name AS tenantName,
        r.id AS roomId,
        r.name AS roomName,
        r.due_day AS dueDay,
        r.rent_price AS rentPrice,
        CASE
            WHEN r.rent_price - SUM(COALESCE(p.amount,0)) > 0 THEN r.rent_price - SUM(COALESCE(p.amount,0))
            ELSE 0
        END AS paymentAmount
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id AND r.owner_id = t.owner_id
        LEFT JOIN payments p ON p.tenant_id = t.id AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE t.owner_id = :ownerId AND t.is_active = true
        GROUP BY t.id,t.name
    """)
    fun getTenantSearchCards(ownerId:String): Flow<List<TenantWithRoomRentCard>>

    @Query("""
        UPDATE tenants
        SET is_active = false
        WHERE id = :tenantId AND owner_id = :ownerId
    """)
    suspend fun removeTenant(tenantId:String,ownerId:String)
}