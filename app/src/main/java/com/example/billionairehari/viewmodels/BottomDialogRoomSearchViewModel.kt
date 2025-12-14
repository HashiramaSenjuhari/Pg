package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottomDialogRoomSearchViewModel @Inject constructor(
    private val room_repository: RoomRepository
): ViewModel() {
    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    private val rooms: StateFlow<List<RoomDao.RoomNameAndTenantCount>> = room_repository.getRoomNameAndAvailability(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val rooms_great = room_repository.getRoomCardsFlow(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val results: StateFlow<List<RoomDao.RoomNameAndTenantCount>> = query
        .debounce(300)
        .distinctUntilChanged()
        .combine(rooms){   // this referesh the rooms if rooms get updated or query
            query,rooms->
            Log.d("BILLIONAIREGREATHARI",rooms.toString())
            if(query.length >= 3) rooms.filter { room -> room.name.contains(query, ignoreCase = true) }
            else rooms
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun update_query(query:String) {
        _query.value = query
    }
}