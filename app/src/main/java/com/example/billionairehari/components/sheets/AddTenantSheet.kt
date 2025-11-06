package com.example.billionairehari.components.sheets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.billionairehari.components.Activate
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.DateInput
import com.example.billionairehari.components.DropDown
import com.example.billionairehari.components.FormButton
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.InputType
import com.example.billionairehari.components.Label
import com.example.billionairehari.components.OutlinedInput
import com.example.billionairehari.components.PhoneNumberTransformation
import com.example.billionairehari.components.PhotoPick
import com.example.billionairehari.components.SelectOption
import com.example.billionairehari.components.contacts.dial
import com.example.billionairehari.icons.Phone
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.screens.ROw
import com.example.billionairehari.viewmodels.AddTenantViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun TenantSheet(
    tenant_default: Tenant = Tenant(),
    onReset:() -> Unit,
    onSubmit:(Tenant) -> Unit,
    scrollState: ScrollState,
    context: Context
) {
    val name = remember { mutableStateOf<String>(tenant_default.name) }
    val room = remember { mutableStateOf<String>(tenant_default.room) }
    val phone = remember { mutableStateOf<String>(tenant_default.phone_number) }
    val image = remember { mutableStateOf<ByteArray?>(tenant_default.image) }
    val joining_date = remember { mutableStateOf<Long>(tenant_default.joining_date) }
    val rent_paid = remember { mutableStateOf<Boolean>(tenant_default.rent_paid_first) }
    val deposit = remember { mutableStateOf<Boolean>(tenant_default.deposit) }
    val auto_remainder = remember { mutableStateOf<Boolean>(tenant_default.automatic_remainder) }

    val nameError = rememberSaveable { mutableStateOf<String?>(null) }
    val roomError = rememberSaveable { mutableStateOf<String?>(null) }
    val dateError = rememberSaveable { mutableStateOf<String?>(null) }
    val phoneError = rememberSaveable { mutableStateOf<String?>(null) }

    Mannual(
        name = name.value,
        room = room.value,
        phone = phone.value,
        image = image.value,
        joining_date = joining_date.value,
        rent_paid = rent_paid.value,
        deposit_paid = deposit.value,
        auto_remainder = auto_remainder.value,
        update_name = {
            name.value = it },
        update_room = {
            room.value = it },
        update_phone = {
            phone.value = it },
        update_image = {
            image.value = it },
        update_rent_paid = {
            rent_paid.value = it },
            update_deposit_paid = { deposit.value = it },
        update_joining_date = {
            joining_date.value = it },
        update_auto_remainder = {
            auto_remainder.value = it },
        name_error = nameError.value,
        phone_error = phoneError.value,
        room_error = roomError.value,
        joining_date_error = dateError.value,
        remove_image = {
            image.value = null },
        onReset = {
            onReset() },
        onDial = {
            dial(phone.value, context = context) },
        onSubmit = {
            val tenant = Tenant(
                name = name.value,
                image = image.value,
                room = room.value,
                automatic_remainder = auto_remainder.value,
                phone_number = phone.value,
                joining_date = joining_date.value,
                rent_paid_first = rent_paid.value,
                deposit = deposit.value
            )
            if(ValidateTenant(tenant = tenant, nameError = nameError, dateError = dateError, phoneError = phoneError, roomError = roomError
            )){
                onSubmit(tenant)
            } },
        isLoading = false,
        scrollState = scrollState
    )
}

private fun ValidateTenant(
    tenant:Tenant,
    nameError:MutableState<String?>,
    roomError:MutableState<String?>,
    phoneError:MutableState<String?>,
    dateError:MutableState<String?>
) : Boolean {
    if(tenant.name.isBlank()){
        nameError.value = "Provide Name"
        return false
    }
    if(tenant.room === ""){
        roomError.value = "Please Select Room"
        return false
    }
    if(tenant.phone_number.length < 10 && tenant.phone_number.length > 10){
        phoneError.value = "Provide Correct Number"
        return false
    }
    if(tenant.joining_date === 0L){
        dateError.value = "Please Choose the Date"
        return false
    }
    return true
}

data class DropDownItem(
    val name:String
)

val fakeItems = listOf(
    DropDownItem(name = "Billionairehari"),
    DropDownItem(name = "Billionaire"),
    DropDownItem(name = "Billionaires")
)

@Composable
fun Mannual(
    name:String,
    update_name:(String) -> Unit,
    name_error:String? = null,
    phone:String,
    update_phone:(String) -> Unit,
    phone_error:String? = null,
    room:String,
    update_room:(String) -> Unit,
    room_error:String? = null,
    joining_date:Long,
    update_joining_date:(Long) -> Unit,
    joining_date_error:String? = null,
    rent_paid:Boolean,
    update_rent_paid:(Boolean) -> Unit,
    deposit_paid:Boolean,
    update_deposit_paid:(Boolean) -> Unit,
    image: ByteArray?,
    update_image:(ByteArray) -> Unit,
    remove_image:() -> Unit,
    auto_remainder:Boolean,
    update_auto_remainder:(Boolean) -> Unit,
    onReset:() -> Unit,
    onSubmit:() -> Unit,
    isLoading:Boolean,
    scrollState: ScrollState,
    onDial:() -> Unit
){
    Column (
        modifier = Modifier.fillMaxHeight()
            .verticalScroll(scrollState)
            .animateContentSize()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PhotoPick(
                    onImageChange = {
                            value ->
                        update_image(value)
                    },
                    image = image,
                    onRemoveImage = {
                        remove_image()
                    }
                )
            }
            /* name */
            OutlinedInput(
                label = "Name",
                onValueChange = {
                    update_name(it) },
                leadingIcon = Icons.Outlined.Person,
                value = name,
                modifier = Modifier.fillMaxWidth(),
                error = name_error
            )
            /* phone number */
            OutlinedInput(
                label = "Phone Number",
                keyBoardType = KeyboardType.Phone,
                onValueChange = {
                    update_phone(it)
                } ,
                value =  phone,
                leadingIcon = Icons.Outlined.Call,
                leadingIconInput = {
                    ROw(
                        modifier = Modifier.padding(start = 13.dp, end = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text("+91")
                        VerticalDivider(modifier = Modifier.height(24.dp))
                    }
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        AppButton(
                            onClick = onDial
                        ) {
                            Text("Verify", fontSize = 12.sp)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLength = 10,
                tranformation = PhoneNumberTransformation,
                type = InputType.NUMBER,
                error = phone_error
            )
            /* room and date */
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                DropDown(
                    label = "Choose Room",
                    is_important = true,
                    error = room_error,
                    items =  fakeItems,
                    onChangeValue = {
                        update_room(it)
                    },
                    value = if(room.isEmpty()) "Select Room"
                    else room,
                    size = 0.5f
                )
                DateInput(
                    label = "Joining Date",
                    onDate = {
                        update_joining_date(it) },
                    modifier = Modifier.fillMaxWidth(),
                    date = joining_date,
                )
            }

            /* first month and deposit */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Label(
                        name = "Rent  (First Month)",
                        fontSize =13.sp
                    )
                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                    SelectOption(
                        selectedOption = rent_paid,
                        onValueChange = {
                            update_rent_paid(it)
                        }
                    )
                }
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Label(
                        name = "Security Deposit",
                        fontSize =13.sp
                    )
                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                    SelectOption(
                        selectedOption = deposit_paid,
                        onValueChange = {
                            update_deposit_paid(it)
                        }
                    )
                }
            }
            ROw(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Automatic Rent Remaninder", fontSize =16.sp, fontWeight = FontWeight.Medium)
                Activate(
                    checked = auto_remainder,
                    onCheckChange = {
                        update_auto_remainder(it)
                    }
                )
            }
        }
        FormButton(
            onSubmit = onSubmit,
            onReset = onReset,
            isLoading = isLoading
        )
    }
}