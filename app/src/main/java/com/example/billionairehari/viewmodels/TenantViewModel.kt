package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Tenant
import com.example.billionairehari.core.data.repository.TenantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel(assistedFactory = TenantViewModel.TenantViewModelFactory::class)
class TenantViewModel @AssistedInject constructor(
    private val repository: TenantRepository,
    @Assisted private val id:String
): ViewModel() {
    @AssistedFactory
    interface TenantViewModelFactory {
        fun create(id:String): TenantViewModel
    }

    val tenant_basic = repository.getTenant(ownerId = "1", tenantId = id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TenantDao.TenantDetails()
        )

}