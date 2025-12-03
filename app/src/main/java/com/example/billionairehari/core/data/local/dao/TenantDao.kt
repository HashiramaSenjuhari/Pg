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
    data class TenantCardDetails(val id:String,val name:String,val tenantRoomName:String,val phone_number:String,val current_paid:String)

    @Query("""
        SELECT
            t.id,
            t.name,
            r.room as tenantRoomName,
            t.phone_number,
            CASE
                WHEN p.id IS NOT NULL THEN 'Paid'
                ELSE 'Not Paid'
            END AS current_paid
        FROM tenant t
        INNER JOIN room r ON
            r.owner_id = :ownerId
            AND r.id = t.room_id
        LEFT JOIN payments p ON
            p.tenant_id = t.id
            AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now');
    """)
    fun getTenantFlow(ownerId:String) : Flow<List<TenantCardDetails>>

    @Query("""
        SELECT
            t.id,
            t.name,
            r.room as tenantRoomName,
            t.phone_number,
            CASE
                WHEN p.id IS NOT NULL THEN 'Paid'
                ELSE 'Not Paid'
            END AS current_paid
            FROM tenant t
            INNER JOIN room r ON
                r.owner_id = :ownerId
                AND r.id = t.room_id
            LEFT JOIN payments p ON
                p.tenant_id = t.id
                AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now');
    """)
    suspend fun getTenants(ownerId:String) : List<TenantCardDetails>

    // ###############################################################################################






    // ###############################################################################################
    // Tenant Details
    // ###############################################################################################

    data class PaymentDetailCard(val id:String,val payment_date:String,val due_date:String,val is_paid:Boolean,val amount:Boolean)
    data class TenantDetails(val tenat: Tenant,val tenantRoomName:String,val payments: List<PaymentDetailCard>)

    @Query("""
        SELECT
            t.*,
            r.name as tenantRoomName,
            p.* as paymentHistory,
            CASE
                WHEN current_month.id IS NOT NULL THEN 'Paid'
                ELSE 'Not Paid'
            END AS current_paid
        FROM tenant t
        INNER JOIN rooms r
            ON r.id = t.room_id
        LEFT JOIN (
            SELECT 
                id,
                payment_date,
                due_date ,
                is_paid,
                amount
            FROM payments
            WHERE tenant_id = :tenantId
            ORDER BY payment_date DESC
            LIMIT 3
        ) AS p ON
            p.id = t.id
        LEFT JOIN payments current_month
            ON current_month.tenant_id = t.id
            AND strftime('%Y-%m',current_month.payment_date) = strftime('%Y-%m','now')
        WHERE t.id = :tenantId
    """)
    fun getTenant(tenantId:String) : TenantDetails

    // ###############################################################################################





    // ###############################################################################################
    // Rent Paid Count
    // ###############################################################################################

    data class RentNotPaid(val rent_not_paid:Int)

    @Query("""
        SELECT COUNT(*) as rent_not_paid
        FROM tenants t
        INNER JOIN rooms r
            ON r.id = t.room_id
        WHERE
            r.owner_id = :ownerId
            AND NOT EXIST(
                SELECT 1
                FROM payments p
                WHERE 
                t.id = p.tenant_id
                AND strftime('%Y-%m',p.payment_date) = strftime('%Y-%m','now')
            )
    """)
    suspend fun getRentNotPaidFlow(ownerId:String): Flow<RentNotPaid>


    // ###############################################################################################
}