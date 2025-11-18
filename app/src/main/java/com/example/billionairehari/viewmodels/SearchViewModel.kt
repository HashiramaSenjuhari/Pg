package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

data class TenantSearchCard(
    val name:String = "",
    val room:String = "",
    val due:Long = 0L,
    val rent_amount:String = ""
)

val greats = listOf<TenantSearchCard>(
    TenantSearchCard(
        name = "BillionaireHari",
        room = "Room 101",
        due = 0L,
        rent_amount = "2000"
    ),
    TenantSearchCard(
        name = "Hari",
        room = "Room 101",
        due = 0L,
        rent_amount = "2000"
    ),
    TenantSearchCard(
        name = "Billionaire",
        room = "Room 101",
        due = 0L,
        rent_amount = "2000"
    ),
    TenantSearchCard(
        name = "Great",
        room = "Room 101",
        due = 0L,
        rent_amount = "2000"
    ),
    TenantSearchCard(
        name = "Mark",
        room = "Room 101",
        due = 0L,
        rent_amount = "2000"
    )
)

class SearchViewModel: ViewModel() {
    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()
    val result: StateFlow<List<TenantSearchCard>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.isBlank()) greats
            else greats.filter { it.name.contains(query, ignoreCase = true) }
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