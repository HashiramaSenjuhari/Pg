package com.example.billionairehari.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.utils.currentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RoomUpdateData(
    val id:String = "",
    val name:String = "",
    val location:String = "",
    val dueDay:String = "",
    val bedCount:String = "",
    val images:List<String> = emptyList(),
    val deposit:String = "",
    val rentPrice:String = "",
    val features:List<String> = emptyList(),
    val ownerId:String = "",
    val nameError:String? = null,
    val locationError:String? = null,
    val dueDayError:String? = null,
    val bedCountError:String? = null,
    val imageCountError:String? = null,
    val depositError:String? = null,
    val rentPriceError:String? = null,
    val isLoading:Boolean = false
)
fun Room.toDefault() : RoomUpdateData = RoomUpdateData(
    ownerId = ownerId,
    id = id,
    name = name,
    location = location,
    dueDay = dueDate.toString(),
    bedCount = bedCount.toString(),
    images = emptyList(),
    deposit = deposit.toString(),
    rentPrice = rentPrice.toString(),
    features = features
)

fun RoomUpdateData.toRoom(): Room = Room(
    id = id,
    ownerId = ownerId,
    name = name,
    location = location,
    features = features,
    deposit = deposit.toInt(),
    images = images,
    bedCount = bedCount.toInt(),
    dueDate = dueDay.toInt(),
    rentPrice = rentPrice.toInt(),
    updatedAt = currentDateTime(),
    createdAt = currentDateTime()
)

@HiltViewModel
class UpdateRoomViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val _room = mutableStateOf<RoomUpdateData>(RoomUpdateData())
    fun loadRoom(id:String){
        viewModelScope.launch {
            val room_values = repository.getRoom(roomId = id, ownerId = "1").toDefault()
            _room.value = room_values
        }
    }
    val room: State<RoomUpdateData> = _room

    /** update **/

    fun update_name(name:String) {
        _room.value = _room.value.copy(
            name = name,
            nameError = null
        )
    }

    fun update_due_day(dueDay:String){
        _room.value = _room.value.copy(
            dueDay = dueDay,
            dueDayError = null
        )
    }

    fun update_location(location:String){
        _room.value = _room.value.copy(
            location = location,
            locationError = null
        )
    }

    fun update_beds(beds:String){
        _room.value = _room.value.copy(
            bedCount = beds,
            bedCountError = null
        )
    }

    fun update_rent(rent:String){
        _room.value = _room.value.copy(
            rentPrice = rent,
            rentPriceError = null
        )
    }

    fun update_deposit(price:String){
        _room.value = _room.value.copy(
            deposit = price,
            depositError = null
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

    fun submit(){
        val current_roon = _room.value
        _room.value = _room.value.copy(
            nameError = validateRoomName(name = current_roon.name),
            bedCountError = validateBeds(beds = current_roon.bedCount),
            depositError = validateDeposit(price = current_roon.deposit),
            rentPriceError = validateRent(price = current_roon.rentPrice)
        )
        if(_room.value.nameError != null || room.value.bedCountError != null
            || room.value.depositError != null
            || room.value.rentPriceError != null
        ){
            return
        }
        viewModelScope.launch {
            try {
                _room.value = _room.value.copy(
                    isLoading = true
                )
                repository.insertRoom(room = _room.value.toRoom())
            }catch(error: Exception){

            }finally{
                _room.value = _room.value.copy(
                    isLoading = false
                )
            }
        }
    }
}