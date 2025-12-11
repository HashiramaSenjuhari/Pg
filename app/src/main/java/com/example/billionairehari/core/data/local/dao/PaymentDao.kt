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
            p.owner_id = :ownerId
            AND strftime('%Y-%m',payment_date) = strftime('%Y-%m','now','localtime')
    """)
    fun getTotalRevenue(ownerId:String): Flow<Revenue>
    // ###############################################################################################
}