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

val greats = listOf<String>("Billionaire","BillionaireHari","Hari","Great","Mark")

class SearchViewModel: ViewModel() {
    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    val result: StateFlow<List<String>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.isBlank()) greats
            else greats.filter { it.contains(query, ignoreCase = true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun update_query(query:String) {
        _query.value = query
    }
}