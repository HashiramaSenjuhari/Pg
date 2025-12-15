package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.components.ChipType
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel(assistedFactory = RoomViewModel.RoomViewModelFactory::class)
class RoomViewModel @AssistedInject constructor(
    private val repository: RoomRepository,
    @Assisted private val id:String
): ViewModel() {
    @AssistedFactory
    interface RoomViewModelFactory {
        fun create(id:String): RoomViewModel
    }

    val room_detail: StateFlow<RoomDao.RoomWithTenantAndDueCount> = repository.getRoomDetail(ownerId = "1", roomId = id)
        .map { value ->
            value
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RoomDao.RoomWithTenantAndDueCount()
        )
    val tenants: StateFlow<List<RoomDao.RoomTenantDetails>> = repository
        .getRoomTenants(ownerId = "1", roomId = id)
        .map {
            value ->
            value
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
