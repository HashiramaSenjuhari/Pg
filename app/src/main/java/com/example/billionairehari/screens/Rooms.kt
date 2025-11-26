package com.example.billionairehari.screens

import android.app.Activity
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.drawable.VectorDrawable
import android.util.Log
import android.view.RoundedCorner
import android.widget.ProgressBar
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupPositionProvider
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.R
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.FilterCard
import com.example.billionairehari.components.FilterOption
import com.example.billionairehari.components.SearchBar
import com.example.billionairehari.components.sheets.AddRoomSheet
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.icons.BedIcon
import com.example.billionairehari.icons.FilterIcon
import com.example.billionairehari.icons.NoticeIcon
import com.example.billionairehari.icons.PersonIcon
import com.example.billionairehari.icons.RentDueIcon
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.icons.TenantsIcon
import com.example.billionairehari.viewmodels.RoomViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.billionairehari.components.rooms.RoomFilterTypes
import com.example.billionairehari.R.drawable
import com.example.billionairehari.components.DropDown
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.Rupee
import com.example.billionairehari.layout.DIALOG_TYPE
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.RoomCardDetails
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.viewmodels.RoomsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.text.toFloat


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsScreen(
    navController: NavController,
    current_action: MutableState<MODAL_TYPE>,
    current_dialog_action: MutableState<DIALOG_TYPE>,
    modifier:Modifier = Modifier,
    viewmodel: RoomsViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    /** viewmodel - start **/
    val filtered_rooms = viewmodel.rooms.collectAsState()
    /** viewmodel - end **/

    val final_rooms = filtered_rooms.value

    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }

    DynamicShowcaseScreen(
        title = "Rooms",
        placeholder = "Search Room",
        scrollState = scrollState,
        navController = navController,
        search_route = Destinations.ROOM_SEARCH_ROUTE
    ) {
        RoomCards(
            scrollState = scrollState,
            navController = navController,
            final_rooms = final_rooms,
            current_action = current_action
        )
    }

}

@Composable
fun DynamicShowcaseScreen(
    scrollState: ScrollState,
    navController: NavController,
    title: String,
    placeholder: String,
    search_route:String,
    content:@Composable () -> Unit
){

    /** header Animation module - Start **/
    /** header Animation module - End **/


    val maxOffset = 100f
    val progress = (scrollState.value.toFloat() / maxOffset).coerceIn(0f,1f)

    val padding = animateDpAsState(targetValue = lerp(50.dp, 9.dp,progress))
    val spacing = animateDpAsState(targetValue = lerp(13.dp,3.dp,progress))
    val fontSize = animateFloatAsState(targetValue = lerp(24.sp, 21.sp,progress).value)
    val activity = LocalView.current.context as Activity

    SideEffect {
        activity.window.statusBarColor = Color.Blue.copy(0.01f).toArgb()
        val wc = WindowInsetsControllerCompat(activity.window,activity.window.decorView)
        wc.isAppearanceLightStatusBars = true
    }

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Blue.copy(0.2f),Color.White,Color.White,Color.White,Color.White,Color.White)))
            .padding(top = padding.value)
    ) {
        DynamicTopHeader(
            title = title,
            placeholder = placeholder,
            onClickFilter = {},
            onClickSearch = {
                navController.navigate(search_route)
            },
            fontSize = fontSize.value.sp,
            spacing = spacing.value
        )
        content.invoke()
    }
}

@Composable
fun DynamicTopHeader(
    title:String,
    placeholder:String,
    spacing: Dp,
    fontSize: TextUnit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(space = spacing),
        modifier =  Modifier
            .animateContentSize()
            .padding(horizontal = 13.dp, vertical = 13.dp)
    ) {
        ROw(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(title, fontSize = fontSize, fontWeight = FontWeight.Bold)
        }
        StaticSearchBar(
            placeholder = placeholder,
            onClick = onClickSearch,
            onClickFilter = onClickFilter
        )
    }
}

@Composable
fun RoomCards(
    scrollState: ScrollState,
    navController: NavController,
    final_rooms: List<RoomCardDetails>,
    current_action: MutableState<MODAL_TYPE>
){
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 6.dp, end = 6.dp,bottom = 90.dp, top = 13.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        final_rooms.forEach {
                room ->
            RoomCard(
                room_detail = room,
                onClick = {
                    navController.navigate("rooms/${room.id}")
                },
                current_action = current_action
            )
        }
    }
}

@Composable
fun StaticSearchBar(
    placeholder:String,
    onClick: () -> Unit,
    onClickFilter:() -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color.Black.copy(0.1f), shape = CircleShape)
            .padding(vertical = 3.dp, horizontal = 13.dp)
            .clickable(
                enabled = true,
                onClick = onClick,
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
    ){
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(placeholder, fontSize = 16.sp, color = Color.Black.copy(0.6f))
            }
            IconButton(
                onClick = onClickFilter
            ){
                Icon(
                    FilterIcon,
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCard(
    room_detail: RoomCardDetails,
    onClick:() -> Unit,
    current_action: MutableState<MODAL_TYPE>
) {
    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(24.dp))
                .background(Color(0xFFFFFFF)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier.padding(13.dp),
            ) {
                RoomCardHeader(
                    name = room_detail.name,
                    available_count = 4
                )
                Spacer(modifier = Modifier.padding(vertical = 3.dp))
                HorizontalDivider(color = Color.Black.copy(0.1f))
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                RoomCardContent(
                    available = 3,
                    beds = 6,
                    due_date = "Nov 24",
                    due_count = 6,
                    current_action = current_action
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCardHeader(
    name:String,
    available_count: Int,
){
    val isAvailable = if(available_count >= 1) true else false
    val availability = if(isAvailable) "${available_count} Available" else "Full"
    ROw(
        modifier = Modifier
            .fillMaxWidth()
    ) {
            ROw(
                horizontalArrangement =  Arrangement.spacedBy(13.dp)
            ) {
                Icon(RoomIcon,
                    contentDescription = "RoomIcon",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color(0xFFF3F4F6))
                        .padding(4.dp)
                )
                Text(name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            ROw {
                val border = if(isAvailable){1.dp} else {0.dp}
                val color = if(isAvailable) Color(0xFFB9F8CF) else Color(0xFFE5E7EB)
                val bg = if(isAvailable) Color(0xFFF0FDF4) else Color(0xFFF9FAFB)
                val content = if(isAvailable) Color(0xFF008236) else Color(0xFF68717F)
                Badge(
                    modifier = Modifier
                        .border(width = border, color = color, shape = RoundedCornerShape(9.dp))
                        .background(bg)
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    containerColor = bg,
                    contentColor = content
                ){
                    Text(availability)
                }
            }
    }
}

@Composable
fun RoomCardContent(
    beds:Int,
    available:Int,
    due_count:Int,
    due_date:String,
    current_action: MutableState<MODAL_TYPE>
){
    val isDue = if(due_count === 0) false else true
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ){
                RoomCardContentLabel(
                    title = "Beds",
                    value = {
                        Text(beds.toString(), fontWeight = FontWeight.Medium)
                    },
                    icon = R.drawable.outline_bed_24,
                    width = 0.5f
                )
                RoomCardContentLabel(
                    title = "Rent Due",
                    value = {
                        Text(
                            if(!isDue){
                                "All Paid"
                            }else {
                                if(due_count === 1){
                                    "${due_count.toString()} person"
                                }else{
                                    "${due_count.toString()} persons"
                                }
                            },
                            color = if(!isDue) Color(0xFF008236) else Color.Red,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    icon = R.drawable.outline_currency_rupee_24,
                    width = 1f
                )
            }
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ){
                RoomCardContentLabel(
                    title = "OCCUPANCY",
                    value = {
                        Text("4/4", fontWeight = FontWeight.Medium)
                    },
                    icon = R.drawable.person,
                    width = 0.5f
                )
                RoomCardContentLabel(
                    title = "Due Date",
                    value = {
                        Text("Nov 24", fontWeight = FontWeight.Normal)
                    },
                    icon = R.drawable.outline_calendar_today_24,
                    width = 1f
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            if(available >= 1){
                AppButton(
                    onClick = {
                        current_action.value = MODAL_TYPE.ADD_TENANT(room = "BillionaireHari")
                    },
                    containerColor = Color.Black.copy(0.8f),
                    contentColor = Color.White,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}


@Composable
fun RoomCardContentLabel(
    title:String,
    icon: Int,
    width:Float = 0.5f,
    value:@Composable () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(width)
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter =  painterResource(icon),
                contentDescription = "",
                modifier = Modifier.clip(RoundedCornerShape(13.dp))
                    .size(30.dp)
                    .background(Color(0xFFB2B0E8).copy(0.6f))
                    .padding(4.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title.uppercase(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.Black.copy(0.4f)
                )
                value.invoke()
            }
        }
    }
}