package com.example.billionairehari.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.repository.PaymentRepository
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.model.TenantRentRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PaymentMethod { CASH, UPI }

data class RecordData(
    val tenant: TenantSearchCard = TenantSearchCard(),
    val paying_amount:String = "",
    val date:Long = 0L,
    val payment_method: PaymentMethod = PaymentMethod.UPI,
    val isLoading:Boolean = false,
    val payError:String? = null,
    val dateError:String? = null
)

@HiltViewModel
class RecordRentViewModel @Inject constructor(
    private val repository: PaymentRepository,
) : ViewModel() {
    private val _record = mutableStateOf(RecordData(
        tenant = TenantSearchCard()
    ))
    val record: State<RecordData> get() = _record

    /** updating value **/

    fun update_tenant(tenant: TenantSearchCard){
        _record.value = _record.value.copy(
            tenant = tenant
        )
    }

    fun update_paying_amount(price:String){
        _record.value = _record.value.copy(
            paying_amount = price,
            payError = null
        )
    }

    fun update_payment_date(date:Long) {
        _record.value = _record.value.copy(
            date = date,
            dateError = null
        )
    }

    fun update_payment_method(method: PaymentMethod){
        _record.value = _record.value.copy(
            payment_method = method
        )
    }

    fun submit(){
        _record.value = _record.value.copy(
            payError = validateRent(_record.value.paying_amount),
            dateError = validateDate(_record.value.date)
        )
        if(_record.value.dateError !== null || _record.value.payError !== null){
            return
        }
        viewModelScope.launch {
            try {
                _record.value = _record.value.copy(
                    isLoading = true
                )
//                repository.insertPayment()
                delay(1000)
            }catch(error: Exception){

            }finally {
                _record.value = _record.value.copy(
                    isLoading = false
                )
//                _record.value = RecordData()
            }
        }
    }
}