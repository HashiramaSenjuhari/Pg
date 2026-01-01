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
}