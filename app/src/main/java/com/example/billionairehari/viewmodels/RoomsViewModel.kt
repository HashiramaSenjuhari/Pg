package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.components.rooms.RoomFilterTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import com.example.billionairehari.components.rooms.RoomFilterTypes.ALL_ROOMS
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val sample_room = RoomCardDetails(
    id = "1",
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
)


val room = RoomCardDetails(
    id = "2",
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
)

val room3 = RoomCardDetails(
    id = "3",
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
)

val room4 = RoomCardDetails(
    id = "4",
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
)


val room5 = RoomCardDetails(
    id = "5",
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
)

val room6 = RoomCardDetails(
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
)

val current_rooms = listOf<RoomCardDetails>(
    room,
    sample_room,
    room3,
    room4,
    room5,
    room6
)

const val ROOM_FILTER_SAVED_STATE = "ROOM_FILTER_SAVED_STATE"

data class RoomUiState(
    val rooms:List<RoomCardDetails> = emptyList<RoomCardDetails>(),
    val filter_type: RoomFilterTypes = ALL_ROOMS,
    val status:String? = null
)

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rooms = MutableStateFlow<List<RoomCardDetails>>(current_rooms)
    val rooms = _rooms.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = emptyList()
    )
    val _saved_filter_type = savedStateHandle.getStateFlow(ROOM_FILTER_SAVED_STATE,ALL_ROOMS)
}