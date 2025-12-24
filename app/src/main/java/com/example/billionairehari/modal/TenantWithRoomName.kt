package com.example.billionairehari.modal

import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.billionairehari.components.sheets.Mannual
import com.example.billionairehari.viewmodels.AddTenantViewModel

data class TenantModal (
    val name:String = "",
    val phone:String = "",
    val room:String = "",
    val joining_date:Long = 0L,
    val rent_paid: Boolean = false,
    val deposit:Boolean = false,
    val photo: Uri? = null,
    val auto_remainder:Boolean = false
)

@Composable
fun AddTenantWithRoomName(
    name:String,
    scrollState: ScrollState
) {
//    val tenant = hiltViewModel<TenantViewModel>()
//    var tenant_value = tenant.tenant.collectAsState().value
    val name = remember { mutableStateOf<String>("") }
    val room = remember { mutableStateOf<String>("") }
    val phone = remember { mutableStateOf<String>("") }
    val image = remember { mutableStateOf<ByteArray?>(null) }
    val joining_date = remember { mutableStateOf<Long>(0L) }
    val rent_paid = remember { mutableStateOf<Boolean>(false) }
    val deposit = remember { mutableStateOf<Boolean>(false) }
    val auto_remainder = remember { mutableStateOf<Boolean>(false) }
    Mannual(

        name = name.value,
        update_name = {

        },
        phone = phone.value,
        update_phone = {
            phone.value = it
        },
        rent_paid = rent_paid.value,
        update_rent_paid = {
            rent_paid.value = it
        },
        deposit_paid = deposit.value,
        update_deposit_paid = {
            deposit.value = it
        },
        image = image.value,
        update_image = {
            image.value = it
        },
        room = room.value,
        update_room = {
            room.value = it
        },
        auto_remainder = auto_remainder.value,
        update_auto_remainder = {
            auto_remainder.value = it
        },
        phone_error = "",
        room_error = "",
        remove_image = {
            image.value = null
        },
        isLoading = false,
        onSubmit = {

        },
        onReset = {

        },
        scrollState = scrollState,
        onDial = {},
        rooms = emptyList(),
        roomId = "",
        update_roomId = {}
    )
}