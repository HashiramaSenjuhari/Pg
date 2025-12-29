package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed class DateRangeType{
    data class Static(val date:Int): DateRangeType()
    data class Dynamic(val startDate:Long? = null,val endDate:Long? = null): DateRangeType()
}

@HiltViewModel
class GetTotalRevenueAndInfoViewModel @Inject constructor(
    private val paymentDao: PaymentDao
): ViewModel() {
    private val _filter = MutableStateFlow<DateRangeType>(DateRangeType.Static(0))
    private val _revenue = MutableStateFlow<PaymentDao.Revenue>(PaymentDao.Revenue(revenue = 0))

    val revenue: StateFlow<PaymentDao.Revenue> = _filter.mapLatest {
        value ->
        withContext(Dispatchers.IO){
            when(value){
                is DateRangeType.Static -> {
                    val month = value.date
                    if(month > 0){
                        paymentDao.getLastNTotalRevenue(ownerId = "1", month = value.date)
                    }
                    else {
                        paymentDao.getCurrentTotalRevenue(ownerId = "1")
                    }
                }
                is DateRangeType.Dynamic -> {
                    val startDate = value.startDate?.toDateString()
                    val endDate = value.endDate?.toDateString()
                    paymentDao.getRangeTotalRevene(ownerId = "1", startDate = startDate!!, endDate = endDate!!)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PaymentDao.Revenue(revenue = 0)
    )
    fun update_filter(filter: DateRangeType){
        Log.d("VIEWMODEL_DEGUB","FROM TOTAL REVENUE ${filter}")
        _filter.value = filter
    }
}