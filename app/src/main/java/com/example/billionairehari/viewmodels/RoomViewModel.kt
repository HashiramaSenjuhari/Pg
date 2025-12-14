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

sealed class UiLoadingState<out T>{
    object Loading: UiLoadingState<Nothing>()
    data class Data<T>(val data:T): UiLoadingState<T>()
}

@AssistedFactory
interface RoomViewModelFactory {
    fun create(roomId:String): RoomViewModel
}

@HiltViewModel
@AssistedInject
class RoomViewModel @AssistedInject constructor(
    private val repository: RoomRepository,
    @Assisted private val roomId:String
): ViewModel() {
    val room_detail: StateFlow<UiLoadingState<RoomDao.RoomWithTenantAndDueCount>> = repository.getRoomDetail(ownerId = "1", roomId = room ?: "3ed26834-c210-4e97-b26f-a3eeafcbf669")
        .map { value ->
            UiLoadingState.Data(value)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiLoadingState.Loading
        )
//    val tenants: StateFlow<UiLoadingState<List<RoomDao.RoomTenantDetails>>> = repository
//        .getRoomTenants(ownerId = "1", roomId = _room_id ?: "")
//        .map {
//            value ->
//            UiLoadingState.Data(value)
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = UiLoadingState.Loading
//        )
}
