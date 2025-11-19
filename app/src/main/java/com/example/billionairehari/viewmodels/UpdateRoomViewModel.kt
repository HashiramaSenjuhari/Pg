package com.example.billionairehari.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billionairehari.model.Room

class UpdateRoomViewModel(
    private val room_data:Room
) : ViewModel() {
    private val _room = mutableStateOf<Room>(room_data)
    val room: State<Room> get() = _room

    fun update_name(name:String) {
        _room.value = _room.value.copy(
            name = name
        )
    }

    fun update_beds(beds:Int){
        _room.value = _room.value.copy(
            count = beds
        )
    }

    fun update_rent(rent:String){
        _room.value = _room.value.copy(
            rent_per_tenant = rent
        )
    }

    fun update_deposit(price:String){
        _room.value = _room.value.copy(
            deposit_per_tenant = price
        )
    }

    fun update_features(features:String){
        val old = _room.value.features.toMutableList()
        _room.value = _room.value.copy(
            features = old + features
        )
    }

    fun remove_features(feature: String){
         val features = _room.value.features.toMutableList()
        features.remove(feature)
        _room.value = _room.value.copy(
            features = features
        )
    }
}

class UpdateRoomFactory(
    private val room: Room
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateRoomViewModel::class.java)){
            return UpdateRoomViewModel(room_data = room) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}