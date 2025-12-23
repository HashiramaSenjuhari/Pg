package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.components.DateRangeType
import com.example.billionairehari.core.data.local.dao.PaymentDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetTotalRevenueAndInfoViewModel @Inject constructor(
    private val paymentDao: PaymentDao
): ViewModel() {
    private val _filter = MutableStateFlow<String>("")

    fun update_filter(filter:String){
        _filter.value = filter
    }

    val revenue: StateFlow<PaymentDao.Revenue> = paymentDao.getTotalRevenue(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PaymentDao.Revenue(revenue = 0)
        )
}