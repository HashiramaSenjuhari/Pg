package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.billionairehari.components.ChipType
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _room_id:String? = savedStateHandle["roomId"]
    val room_id = _room_id
    val room = MutableStateFlow(RoomCardDetails(
        name = "Room 101",
        id = "101",
        deposit_per_tenant = "2000",
        rent_per_tenant = "2000",
        is_available = false,
        total_beds = "3",
        features = listOf("ac"),
        images = emptyList()
    ))
}
