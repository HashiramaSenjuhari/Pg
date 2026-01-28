package com.example.billionairehari.core.data.repository

import android.health.connect.datatypes.units.Percentage
import android.icu.util.Calendar
import android.util.Log
import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.PaymentRepositoryInterface
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.utils.toDateFormat
import com.example.billionairehari.utils.toDateString
import com.example.billionairehari.viewmodels.DateRangeType
import com.example.billionairehari.viewmodels.FilterOption
import com.example.billionairehari.viewmodels.PaymentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.compareTo

class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
): PaymentRepositoryInterface {
    override suspend fun insertPayment(payment: Payment) {
        paymentDao.insertPayment(payment = payment)
    }

    override fun getRevenue(ownerId: String, type: DateRangeType): Flow<PaymentDao.Revenue> {
        return when(val value = type){
            is DateRangeType.Static -> {
                val month = value.date
                if(month > 0){
                    /** Getting Start And End Format "yyyy-MM" **/
                    val date = LocalDate.now().withDayOfMonth(1)
                    val start = date
                        .minusMonths(month.toLong())
                        .atStartOfDay(ZoneId.of("Asia/Kolkata"))
                        .toInstant()
                        .toEpochMilli()
                        .toDateFormat("yyyy-MM")
                    val end = date.atStartOfDay(ZoneId.of("Asia/Kolkata"))
                        .toInstant()
                        .toEpochMilli()
                        .toDateFormat("yyyy-MM")

                    paymentDao.getLastNTotalRevenue(ownerId = ownerId, startDate = start, endDate = end)
                }
                else {
                    paymentDao.getCurrentTotalRevenue(ownerId = ownerId)
                }
            }
            is DateRangeType.Dynamic -> {
                val startDate = value.startDate?.toDateString()
                val endDate = value.endDate?.toDateString()
                paymentDao.getRangeTotalRevene(ownerId = ownerId, startDate = startDate!!, endDate = endDate!!)
            }
        }
    }

    override fun getPercentage(ownerId: String): Flow<PaymentDao.Percentag> = paymentDao.getPercentage(ownerId = ownerId)
    override fun getPaymentsHistory(ownerId: String,paymentTypes:List<String>,paymentDates: List<String>): Flow<List<PaymentDao.PaymentCard>> {
        Log.d("PAYMENT_DEBUG","${paymentTypes} ${paymentDates}")
        return paymentDao.getPaymentHistory(
            ownerId = "1",
            shouldFilterByPayment = paymentTypes.isNotEmpty(),
            shouldFilterByDates = paymentDates.isNotEmpty(),
            paymentTypes = paymentTypes,
            dates = paymentDates
        )
    }
    override fun getPaymentDetail(
        ownerId: String,
        paymentId: String
    ): Flow<PaymentDao.PaymentDetail> = paymentDao.getPayment(ownerId = ownerId, id = paymentId)
    override fun getTenantPaymentHistory(ownerId:String,id:String): Flow<List<PaymentDao.PaymentCard>> = paymentDao
        .getTenantPaymentHistory(ownerId = ownerId,id = id)

}