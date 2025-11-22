package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn


data class Tenant(
    val id:String = "",
    val name:String = "",
    val room_name:String = "",
    val image:String = "",
    val is_paid:Boolean = true,
    val is_noticed:Boolean = true
)

class TenantsViewModel: ViewModel() {
    private val _tenants = MutableStateFlow<List<Tenant>>(emptyList())
    val tenants = _tenants.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        /** Load from the repository **/
    }
}