package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GetAllPaymentHistoryViewModel @Inject constructor(
    private val payment: PaymentRepository
) : ViewModel() {
    val history = payment.getPaymentsHistory(ownerId = "1")
        .stateIn(
            started = SharingStarted.Lazily,
            scope = viewModelScope,
            initialValue = emptyList()
        )
}