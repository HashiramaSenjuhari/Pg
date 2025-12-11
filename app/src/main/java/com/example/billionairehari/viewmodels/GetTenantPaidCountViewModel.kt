package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.repository.TenantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GetTenantPaidCountViewModel @Inject constructor(
    private val repository: TenantRepository
) : ViewModel() {
    val paid: StateFlow<TenantDao.RentPaid> = repository.getPaidCount(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TenantDao.RentPaid(rentPaid = 0)
        )
    val not_paid: StateFlow<TenantDao.RentNotPaid> = repository.getNotPaidCount(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TenantDao.RentNotPaid(notPaid = 0)
        )
}