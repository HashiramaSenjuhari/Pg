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

class RoomSearchViewModel(
    private val savedState: SavedStateHandle
): ViewModel() {
    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<List<RoomCardDetails>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map { query ->
            if(query.text.isBlank()) greats
            else greats.filter { it.name.contains(query.text,ignoreCase = true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
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
