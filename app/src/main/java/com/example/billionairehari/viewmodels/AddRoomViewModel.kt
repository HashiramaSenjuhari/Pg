package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RoomData(
    val name:String = "",
    val nameError:String? = null,
    val no_of_beds:String = "",
    val bedError:String? = null,
    val rent_price:String = "",
    val rentPriceError:String? = null,
    val deposit:String = "",
    val depositError:String? = null,
    val features:List<String> = emptyList(),
    val images:List<ByteArray> = emptyList(),
    val isLoading:Boolean = false
)

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val repository: RoomRepository
): ViewModel(){
    private val _room = mutableStateOf(RoomData())
    val room: State<RoomData> get() = _room

    /** update **/
    fun update_name(name:String){
        _room.value = _room.value.copy(
            name = name,
            nameError = null
        )
    }

    fun update_beds(beds:String){
        _room.value = _room.value.copy(
            no_of_beds = beds,
            bedError = null
        )
    }

    fun update_rent(price:String){
        _room.value = _room.value.copy(
            rent_price = price,
            rentPriceError = null
        )
    }

    fun update_deposit(price:String){
        _room.value = _room.value.copy(
            deposit = price,
            depositError = null
        )
    }

    fun update_features(feature:String){
        val oldFeatures = _room.value.features
        _room.value = _room.value.copy(
            features = oldFeatures + feature
        )
    }

    fun remove_feature(feature:String){
        val features = _room.value.features.toMutableList()
        features.remove(feature)
        _room.value = _room.value.copy(
            features = features
        )
    }

    fun update_images(image: ByteArray){
        val oldImages = _room.value.images
        _room.value = _room.value.copy(
            images = oldImages + image
        )
    }

    fun remove_images(index:Int){
        val images = _room.value.images.toMutableList()
        images.removeAt(index)
        _room.value = _room.value.copy(
            images = images
        )
    }

    fun reset(){
        _room.value = RoomData()
    }

    fun submit(){
        _room.value = _room.value.copy(
            nameError = validateName(_room.value.name),
            bedError = validateBeds(_room.value.no_of_beds),
            rentPriceError = validateRent(_room.value.rent_price),
            depositError = validateDeposit(_room.value.deposit)
        )
        if(_room.value.nameError != null
            || _room.value.bedError != null
            || _room.value.rentPriceError != null
            || _room.value.depositError != null){
            return
        }
        _room.value = _room.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            try {
//                repository.insertRoom(room)
                delay(4000)
            }catch(error: Exception){

            }finally {
                _room.value = RoomData()
            }
        }
    }
}

fun validateRoomName(name:String):String? {
    if(name.trim().isEmpty() && name.trim().length < 1) {
        return "please enter valid name"
    }
    return null
}
fun validateBeds(beds:String):String? {
    if(!beds.trim().isEmpty() && beds.toInt() <= 0) {
        return "please enter valid bed count"
    }else if(beds.trim().isEmpty()){
        return "please fill the bed count"
    }else {
        return null
    }
}
fun validateRent(price:String):String? {
    if(!price.trim().isEmpty() && price.toInt() <= 0) {
        return "please enter valid rent price"
    }else if(price.trim().isEmpty()){
        return "please fill the rent price"
    }else {
        return null
    }
}
fun validateDeposit(price:String):String? {
    if(!price.trim().isEmpty() && price.toInt() <= 0) {
        return "please enter valid rent deposit"
    }else if(price.trim().isEmpty()){
        return "please fill the rent deposit"
    }else {
        return null
    }
}