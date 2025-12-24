package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.core.data.local.entity.PaymentStatus
import com.example.billionairehari.core.data.local.entity.PaymentType
import com.example.billionairehari.core.data.repository.PaymentRepository
import com.example.billionairehari.core.data.repository.TenantRepository
import com.example.billionairehari.utils.combineDaytoCurrentDate
import com.example.billionairehari.utils.currentDateTime
import com.example.billionairehari.utils.currentMonthInt
import com.example.billionairehari.utils.currentYear
import com.example.billionairehari.utils.generateUUID
import com.example.billionairehari.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


data class RecordDataUiState(
    val tenantAndRent: TenantDao.TenantWithRoomRentCard? = null,
    val amount:String = "",
    val paymentDate:Long = 0L,
    val paymentType: PaymentType = PaymentType.CASH,

    val isLoading:Boolean = false,

    val tenantError:String? = null,
    val dateError:String? = null,
    val amountError:String? = null
){
    val paymentStatus: PaymentStatus
        get() = if(this.amount.toIntOrNull()!! < tenantAndRent?.rentPrice!!){
        PaymentStatus.PARTIAL
    }else {
        PaymentStatus.PAID
    } // this get() runs the paymentStatus and parse the amount and gives enum
}

fun RecordDataUiState.toRoom(ownerId:String) : Payment = Payment(
    id = generateUUID(),
    ownerId = ownerId,
    tenantId = tenantAndRent?.id!!,
    roomId = tenantAndRent?.roomId!!,

    amount = amount.toInt(),
    paymentType = paymentType,
    paymentStatus = paymentStatus,
    dueDate = combineDaytoCurrentDate(tenantAndRent?.dueDay!!),
    paymentDate = paymentDate.toDateString(),
    updatedAt = currentDateTime(),
    createdAt = currentDateTime()
)

@HiltViewModel
class RecordRentViewModel @Inject constructor(
    private val tenant_repository: TenantRepository,
    private val repository: PaymentRepository,
) : ViewModel() {

    /** SEARCH PART - START **/
    private val tenantsWithRent: StateFlow<List<TenantDao.TenantWithRoomRentCard>> = tenant_repository
        .getTenantSearchCards(ownerId = "1")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    fun update_query(query:String){
        _query.value = query
    }

    val search_result = query
        .debounce(300L)
        .distinctUntilChanged()
        .combine(tenantsWithRent){
            query,cards ->
            cards.filter { card -> card.tenantName.contains(query) || card.roomName.contains(query) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    /** SEARCH PART - END **/

    /** RECORD DATA - START **/
    private val _record: MutableState<RecordDataUiState> = mutableStateOf(RecordDataUiState())
    val record: State<RecordDataUiState> get() = _record

    fun update_tenant(tenant: TenantDao.TenantWithRoomRentCard){
        _record.value = _record.value.copy(
            tenantAndRent = tenant,
            tenantError = null
        )
    }

    fun update_paying_amount(price:String){
        _record.value = _record.value.copy(
            amount = price,
            amountError = null
        )
    }

    fun update_payment_date(date:Long) {
        _record.value = _record.value.copy(
            paymentDate = date,
            dateError = null
        )
    }

    fun update_payment_method(method: PaymentType){
        _record.value = _record.value.copy(
            paymentType = method
        )
    }

    fun submit(is_open: MutableState<Boolean>){
        val current_value = _record.value
        Log.d("CLICKED",current_value.toString())

        /** Error handling **/
        _record.value = _record.value.copy(
            dateError = validateDate(current_value.paymentDate),
            tenantError = validateTenant(tenant = current_value.tenantAndRent),
            amountError = validateAmount(current_value.amount)
        )
        val current_error = _record.value
        Log.d("ERRORSGREAT",current_error.toString())
        if(current_error.dateError != null
            || current_error.tenantError != null
            || current_error.amountError != null){
            return
        }

        /** Saving to room **/
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val current_data = _record.value
                _record.value = _record.value.copy(
                    isLoading = true
                )
                Log.d("RentCARD",_record.value.toString())

                val payment_data =  current_data.toRoom(
                    ownerId = "1"
                )
                repository.insertPayment(payment = payment_data)
                is_open.value = false
            }catch(error: Exception){

            }finally {
                _record.value = RecordDataUiState()
            }
        }
    }
    /** RECORD PART - END **/
}

fun validateTenant(tenant: TenantDao.TenantWithRoomRentCard? = null):String?{
    if(tenant == null){
        return "Please Choose the Tenant to Proceed With furthur"
    }
    return null
}

fun validateAmount(amount:String):String? {
    val price = amount.toIntOrNull() ?: 0
    Log.d("MAXHARI",price.toString())
    if(price > 999999){
        return "Max of 6 digit allowed"
    }
    else if(price < 1){
        return "Please Enter Valid amount"
    }
    return null
}