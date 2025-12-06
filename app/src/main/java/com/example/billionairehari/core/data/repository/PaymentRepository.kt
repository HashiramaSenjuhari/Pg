package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.PaymentRepositoryInterface
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.entity.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
): PaymentRepositoryInterface {
    override suspend fun insertPayment(payment: Payment) {
        paymentDao.insertPayment(payment = payment)
    }

    override fun getRevenue(ownerId: String): Flow<ApiResult<PaymentDao.Revenue>> = paymentDao
        .getTotalRevenue(ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(message = it.message ?: "", code = 300) }
}