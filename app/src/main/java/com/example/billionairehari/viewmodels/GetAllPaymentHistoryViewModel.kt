package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.repository.OwnerRepository
import com.example.billionairehari.core.data.repository.PaymentRepository
import com.example.billionairehari.core.data.repository.TenantRepository
import com.example.billionairehari.screens.Filter
import com.example.billionairehari.screens.FilterOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

object PaymentType {
    const val ALL = "ALL"
    const val UPI = "UPI"
    const val CASH = "CASH"
}

data class FilterOption(
    val paymentMonths: List<String> = emptyList(),
    val paymentType:List<String> = emptyList()
)

@HiltViewModel
class GetAllPaymentHistoryViewModel @Inject constructor(
    private val payment: PaymentRepository,
    private val owner: OwnerRepository

) : ViewModel() {
    private val _filterOptions = MutableStateFlow<FilterOption>(FilterOption())

    val uiState = _filterOptions.flatMapLatest {
        filter ->
        payment.getPaymentsHistory(ownerId = "1", paymentDates = filter.paymentMonths, paymentTypes = filter.paymentType)
    }.map {
        payments ->
        payments
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val first_date = owner.getFirstDate(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    fun update_payment_filters(types: List<String>,months : List<String>){
        _filterOptions.value = _filterOptions.value.copy(
            paymentType = types,
            paymentMonths = months
        )
    }
    fun clear(){
        _filterOptions.value = FilterOption()
    }
}