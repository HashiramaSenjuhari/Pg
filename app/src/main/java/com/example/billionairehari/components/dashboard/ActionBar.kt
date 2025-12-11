package com.example.billionairehari.components.dashboard

import android.util.Log
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.billionairehari.model.TenantRentRecord
import com.example.billionairehari.viewmodels.AddRoomViewModel
import com.example.billionairehari.viewmodels.RoomsViewModel
import com.example.billionairehari.viewmodels.UpdateRoomViewModel
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
            current_action.value = MODAL_TYPE.ADD_TENANT()
        }),
        Action(name = "Add Rent", icon = ContactIcon, action = {
            is_open.value = true
            current_action.value = MODAL_TYPE.UPDATE_TENANT_RENT(tenant = TenantRentRecord())
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
    scrollState: ScrollState,
    viewmodel: AddRoomViewModel = hiltViewModel()
){
    val room = viewmodel.room.value

    AddRoomSheet(
        room_name = room.name,
        bed_count = room.no_of_beds,
        rent = room.rent_price,
        deposit = room.deposit,
        features = room.features,
        images = room.images,
        isLoading = room.isLoading,

        onRoomNameChange = {
            viewmodel.update_name(it)
        },
        onRentChange = {
            viewmodel.update_rent(it)
        },
        onDepositChange = {
            viewmodel.update_deposit(it)
        },
        onNoOfBedChange = {
            viewmodel.update_beds(it)
        },
        onImageAdd = {
            viewmodel.update_images(it)
        },
        onImageRemove = {
            viewmodel.remove_images(it)
        },
        onImageError = {

        },
        onFeatureAdd = {
            viewmodel.update_features(it)
        },
        onFeatureRemove = {
            viewmodel.remove_feature(it)
        },

        images_error = null,
        room_name_error = room.nameError,
        beds_error = room.bedError,
        rent_error = room.rentPriceError,
        deposit_error = room.depositError,
        features_error = null,

        scrollState = scrollState,
        onSubmit = {
            viewmodel.submit()
        },
        onReset = {
            viewmodel.reset()
        }
    )
}


@Composable
fun UpdateRoomSheet(
    room: Room,
    scrollState: ScrollState,
){
    val owner = LocalViewModelStoreOwner.current

    /** viewmodelStoreOwner refers the activity
     *  the viewmodel lives as long as the compose is living
     * **/

    Log.d("RoomViewModel",room.toString())
    val viewmodel: UpdateRoomViewModel = hiltViewModel()

    val room = viewmodel.room.value

    AddRoomSheet(
        room_name = room.name,
        bed_count = room.count.toString(),
        rent = room.rent,
        deposit = room.deposit,
        features = room.features,
        images = emptyList(),
        isLoading = false,

        onRoomNameChange = {
            viewmodel.update_name(it)
        },
        onRentChange = {
            viewmodel.update_rent(it)
        },
        onDepositChange = {
            viewmodel.update_deposit(it)
        },
        onNoOfBedChange = {
            viewmodel.update_beds(it)
        },
        onImageAdd = {
        },
        onImageRemove = {
        },
        onImageError = {

        },
        onFeatureAdd = {
            viewmodel.update_features(it)
        },
        onFeatureRemove = {
            viewmodel.remove_features(it)
        },

        images_error = null,
        room_name_error = room.nameError,
        beds_error = room.countError,
        rent_error = room.rentError,
        deposit_error = room.depositError,
        features_error = null,

        scrollState = scrollState,
        onSubmit = {
            viewmodel.submit()
        },
        onReset = {
//            viewmodel.reset()
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
                    modifier = Modifier.border(width = 1.3.dp, color = Color.Black.copy(0.1f),shape = CircleShape)
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