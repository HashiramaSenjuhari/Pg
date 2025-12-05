package com.example.billionairehari.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
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


sealed class TenantSearchUiState {
    object Loading: TenantSearchUiState()
    data class Default(val recent_searches:List<String>): TenantSearchUiState()
    data class Tenants(val tenants:List<TenantData>): TenantSearchUiState()
}
class TenantSearchViewModel(
    private val savedState: SavedStateHandle
): ViewModel() {
    var recent_searches = emptyList<String>()

    init {
        recent_searches = listOf("BillionaireHari","BillionaireHari")
    }

    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<TenantSearchUiState> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.text.trim().length <= 2) TenantSearchUiState.Default(recent_searches = recent_searches)
            else TenantSearchUiState.Tenants(tenants.filter { it.name.contains(query.text, ignoreCase = true) })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TenantSearchUiState.Loading
        )

    fun update_query(query:String) {
        _query.value = TextFieldValue(query)
    }

    fun reset_query(){
        _query.value = TextFieldValue("")
    }
}