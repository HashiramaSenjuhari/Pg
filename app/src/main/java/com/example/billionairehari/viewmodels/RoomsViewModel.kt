package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.components.current_rooms
import com.example.billionairehari.components.rooms.RoomFilterTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import com.example.billionairehari.components.rooms.RoomFilterTypes.ALL_ROOMS
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


const val ROOM_FILTER_SAVED_STATE = "ROOM_FILTER_SAVED_STATE"

data class RoomUiState(
    val rooms:List<RoomCardDetails> = emptyList<RoomCardDetails>(),
    val filter_type: RoomFilterTypes = ALL_ROOMS,
    val status:String? = null
)

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val repository: RoomRepository,
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