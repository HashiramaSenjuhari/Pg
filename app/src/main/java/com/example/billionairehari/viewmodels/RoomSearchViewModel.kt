package com.example.billionairehari.viewmodels

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


val greats = listOf<RoomCardDetails>(
    RoomCardDetails(
        id = "6",
        name = "Room 101",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 102",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 103",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 104",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 105",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    )
)

sealed class RoomSearchUiState {
    data class Default(val recent_searches:List<String>): RoomSearchUiState()
    data class Rooms(val rooms: List<RoomCardDetails>): RoomSearchUiState()
    object Loading: RoomSearchUiState()
}

class RoomSearchViewModel(
    private val savedState: SavedStateHandle
): ViewModel() {
    var recent_searches = emptyList<String>()
    init {
        recent_searches = listOf("Room 101","Room 102")
    }
    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<RoomSearchUiState> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.text.trim().length <= 2) RoomSearchUiState.Default(recent_searches = recent_searches)
            else RoomSearchUiState.Rooms(greats.filter { it.name.contains(query.text,ignoreCase = true) })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RoomSearchUiState.Loading
        )

    fun update_query(query:String) {
        _query.value = TextFieldValue(
            text = query,
            selection = TextRange(query.length)
        )
    }

    fun reset_query(){
        _query.value = TextFieldValue(text = "", selection = TextRange(0))
    }
}
