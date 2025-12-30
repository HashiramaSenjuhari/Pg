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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val ROOM_FILTER_SAVED_STATE = "ROOM_FILTER_SAVED_STATE"

data class RoomUiState(
    val rooms:List<RoomCardDetails> = emptyList<RoomCardDetails>(),
    val filter_type: RoomFilterTypes = ALL_ROOMS,
    val status:String? = null
)

enum class FILTER {
    RENT_DUE,
    AVAILABLE,
    DEFAULT
}


@OptIn(FlowPreview::class)
@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val repository: RoomRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _type = MutableStateFlow<FILTER>(FILTER.DEFAULT)
    val type = _type.asStateFlow()
    private val room_list = repository.getRoomCardsFlow(ownerId = "1")

    val rooms: StateFlow<List<RoomDao.RoomCard>> = combine(
        _type.debounce(300),
        room_list
    ){
        type, rooms ->
        when(type){
            FILTER.DEFAULT -> rooms
            FILTER.AVAILABLE -> rooms.filter { roomCard -> roomCard.tenant_count < roomCard.bed_count }
            FILTER.RENT_DUE -> rooms.filter { roomCard -> roomCard.not_paid > 0 }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun update_filter(type: FILTER){
        _type.value = type
    }
}