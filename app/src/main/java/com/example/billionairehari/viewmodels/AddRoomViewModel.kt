package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.entity.Owner
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.core.data.repository.OwnerRepository
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.utils.currentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.UUID
import javax.inject.Inject
import kotlin.uuid.Uuid

data class RoomData(
    val name:String = "",
    val nameError:String? = null,
    val location:String = "",
    val locationError:String? = null,
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
    private val ownerDao : OwnerRepository,
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

    fun update_location(location:String){
        _room.value = _room.value.copy(
            location = location,
            locationError = null
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
            depositError = validateDeposit(_room.value.deposit),
            locationError = if(_room.value.location.length < 1) "Please Enter the Valid location" else null
        )
        if(_room.value.nameError != null
            || _room.value.bedError != null
            || _room.value.rentPriceError != null
            || _room.value.depositError != null
            || _room.value.locationError != null){
            return
        }
        _room.value = _room.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val data = _room.value
            val id = UUID.randomUUID().toString()
            try {
                val owner = Owner(
                    id = "1",
                    name = "hari",
                    phone = "8668072363",
                    isVerified = true,
                    pgName = "BillionaireHari",
                    createdAt = currentDateTime()
                )
                ownerDao.createOwner(owner)
                val room = Room(
                    id = id,
                    name = data.name,
                    ownerId = "1",
                    rentPrice = data.rent_price.toInt(),
                    dueDate = currentDateTime(),
                    bedCount = data.no_of_beds.toInt(),
                    deposit = data.deposit.toInt(),
                    features = data.features,
                    images = emptyList(),
                    location = data.location
                )
                repository.insertRoom(room)
                val great = repository.getRoom(roomId = id, ownerId = "1")
                Log.d("RoomCreated", great.toString())
//                delay(1000)
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