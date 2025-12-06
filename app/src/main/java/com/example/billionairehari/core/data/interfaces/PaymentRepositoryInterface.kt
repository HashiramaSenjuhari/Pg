package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.entity.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepositoryInterface {
    suspend fun insertPayment(payment: Payment)
    fun getRevenue(ownerId:String): Flow<ApiResult<PaymentDao.Revenue>>
}