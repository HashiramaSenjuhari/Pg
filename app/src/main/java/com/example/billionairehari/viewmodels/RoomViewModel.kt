package com.example.billionairehari.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.billionairehari.components.ChipType
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _room_id:String? = savedStateHandle["roomId"]
    val room = mutableStateOf<Room>(
        Room(
            name = "Billionaire Hari",
            total_beds = "6",
            count = 0,
            rent_per_tenant = "3000",
            deposit_per_tenant = "3000",
            images = emptyList(),
            features = emptyList()
        )
    )
}
