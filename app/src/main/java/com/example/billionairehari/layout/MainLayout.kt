package com.example.billionairehari.layout

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.billionairehari.components.dashboard.RoomSheet
import com.example.billionairehari.components.dashboard.TopBar
import com.example.billionairehari.components.dashboard.UpdateRoomSheet
import com.example.billionairehari.components.dialogs.RemoveDialog
import com.example.billionairehari.components.sheets.AnnounceSheet
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.components.sheets.Brodcast
import com.example.billionairehari.components.sheets.SelectedType
import com.example.billionairehari.components.sheets.TenantSheet
import com.example.billionairehari.modal.RecordRentPriceModal
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.GetRoomTenantCountViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

sealed class MODAL_TYPE {
    object ADD_ROOM: MODAL_TYPE()
    object NONE: MODAL_TYPE()
    data class ADD_TENANT(val room:String? = null): MODAL_TYPE()
    data class ADD_TENANT_WITH_PRE_ROOM(val roomName:String): MODAL_TYPE()
    data class UPDATE_TENANT(val tenant: Tenant): MODAL_TYPE()
    data class UPDATE_ROOM(val id: String): MODAL_TYPE()
    data class UPDATE_TENANT_RENT(val id:String? = null): MODAL_TYPE()
    data class ANNOUNCE(val reveivers:List<SelectedType>? = null): MODAL_TYPE()
}

sealed class DIALOG_TYPE {
    data class DELETE_ROOM(val id:String): DIALOG_TYPE()
    data class DELETE_TENANT(val id:String): DIALOG_TYPE()
    object NONE: DIALOG_TYPE()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    startDestination:String = "dashboard",
){
    // states
    val scrollState = rememberScrollState()
    val snackbarHost = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded =  true)

    // navigation
    val navBackStack = navController.currentBackStackEntryAsState()
    val destination = navBackStack.value?.destination

    // mutableState
    val is_open = rememberSaveable { mutableStateOf<Boolean>(true) }
    val is_dialog_open = rememberSaveable { mutableStateOf<Boolean>(false) }
    val current_action = remember { mutableStateOf<MODAL_TYPE>(MODAL_TYPE.NONE) }
    val current_dialog_action = remember { mutableStateOf<DIALOG_TYPE>(DIALOG_TYPE.NONE) }

    val title = when (current_action.value){
        MODAL_TYPE.ADD_ROOM -> "Add Room"
        is MODAL_TYPE.ADD_TENANT -> "Add Tenant"
        is MODAL_TYPE.ANNOUNCE -> "Announce"
        is MODAL_TYPE.UPDATE_TENANT_RENT -> "Record Rent"
        is MODAL_TYPE.UPDATE_ROOM -> "Update Room"
        MODAL_TYPE.NONE -> ""
        is MODAL_TYPE.ADD_TENANT_WITH_PRE_ROOM -> "Add Tenant"
        else -> ""
    }

    // destination
    val route = destination?.route?.split("/")
    val route_size = route?.size ?: 0
    val path = route?.get(0) ?: ""

    val activity = LocalView.current.context as Activity
    SideEffect {
        activity.window.statusBarColor = Color.White.toArgb()
        val wc = WindowInsetsControllerCompat(activity.window,activity.window.decorView)
        wc.isAppearanceLightStatusBars = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(0.dp),
        containerColor = Color.White,
        snackbarHost = {
            SnackbarUi(
                snackbarHostState = snackbarHost
            )
        },
        topBar = {
            Header(
                route_size = route_size,
                path = path,
                navController = navController
            )
        },
        bottomBar = {
            if(route_size === 1){
                BottomBar(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingButton(
                is_open = is_open,
                current_action = current_action,
                path = path,
                navBackStack = navBackStack,
                route_size = route_size,
                route = route
            )
        }
    ) {
        innerPadding ->
        AppLayout(
            padding = innerPadding,
            startDestination = startDestination,
            navController = navController,
            is_open = is_open,
            is_dialog_open = is_dialog_open,
            current_action = current_action,
            current_dialog_action = current_dialog_action
        )
        if(current_action.value != MODAL_TYPE.NONE){
            ModalUi(
                title = title,
                current_action = current_action,
                is_open = is_open,
                scrollState = scrollState,
                context = context,
                sheetState = sheetState
            )
        }

        if(current_dialog_action.value !== DIALOG_TYPE.NONE){
            DialogUi(
                is_dialog_open = is_dialog_open,
                current_dialog_action = current_dialog_action
            )
        }
    }
}

@Composable
fun PassAndDelete(is_dialog_open: MutableState<Boolean>){
    RemoveDialog(
        title = "Remove Room",
        content = "Are you sure to remove the room? you can see room for 7 days in your trash",
        is_open = is_dialog_open,
        onRemove = {
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(name:String,onNavigate:() -> Unit){
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        title = {
            ROw {
                Text("Tenant", fontSize = 16.sp)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun FloatingButton(
    navBackStack: State<NavBackStackEntry?>,
    route_size:Int,
    route:List<String>?,
    path:String,
    is_open: MutableState<Boolean>,
    current_action: MutableState<MODAL_TYPE>
){
    if(route_size == 1 && path == "rooms"){
        FloatingActionButton(
            onClick = {
                current_action.value = MODAL_TYPE.ADD_ROOM
                is_open.value = true
            },
            containerColor =  Color(0xFFB2B0E8)
        ) {
            Icon(Icons.Default.Add, contentDescription = "")
        }
    }
    if(route?.size == 2 && path == "rooms"){
        val id = navBackStack.value?.arguments?.getString("roomId")
        val room_id = hiltViewModel<GetRoomTenantCountViewModel, GetRoomTenantCountViewModel.GetRoomTenantCountFactory>{
                factory -> factory.create(id!!)
        }
        FloatingActionButton(
            onClick = {
                current_action.value = MODAL_TYPE.ADD_TENANT_WITH_PRE_ROOM(roomName = room_id.roomId)
                is_open.value = true
            },
            containerColor =  Color(0xFFB2B0E8)
        ) {
            Icon(Icons.Default.Add, contentDescription = "")
        }
    }
    if(path == "tenants"){
        FloatingActionButton(
            onClick = {
                current_action.value = MODAL_TYPE.ADD_TENANT()
                is_open.value = true
            },
            containerColor =  Color(0xFFB2B0E8)
        ) {
            Icon(Icons.Default.Add, contentDescription = "")
        }
    }
}

@Composable
fun SnackbarUi(snackbarHostState: SnackbarHostState){
    SnackbarHost(hostState = snackbarHostState){ data ->
        Snackbar(
            containerColor = Color.Black,
            contentColor = Color.White,
            shape = RoundedCornerShape(13.dp),
            action = {
                Button(
                    onClick = {
                        data.performAction()
                    }
                ) {
                    Text("Enable")
                }
            },
            dismissAction = {
                IconButton(
                    onClick = {
                        data.dismiss()
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }
            }
        ){
            Text(data.visuals.message)
        }
    }
}

@Composable
fun Header(
    route_size: Int,
    path: String,
    navController: NavHostController
){
    if(route_size >= 2 && path != "search"){
        HeaderBar(
            name = path.replaceFirstChar { it.uppercaseChar() },
            onNavigate = {
                navController.navigate(path)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalUi(
    title:String,
    current_action: MutableState<MODAL_TYPE>,
    sheetState: SheetState,
    is_open: MutableState<Boolean>,
    context: Context,
    scrollState: ScrollState
){
    BottomModalLayout(
        name = title,
        onDismiss = {
            current_action.value = MODAL_TYPE.NONE
        },
        sheetState = sheetState,
        is_open = is_open
    ) {
        when (val value = current_action.value){
            MODAL_TYPE.NONE -> {}
            is MODAL_TYPE.ANNOUNCE -> AnnounceSheet(
                context = context,
                receivers = emptyList(),
                brodcast_type = Brodcast.EVERYONE,
                title = "",
                message = "",
                onTitleChanged = {},
                onImageUpdate = {},
                onImageRemove = {},
                onImageLimit = {},
                onChangeBrodcast = {},
                onMessageUpdate = {},
                onReceiverRemove = {},
                onSearchAdd = {},
                title_error = "",
                image_error = "",
                isLoading = false,
                onSubmit = {},
                onReset = {},
            )
            MODAL_TYPE.ADD_ROOM -> RoomSheet(
                scrollState = scrollState
            )
            is MODAL_TYPE.ADD_TENANT -> TenantSheet(
                room = value.room,
                scrollState = scrollState,
                context = context
            )
            is MODAL_TYPE.ADD_TENANT_WITH_PRE_ROOM -> {
                TenantSheet(
                    scrollState = scrollState,
                    context = context
                )
            }
            is MODAL_TYPE.UPDATE_ROOM -> {
                UpdateRoomSheet(
                    scrollState = scrollState,
                    id = value.id
                )
            }
            is MODAL_TYPE.UPDATE_TENANT -> {
                TenantSheet(
                    scrollState = scrollState,
                    context = context
                )
            }
            is MODAL_TYPE.UPDATE_TENANT_RENT -> {
                val id = value?.id ?: null
                RecordRentPriceModal(
                    tenant_name = "Record Rent",
                    actual_rent_price = "2000",
                    rent_price = "20000",
                    date = 0L,
                    onRentPriceChange = {},
                    onDateChange = {},
                    rent_price_error = "",
                    onReset = {},
                    onSubmit = {},
                    isLoading = false,
                    is_open = is_open
                )
            }
            else -> {}
        }
    }
}

@Composable
fun DialogUi(
    current_dialog_action: MutableState<DIALOG_TYPE>,
    is_dialog_open: MutableState<Boolean>
){
    when(val value = current_dialog_action.value){
        is DIALOG_TYPE.DELETE_ROOM -> {
            PassAndDelete(is_dialog_open = is_dialog_open)
        }
        is DIALOG_TYPE.DELETE_TENANT -> {
            RemoveDialog(
                title = "Remove Tenant",
                content = "Are you sure to remove the tenant? you are see tenant for 7 days in your trash",
                onRemove = {

                },
                is_open = is_dialog_open
            )
        }
        else -> {}
    }
}