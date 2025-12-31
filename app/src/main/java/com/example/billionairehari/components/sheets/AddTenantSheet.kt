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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
import com.example.billionairehari.components.dialogs.BottomTenantSearchCard
import com.example.billionairehari.icons.Phone
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.BottomDialogSearchScreen
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.AddTenantViewModel
import com.example.billionairehari.viewmodels.BottomDialogRoomSearchViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TenantSheet(
    room: Pair<String,String>? = null,
    scrollState: ScrollState,
    context: Context,
    viewmodel: AddTenantViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        if(room != null){
            viewmodel.update_room(roomId = room.first, room = room.second)
        }
    }
    val room = viewmodel.room.collectAsState()
    val owner = LocalViewModelStoreOwner.current

    val aadhar = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }

    val tenant = viewmodel.tenant.value

    val errors = tenant

    Mannual(
        roomId = tenant.roomId,
        room = tenant.room,
        name = tenant.name,
        phone = tenant.phone,
        image = tenant.image,
        rent_paid = tenant.first_month_rent,
        deposit_paid = tenant.security_deposit,
        auto_remainder = tenant.automatic_remainder,
        update_room = { viewmodel.update_room(roomId = it.first, room = it.second) },
        update_name = { viewmodel.update_name(it) },
        update_phone = { viewmodel.update_phone(it) },
        update_image = { viewmodel.update_image(it) },
        update_rent_paid = { viewmodel.update_first_month_rent_paid(it) },
        update_deposit_paid = { viewmodel.update_security_deposit(it) },
        update_auto_remainder = { viewmodel.update_automatic_remainder(it) },

        phone_error = errors.phoneNumberError,
        room_error = errors.roomError,

        remove_image = { viewmodel.remove_image() },
        onReset = {},
        onDial = { dial(tenant.phone, context = context) },
        onSubmit = { viewmodel.submit() },
        scrollState = scrollState,

        /** loading **/
        isLoading = tenant.isLoading
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
    name:String,
    update_name:(String) -> Unit,
    name_error:String? = null,

    phone:String,
    update_phone:(String) -> Unit,
    phone_error:String? = null,

    roomId:String,
    room:String,
    update_room:(Pair<String,String>) -> Unit,
    room_error:String? = null,

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
    onDial:() -> Unit,

    scrollState: ScrollState,
    viewmodel: BottomDialogRoomSearchViewModel = hiltViewModel()
){
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.results.collectAsState()
    val context = LocalContext.current
    val isOpen = remember { mutableStateOf(false) }

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
                        value = name,
                        onValueChange = {
                            update_name(it)
                        },
                        leadingIcon = Icons.Outlined.Person,
                        label = "Name",
                        modifier = Modifier.fillMaxWidth()
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

                }
                /* room and date */
                Row(
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    OutlinedInput(
                        value = room,
                        onValueChange = {},
                        onClick = {
                            isOpen.value = true
                        },
                        readOnly = true,
                        label = "Select Rooms",
                        modifier = Modifier.fillMaxWidth(1f),
                        leadingIcon = RoomIcon
                    )
                }

                /* first month and deposit */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ROw (
                        modifier = Modifier.weight(1f)
                    ) {
                        Label(
                            name = "Deposit Amount",
                            fontSize =16.sp
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
        BottomDialogSearchScreen(
            value = query.value,
            onChangeValue = {
                viewmodel.update_query(it)
            },
            search_label = "Search Room",
            is_open = isOpen
        ){
            results.value.forEach {
                RoomSearchCard(
                    name = it.name,
                    location = "up",
                    available = it.available_beds,
                    onClick = {
                        update_room(it.id to it.name)
                        isOpen.value = false
                        viewmodel.update_query("")
                    }
                )
            }
        }
}

@Composable
fun RoomSearchCard(
    name:String,
    location:String,
    available:Int,
    onClick:() -> Unit
){
    val isAvailable = if(available > 0) true else false
    val textColor = if(isAvailable) Color.Black else Color.Black.copy(0.4f)
        ROw(
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    role = Role.Button,
                    enabled = isAvailable,

                ).fillMaxWidth()
                .drawBehind{
                    drawLine(
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        color = Color.Black.copy(0.4f),
                        strokeWidth = 1f
                    )
                }
                .padding(13.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(name,fontSize= 16.sp, color = textColor)
                Text(location, fontSize = 13.sp,color = Color.Black.copy(0.6f))
            }
            Badge(
                modifier = Modifier.clip(CircleShape)
                    .width(80.dp)
                    .background(
                        if(isAvailable) Color(0xFF5ec639) else {
                            Color(0xFFff595d)
                        }
                    )
                    .padding(vertical = 6.dp),
                containerColor = Color.Transparent,
                contentColor = if(isAvailable) Color.Black else Color.White
            ) {
                Text(if(isAvailable) "$available Available" else "Full")
            }
        }
}
