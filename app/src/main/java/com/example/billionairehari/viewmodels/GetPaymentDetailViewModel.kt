package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.repository.PaymentRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = GetPaymentDetailViewModel.GetPaymentDetailFactory::class)
class GetPaymentDetailViewModel @AssistedInject constructor(
    private val paymentRepository: PaymentRepository,
    @Assisted private val id:String
): ViewModel() {
    @AssistedFactory
    interface GetPaymentDetailFactory {
        fun create(id:String) : GetPaymentDetailViewModel
    }

    val paymentDetails = paymentRepository.getPaymentDetail(ownerId = "1", paymentId = id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PaymentDao.PaymentDetail()
        )
}