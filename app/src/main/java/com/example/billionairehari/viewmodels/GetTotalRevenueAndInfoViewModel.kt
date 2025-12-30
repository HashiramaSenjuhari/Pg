package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.repository.PaymentRepository
import com.example.billionairehari.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed class DateRangeType{
    data class Static(val date:Int): DateRangeType()
    data class Dynamic(val startDate:Long? = null,val endDate:Long? = null): DateRangeType()
}

@HiltViewModel
class GetTotalRevenueAndInfoViewModel @Inject constructor(
    private val payment: PaymentRepository
): ViewModel() {
    private val _filter = MutableStateFlow<DateRangeType>(DateRangeType.Static(0))

    val revenue: StateFlow<PaymentDao.Revenue> = _filter
        .flatMapLatest {
            value ->
            Log.d("GETREVENUE","Cliked")
            payment.getRevenue(ownerId = "1", type = value)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PaymentDao.Revenue(revenue = 0)
        )
    fun update_filter(filter: DateRangeType){
        Log.d("GETREVENUE","FROM TOTAL REVENUE ${filter}")
        _filter.value = filter
    }
}