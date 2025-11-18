package com.example.billionairehari.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PaymentMethod { CASH, UPI }

data class RecordData(
    val tenant: TenantSearchCard = TenantSearchCard(),
    val paying_amount:String = "",
    val date:Long = 0L,
    val payment_method: PaymentMethod = PaymentMethod.UPI,
    val isLoading:Boolean = false
)

class RecordRentViewModel : ViewModel() {
    private val _record = mutableStateOf(RecordData())
    val record: State<RecordData> get() = _record

    /** updating value **/

    fun update_tenant(tenant: TenantSearchCard){
        _record.value = _record.value.copy(
            tenant = tenant
        )
    }

    fun update_paying_amount(price:String){
        _record.value = _record.value.copy(
            paying_amount = price
        )
    }

    fun update_payment_date(date:Long) {
        _record.value = _record.value.copy(
            date = date
        )
    }

    fun update_payment_method(method: PaymentMethod){
        _record.value = _record.value.copy(
            payment_method = method
        )
    }

    fun submit(){
        viewModelScope.launch {
            try {
                _record.value = _record.value.copy(
                    isLoading = true
                )
                delay(1000)
            }catch(error: Exception){

            }finally {
                _record.value = RecordData()
            }
        }
    }
}