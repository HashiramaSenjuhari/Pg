package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.screens.TenantData
import com.example.billionairehari.screens.tenants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class TenantSearchCard(
    val name:String = "",
    val room:String = "",
    val due:Long = 0L,
    val rent_amount:String = ""
)


class TenantSearchViewModel(
    private val savedState: SavedStateHandle
): ViewModel() {
    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()
    val result: StateFlow<List<TenantData>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.isBlank()) tenants
            else tenants.filter { it.name.contains(query, ignoreCase = true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun update_query(query:String) {
        _query.value = query
    }

    fun reset_query(){
        _query.value = ""
    }
}