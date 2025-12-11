package com.example.billionairehari.viewmodels

import android.util.Log
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
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
    init {
        viewModelScope.launch {
            val rooms = repository.getTables()
            Log.d("BillionaireHariGreat",rooms.toString())
        }
    }

    val rooms: StateFlow<List<RoomDao.RoomCard>> = repository.getRoomCardsFlow("1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val _saved_filter_type = savedStateHandle.getStateFlow(ROOM_FILTER_SAVED_STATE,ALL_ROOMS)
}