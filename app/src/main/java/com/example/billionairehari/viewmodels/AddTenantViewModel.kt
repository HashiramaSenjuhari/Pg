package com.example.billionairehari.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.billionairehari.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TenantData (
    val name:String = "",
    val nameError:String? = null,
    val image: ByteArray? = null,
    val imageError:String? = null,
    val phone:String = "",
    val phoneNumberError:String? = null,
    val room:String = "",
    val roomError:String? = null,
    val date:Long = 0L,
    val dateError:String? = null,
    val first_month_rent:Boolean = false,
    val security_deposit: Boolean = false,
    val automatic_remainder:Boolean = false,
    val isLoading:Boolean = false
)

class AddTenantViewModel(
    private val room:String? = null
): ViewModel(){
    var tenant = mutableStateOf(TenantData(room = room ?: ""))
        private set

    /** update value **/
    fun update_name(name:String){
        tenant.value = tenant.value.copy(name = name, nameError = null)
    }
    fun update_image(image: ByteArray?) {
        tenant.value = tenant.value.copy(image = image, imageError = null)
    }
    fun update_phone(phone:String){
        tenant.value = tenant.value.copy(phone = phone, phoneNumberError = null)
    }
    fun update_room(room:String){
        tenant.value = tenant.value.copy(room = room, roomError = null)
    }
    fun update_date(date:Long){
        tenant.value = tenant.value.copy(date = date, dateError = null)
    }
    fun update_first_month_rent_paid(paid:Boolean) {
        tenant.value = tenant.value.copy(first_month_rent = paid)
    }
    fun update_security_deposit(paid:Boolean){
        tenant.value = tenant.value.copy(security_deposit = paid)
    }
    fun update_automatic_remainder(remainder:Boolean){
        tenant.value = tenant.value.copy(automatic_remainder = remainder)
    }

    fun remove_image(){
        tenant.value = tenant.value.copy(image = null)
    }
    /** upload to repository **/
    fun submit(){
        tenant.value = tenant.value.copy(
            nameError = validateName(tenant.value.name),
            phoneNumberError = validatePhoneNumber(tenant.value.phone),
            roomError = validateRoom(tenant.value.room),
            dateError = validateDate(tenant.value.date)
        )
        if(tenant.value.nameError != null
            || tenant.value.phoneNumberError != null
            || tenant.value.roomError != null
            || tenant.value.dateError != null){
            return
        }
        tenant.value = tenant.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            try {
                delay(4000)
            }catch(error: Exception){

            }finally {
                tenant.value = TenantData()
            }
        }
    }
}

fun validateName(name:String):String? {
    if(name.trim().isEmpty()){
        return "please enter the name"
    }
    return null
}

fun validatePhoneNumber(phoneNumber:String):String? {
    if(phoneNumber.trim().length < 10){
        return "please enter valid phone number"
    }
    return null
}

fun validateRoom(room:String):String? {
    if(room.trim().isEmpty()){
        return "please choose room"
    }
    return null
}

fun validateDate(date:Long):String? {
    if(date.equals(0L)){
        return "please choose date"
    }
    return null
}

class AddTenantFactory(
    private val room:String? = null
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTenantViewModel::class.java)){
            return AddTenantViewModel(room = room) as T
        }else {
            throw IllegalArgumentException("Please Provide Correct Class")
        }
    }
}