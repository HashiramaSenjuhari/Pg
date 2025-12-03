package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Payment

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
            p.owner_id = :ownerId
            AND strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
    """)
    suspend fun getTotalRevenue(ownerId:String): Revenue
    // ###############################################################################################





    // ###############################################################################################
    // Rent Detail Count
    // ###############################################################################################

    data class RentPaid(val rent_paid:Int)

    @Query("""
        SELECT COUNT(*) as rent_paid
        FROM payments
        WHERE
            owner_id = :ownerId
            AND strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
    """)
    fun getRentPaidFlow(ownerId:String): Flow<RentPaid>

    // ###############################################################################################
}