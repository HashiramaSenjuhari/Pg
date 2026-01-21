package com.example.billionairehari.core.data.interfaces

import android.health.connect.datatypes.units.Percentage
import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.viewmodels.DateRangeType
import kotlinx.coroutines.flow.Flow

interface PaymentRepositoryInterface {
    suspend fun insertPayment(payment: Payment)
    fun getRevenue(ownerId:String,type: DateRangeType): Flow<PaymentDao.Revenue>
    fun getPercentage(ownerId:String): Flow<PaymentDao.Percentag>
    fun getPaymentsHistory(ownerId:String): Flow<List<PaymentDao.PaymentCard>>
    fun getPaymentDetail(ownerId:String,paymentId:String): Flow<PaymentDao.PaymentDetail>
    fun getTenantPaymentHistory(ownerId:String,id:String): Flow<List<PaymentDao.PaymentCard>>
}