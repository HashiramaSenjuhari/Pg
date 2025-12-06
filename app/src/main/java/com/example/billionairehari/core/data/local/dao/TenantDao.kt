package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.core.data.local.entity.Tenant
import kotlinx.coroutines.flow.Flow

@Dao
interface TenantDao {
    @Insert
    suspend fun insertTenant(tenant: Tenant)

    //################################################################################################
    //                                    TenantRoomCard
    //################################################################################################
    data class TenantCardDetails(val id:String,val name:String,val phone_number:String,val roomName:String,val current_paid:Int)

    @Query("""
        SELECT
        t.id,t.name,t.phone_number,
        r.name AS roomName,
        CASE WHEN p.id IS NOT NULL THEN 1 ELSE 0 END AS current_paid
        FROM tenants t
        LEFT JOIN rooms r ON t.room_id = r.id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
    """)
    fun getTenantsCardFlow(ownerId:String) : Flow<List<TenantCardDetails>>


    @Query("""
        SELECT
        t.id,t.name,t.phone_number,
        r.name AS roomName,
        CASE WHEN p.id IS NOT NULL THEN 1 ELSE 0 END AS current_paid
        FROM tenants t
        LEFT JOIN rooms r ON t.room_id = r.id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
    """)
    suspend fun getTenantsCard(ownerId:String) :List<TenantCardDetails>
    // ###############################################################################################






    // ###############################################################################################
    // Tenant Details
    // ###############################################################################################

    data class PaymentDetailCard(val id:String,val payment_date:String,val due_date:String,val is_paid:Boolean,val amount:Boolean)
    data class TenantDetails(
        /** Tenant detail **/
        val id:String,
        val name:String,
        val image:String,
        val phone_number:String,
        val alternate_number:String,
        val joining_date:Long,
        val automatic_rent_remainder:Boolean,
        val is_active:Boolean,

        /** Room details **/
        val room_id:String,
        val tenantRoomName:String,
        val dueDate: Long,
        val currentPaid:Int,

        /** payment detail **/
        val paymentId:String,
        val paymentDueDate:Long,
        val paymentDate:Long,
        val paymentPaid:Boolean,
        val paymentAmount:Int,
        val paymentTenantId:String
    )

    @Query("""
        SELECT
        t.*,
        r.name AS tenantRoomName,
        r.due_date AS dueDate,
        CASE WHEN strftime('%Y-%m',p.paymentDate) = strftime('%Y-%m','now') THEN 1 ELSE 0 END AS currentPaid,
        p.*
        FROM tenants t
        INNER JOIN rooms r ON t.room_id = r.id
        LEFT JOIN (
            SELECT id AS paymentId,
            due_date AS paymentDueDate,
            payment_date AS paymentDate,
            is_paid AS paymentPaid,
            amount AS paymentAmount,
            tenant_id AS paymentTenantId
            FROM payments
        ) AS p ON p.paymentTenantId = t.id
        WHERE r.owner_id = :ownerId AND t.id = :tenantId
        ORDER BY t.id,COALESCE(p.paymentDate,'0000-00-00') DESC
    """)
    fun getTenant(ownerId:String,tenantId:String) : Flow<TenantDetails>

    // ###############################################################################################





    /** ###############################################################################################
    // Rent Paid Count
    // ############################################################################################### **/

    data class RentNotPaid(val notPaid:Int)
    data class RentPaid(val rentPaid:Int)

    @Query("""
        SELECT COUNT(DISTINCT t.id) AS notPaid
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId AND p.id IS NULL
    """)
    fun getRentNotPaidFlow(ownerId:String): Flow<RentNotPaid>

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
}