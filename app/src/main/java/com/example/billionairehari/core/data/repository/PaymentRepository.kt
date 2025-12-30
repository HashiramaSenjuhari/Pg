package com.example.billionairehari.core.data.repository

import android.util.Log
import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.PaymentRepositoryInterface
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.utils.toDateString
import com.example.billionairehari.viewmodels.DateRangeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.compareTo

class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
): PaymentRepositoryInterface {
    override suspend fun insertPayment(payment: Payment) {
        paymentDao.insertPayment(payment = payment)
    }

    override fun getRevenue(ownerId: String, type: DateRangeType): Flow<PaymentDao.Revenue> {
        Log.d("GETREVENUE","${type}")
        return when(val value = type){
            is DateRangeType.Static -> {
                val month = value.date
                if(month > 0){
                   Log.d("GETREVENUE","CURRENT ${type} > 0")
                   paymentDao.getLastNTotalRevenue(ownerId = ownerId, month = value.date)
                }
                else {
                    Log.d("GETREVENUE","CURRENT ${type} < 0")
                    paymentDao.getCurrentTotalRevenue(ownerId = ownerId)
                }
            }
            is DateRangeType.Dynamic -> {
                Log.d("GETREVENUE","CURRENT ${type} dynamic")
                val startDate = value.startDate?.toDateString()
                val endDate = value.endDate?.toDateString()
                paymentDao.getRangeTotalRevene(ownerId = ownerId, startDate = startDate!!, endDate = endDate!!)
            }
        }
    }
}