package com.example.billionairehari.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.repository.RecentSearchRepository
import com.example.billionairehari.screens.TenantData
import com.example.billionairehari.screens.tenants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TenantSearchCard(
    val name:String = "",
    val room:String = "",
    val due:Long = 0L,
    val rent_amount:String = ""
)


@HiltViewModel
class TenantSearchViewModel @Inject constructor (
    private val repository: RecentSearchRepository,
    private val savedState: SavedStateHandle
): ViewModel() {
    var recent_searches = emptyList<String>()

    init {
        recent_searches = listOf("BillionaireHari","BillionaireHari")
    }

    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<SearchUiState<TenantData>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.text.trim().length <= 2) SearchUiState.Default
            else SearchUiState.Data<TenantData>(tenants.filter { it.name.contains(query.text, ignoreCase = true) })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Loading
        )

    fun update_query(query:String) {
        _query.value = TextFieldValue(query)
    }

    fun reset_query(){
        _query.value = TextFieldValue("")
    }
}