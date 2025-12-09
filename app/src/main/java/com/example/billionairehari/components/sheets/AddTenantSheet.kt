package com.example.billionairehari.components.sheets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.billionairehari.components.AadharNumberTransformation
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
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.AddTenantViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TenantSheet(
    room:String? = null,
    scrollState: ScrollState,
    context: Context
) {

    Log.d("ROOM_GREAT",room.toString())
    val owner = LocalViewModelStoreOwner.current

    val viewmodel: AddTenantViewModel = hiltViewModel()
    val aadhar = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }

    val tenant = viewmodel.tenant.value

    val errors = tenant

    Mannual(
        room =  tenant.room,
        phone = tenant.phone,
        image = tenant.image,
        aadhar = aadhar.value,
        otp = otp.value,
        joining_date = tenant.date,
        rent_paid = tenant.first_month_rent,
        deposit_paid = tenant.security_deposit,
        auto_remainder = tenant.automatic_remainder,
        update_room = { viewmodel.update_room(it) },
        update_phone = { viewmodel.update_phone(it) },
        update_image = { viewmodel.update_image(it) },
        update_rent_paid = { viewmodel.update_first_month_rent_paid(it) },
        update_deposit_paid = { viewmodel.update_security_deposit(it) },
        update_joining_date = { viewmodel.update_date(it) },
        update_auto_remainder = { viewmodel.update_automatic_remainder(it) },
        update_aadhar = { aadhar.value = it },
        update_otp = { otp.value = it },

        aadhar_error = errors.aadharError,
        phone_error = errors.phoneNumberError,
        room_error = errors.roomError,
        joining_date_error = errors.dateError,

        remove_image = { viewmodel.remove_image() },
        onReset = {},
        onDial = { dial(tenant.phone, context = context) },
        onSubmit = { viewmodel.submit() },
        scrollState = scrollState,

        SendOtp = {
            viewmodel.verify_aadhar(aadhar = aadhar.value)
        },
        VerifyOtp = {
            viewmodel.verify_opt(otp = otp.value)
        },

        /** loading **/
        isLoading = tenant.isLoading,
        loadingOtp = tenant.isSendingOtp,
        verifyingOtp = tenant.isVerfyingOtp,

        /** open **/
        isOtpSent = tenant.isOptSent
    )
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

    aadhar:String,
    update_aadhar:(String) -> Unit,
    aadhar_error:String?,

    otp:String,
    update_otp:(String) -> Unit,

    /** loading **/
    loadingOtp:Boolean,
    verifyingOtp:Boolean,
    isOtpSent:Boolean,

    auto_remainder:Boolean,
    update_auto_remainder:(Boolean) -> Unit,

    SendOtp:() -> Unit,
    VerifyOtp:() -> Unit,

    onReset:() -> Unit,
    onSubmit:() -> Unit,
    isLoading:Boolean,
    onDial:() -> Unit,

    scrollState: ScrollState,
){
    val sendOtp = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(scrollState)
                .animateContentSize()
                .padding(vertical = 6.dp),
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    OutlinedInput(
                        leadingIcon = Icons.Outlined.Person,
                        label = "Aadhar Number",
                        value = aadhar,
                        onValueChange = {
                            update_aadhar(it)
                        },
                        maxLength = 12,
                        type = InputType.NUMBER,
                        keyBoardType = KeyboardType.Number,
                        tranformation = AadharNumberTransformation,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            AppButton(
                                padding = PaddingValues(horizontal = 13.dp),
                                onClick = SendOtp,
                            ) {
                                if(loadingOtp){
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 1.dp)
                                }else {
                                    Text("Send OTP", fontSize = 12.sp)
                                }
                            }
                        },
                        readOnly = loadingOtp,
                        error = aadhar_error
                    )
                    if(isOtpSent){
                        val time = remember { mutableStateOf(10) }
                        LaunchedEffect(
                            time.value
                        ) {
                            if(time.value > 0){
                                delay(1000)
                                time.value -= 1
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OutlinedInput(
                                leadingIcon = Icons.Default.Person,
                                label = "OTP",
                                value = otp,
                                onValueChange = {
                                    update_otp(it)
                                },
                                maxLength = 12,
                                type = InputType.NUMBER,
                                keyBoardType = KeyboardType.Number,
                                tranformation = AadharNumberTransformation,
                                modifier = Modifier.fillMaxWidth(),
                                trailingIcon = {
                                    AppButton(
                                        padding = PaddingValues(horizontal = 13.dp),
                                        onClick = VerifyOtp,
                                    ) {
                                        Text("Verify OTP", fontSize = 12.sp)
                                    }
                                }
                            )
                            if(time.value > 0){
                                Text("Reset password in 0:${time.value.toString().padStart(2,'0')}", fontSize = 13.sp,color = Color.Red)
                            }else {
                                Text(
                                    "Resend OTP",
                                    textDecoration = TextDecoration.Underline,
                                    modifier =  Modifier.clickable(
                                        onClick = {
                                            Toast.makeText(context,"Resent OTP to phone number",Toast.LENGTH_SHORT).show()
                                            time.value = 10
                                        }
                                    )
                                )
                            }
                        }
                    }
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

                }
                /* room and date */
                Row(
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    verticalAlignment = Alignment.Top
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
                        error = joining_date_error
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
        }
        FormButton(
            onReset = onReset,
            isLoading = isLoading,
            onSubmit = onSubmit
        )
    }
}