package com.example.billionairehari.components.dashboard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.components.sheets.AddRoomSheet
import com.example.billionairehari.components.sheets.AnnounceSheet
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.icons.AnnounceIcon
import com.example.billionairehari.icons.ContactIcon
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.model.Room
import com.example.billionairehari.viewmodels.RoomsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

data class Action(
    val name:String,
    val icon:ImageVector,
    val action:() -> Unit
)

@Composable
fun ActionBar(
    navAction: NavigationAction,
    is_open: MutableState<Boolean>,
    current_action: MutableState<MODAL_TYPE>
){
    val actions = listOf<Action>(

        Action(name = "Announce", icon = AnnounceIcon, action = {
            is_open.value = true
            current_action.value = MODAL_TYPE.ANNOUNCE(reveivers = emptyList())
        }),
        Action(name = "Add Room", icon = RoomIcon, action = {
            is_open.value = true
            current_action.value = MODAL_TYPE.ADD_ROOM
        }),
        Action(name = "Add Tenant", icon = TenantIcon, action = {
            is_open.value = true
            current_action.value = MODAL_TYPE.ADD_TENANT
        }),
        Action(name = "Add Rent", icon = ContactIcon, action = {
            is_open.value = true
            current_action.value = MODAL_TYPE.UPDATE_TENANT_RENT()
        })
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text("Quick Actions", fontSize = 16.sp,modifier = Modifier.offset(6.dp))
            Row(
                modifier = Modifier
            ) {
                actions.forEach { (name, icon, action) ->
                    ActionButton(
                        name = name,
                        icon = icon,
                        onClick = action
                    )
                }
            }
        }
    }
}

@Composable
fun RoomSheet(
    room_default: Room = Room(),
    onReset:() -> Unit,
    onSubmit:(Room) -> Unit,
    scrollState: ScrollState
){
    val name = rememberSaveable { mutableStateOf<String>(room_default.name) }
    val images = rememberSaveable { room_default.images.toMutableList() }
    val deposit = rememberSaveable { mutableStateOf<String>(room_default.deposit_per_tenant) }
    val bed_count = rememberSaveable { mutableStateOf<String>(room_default.total_beds) }
    val rent = rememberSaveable { mutableStateOf<String>(room_default.rent_per_tenant) }
    val features = rememberSaveable { room_default.features.toMutableList() }

    var name_error = rememberSaveable { mutableStateOf<String?>(null) }
    var bed_count_error = rememberSaveable { mutableStateOf<String?>(null) }
    var rent_error = rememberSaveable { mutableStateOf<String?>(null) }
    var features_error = rememberSaveable { mutableStateOf<String?>(null) }
    var deposit_error = rememberSaveable { mutableStateOf<String?>(null) }
    val image_error = rememberSaveable { mutableStateOf<String?>(null) }

    AddRoomSheet(
        images = images,
        deposit = deposit.value,
        bed_count = bed_count.value,
        features = features,
        rent = rent.value,
        room_name = name.value,
        onImageRemove = {
            images.removeAt(it)
        },
        onImageError = {

        },
        onImageAdd = {
            images.add(it)
        },
        onAminitiesChange = {
            features.add(it.name)
        },
        onDepositChange = {
            deposit.value = it
        },
        onNoOfBedChange = {
            bed_count.value = it
        },
        onRentChange = {
            rent.value = it
        },
        onRoomNameChange = {
            name.value = it
        },
        onFeatureRemove = {
            features.removeAt(it)
        },
        images_error = image_error.value,
        deposit_error = deposit_error.value,
        rent_error = rent_error.value,
        beds_error = bed_count_error.value,
        room_name_error = name_error.value,
        features_error = features_error.value,
        onReset = onReset,
        scrollState = scrollState,
        onSubmit = {
            val room = Room(
                    name = name.value,
                    features = features,
                    rent_per_tenant = rent.value,
                    deposit_per_tenant = deposit.value,
                    images = images,
                    total_beds = bed_count.value
                )
            if(ValidateRoom(
                    value = room,
                    name_error = name_error,
                    beds_error = bed_count_error,
                    deposit_error = deposit_error,
                    rent_error = rent_error,
                    features_error = features_error,
                    images_error = image_error
                )){
                onSubmit(
                    room
                )
            }

        }
    )
}

private fun ValidateRoom(
    name_error: MutableState<String?>,
    images_error: MutableState<String?>,
    beds_error: MutableState<String?>,
    rent_error: MutableState<String?>,
    features_error: MutableState<String?>,
    deposit_error: MutableState<String?>,
    value:Room
) : Boolean {
    if(value.name.isBlank()){
        name_error.value = "Please Provide Name"
        return false
    }
    if(value.images.size > 4){
        images_error.value = "Maximum of 4 images is allowed"
        return false
    }
    if((value.total_beds.toIntOrNull() ?: 0) <= 0){
        beds_error.value = "Minimum of 1 bed is Needed"
        return false
    }
    if((value.deposit_per_tenant.toIntOrNull() ?: 0) <= 0){
        deposit_error.value = "Please Enter the Deposit Amount"
        return false
    }
    if((value.rent_per_tenant.toIntOrNull() ?: 0) <= 0){
        rent_error.value = "Please Enter Rent amount"
        return false
    }
    if(value.features.size > 10){
        features_error.value = "Maximum of 10 features is allowed"
        return false
    }
    return true
}

@Composable
fun ActionButton(
    name:String,
    icon: ImageVector,
    onClick:() -> Unit
){
    DisableRippleEffect {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 13.dp, vertical = 6.dp),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.wrapContentSize()
                .indication(interactionSource = remember { MutableInteractionSource() }, indication = null)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = name,
                    modifier = Modifier.border(width = 1.3.dp, color = Color(0xFF909090),shape = CircleShape)
                        .background(Color.White)
                        .padding(16.dp),
                    tint = Color(0xFF404040)
                )
                Text(name, fontSize =12.sp,color = Color(0xFF404040), textAlign = TextAlign.Center)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisableRippleEffect(content:@Composable () -> Unit) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        content()
    }
}