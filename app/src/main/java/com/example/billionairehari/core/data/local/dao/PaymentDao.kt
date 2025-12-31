package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Payment
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
            strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
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
            strftime('%Y-%m',payment_date) = strftime('%Y-%m','now')
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

    @Query("""
        SELECT
        CASE 
            WHEN SUM(CASE WHEN strftime('%Y-%m',amount) = strftime('%Y-%m','now','-1 months') THEN amount END) > 0
            THEN
                ROUND(
                    (
                        (
                            SUM(CASE WHEN strftime('%Y-%m',amount) = strftime('%Y-%m','now') THEN amount END) -
                            SUM(CASE WHEN strftime('%Y-%m',amount) = strftime('%Y-%m','now','-1 months') THEN amount END)
                        )/
                        SUM(CASE WHEN strftime('%Y-%m',amount) = strftime('%Y-%m','now','-1 months') THEN amount END)
                    ) * 100,2
                )
            ELSE 0.0
            END
        FROM payments
        WHERE
            owner_id = :ownerId
    """)
    fun getPercentage(ownerId:String):Flow<Int>
}