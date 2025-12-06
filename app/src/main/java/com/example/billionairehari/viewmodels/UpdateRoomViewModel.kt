package com.example.billionairehari.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class RoomUpdateData(
    val name:String,
    val images:List<String>,
    val count:String,
    val rent:String,
    val deposit:String,
    val features:List<String>,
    val isLoading: Boolean = false,
    val nameError:String? = null,
    val countError:String? = null,
    val rentError:String? = null,
    val depositError:String? = null
)

@HiltViewModel
class UpdateRoomViewModel(
    private val repository: RoomRepository,
    private val data: Room
) : ViewModel() {
    /**
     *  architecture
     *
     *  collect data from room
     *  and pass it to data class
     *
     * **/
    private val _room = mutableStateOf<RoomUpdateData>(
        RoomUpdateData(
            name = data.name,
            count = data.total_beds,
            features = data.features,
            images = data.images,
            deposit = data.deposit_per_tenant,
            rent = data.rent_per_tenant
        )
    )

    val room: State<RoomUpdateData> = _room

    /** update **/

    fun update_name(name:String) {
        _room.value = _room.value.copy(
            name = name,
            nameError = null
        )
    }

    fun update_beds(beds:String){
        _room.value = _room.value.copy(
            count = beds,
            countError = null
        )
    }

    fun update_rent(rent:String){
        _room.value = _room.value.copy(
            rent = rent,
            rentError = null
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
            countError = validateBeds(beds = current_roon.count),
            depositError = validateDeposit(price = current_roon.deposit),
            rentError = validateRent(price = current_roon.rent)
        )
        if(_room.value.nameError != null || room.value.countError != null
            || room.value.depositError != null
            || room.value.rentError != null
        ){
            return
        }
        viewModelScope.launch {
            try {
                _room.value = _room.value.copy(
                    isLoading = true
                )
                delay(4000)
            }catch(error: Exception){

            }finally{
                _room.value = _room.value.copy(
                    isLoading = false
                )
            }
        }
    }
}

class UpdateRoomFactory(
    private val room: Room
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateRoomViewModel::class.java)){
            return UpdateRoomViewModel(data = room) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}