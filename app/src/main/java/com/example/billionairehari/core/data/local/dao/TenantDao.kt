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
    fun getTenantsFlow(ownerId:String) : Flow<List<TenantCardDetails>>

    // ###############################################################################################






    // ###############################################################################################
    // Tenant Details
    // ###############################################################################################

    data class PaymentDetailCard(val id:String,val payment_date:String,val due_date:String,val is_paid:Boolean,val amount:Boolean)
    data class TenantDetails(val tenat: Tenant,val tenantRoomName:String,val payments: List<PaymentDetailCard>)

    @Query("""
        SELECT
        t.*,
        r.name AS roomName,
        r.due_date AS dueDate,
        CASE WHEN strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now') THEN 1 ELSE 0 END AS current_paid,
        p.id AS paymentId,
        p.payment_date AS paymentDate,
        p.due_date AS dueDate,
        p.is_paid AS isPaid,
        p.amount AS amountPaid
        FROM tenants t
        INNER JOIN rooms r ON t.room_id = r.id
        LEFT JOIN payments p ON p.tenant_id = t.id
        WHERE r.owner_id = :ownerId
        ORDER BY t.id,COALESCE(p.payment_date,'0000-00-00') DESC
    """)
    fun getTenant(tenantId:String) : TenantDetails

    // ###############################################################################################





    // ###############################################################################################
    // Rent Paid Count
    // ###############################################################################################

    data class RentNotPaid(val rent_not_paid:Int)
    data class RentPaid(val rent_paid:Int)

    @Query("""
        SELECT COUNT(DISTICT t.id) AS notPaid
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id
        LEFT JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId AND p.id IS NULL
    """)
    fun getRentNotPaidFlow(ownerId:String): Flow<RentNotPaid>

    @Query("""
        SELECT COUNT(DISTINCT t.id) AS paid
        FROM tenants t
        INNER JOIN rooms r ON r.id = t.room_id
        INNER JOIN payments p ON p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
        WHERE r.owner_id = :ownerId
    """)
    fun getRentPaidFlow(ownerId:String) : Flow<RentPaid>

    // ###############################################################################################
}