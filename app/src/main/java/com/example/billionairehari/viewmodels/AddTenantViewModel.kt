package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.billionairehari.R
import kotlinx.coroutines.launch

data class TenantData (
    val name:String = "",
    val image: ByteArray? = null,
    val phone:String = "",
    val room:String = "",
    val date:Long = 0L,
    val first_month_rent:Boolean = false,
    val security_deposit: Boolean = false,
    val automatic_remainder:Boolean = false
)

class AddTenantViewModel: ViewModel(){
    private val _tenant = MutableStateFlow(TenantData())
    private val _tenant_error = MutableStateFlow(TenantDataError())

    val tenant = _tenant.asStateFlow()
    val tenant_error = _tenant_error.asStateFlow()

    /** update value **/
    fun update_name(name:String){
        _tenant.value = _tenant.value.copy(name = name)
    }
    fun update_image(image: ByteArray?) {
        _tenant.value = tenant.value.copy(image = image)
    }
    fun update_phone(phone:String){
        _tenant.value = _tenant.value.copy(phone = phone)
    }
    fun update_room(room:String){
        _tenant.value = _tenant.value.copy(room = room)
    }
    fun update_date(date:Long){
        _tenant.value = _tenant.value.copy(date = date)
    }
    fun update_first_month_rent_paid(paid:Boolean) {
        _tenant.value = _tenant.value.copy(first_month_rent = paid)
    }
    fun update_security_deposit(paid:Boolean){
        _tenant.value = _tenant.value.copy(security_deposit = paid)
    }
    fun update_automatic_remainder(remainder:Boolean){
        _tenant.value = _tenant.value.copy(automatic_remainder = remainder)
    }

    fun remove_image(){
        _tenant.value = _tenant.value.copy(image = null)
    }
    /** upload to repository **/
    fun submit(){
        viewModelScope.launch {
            TODO("Save the Data to Repository")
            // save to repostiory
        }
    }
}