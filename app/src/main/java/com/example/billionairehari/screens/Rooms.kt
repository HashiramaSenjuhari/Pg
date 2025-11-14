package com.example.billionairehari.screens

import android.graphics.Rect
import android.graphics.drawable.VectorDrawable
import android.util.Log
import android.view.RoundedCorner
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupPositionProvider
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.model.RoomCardDetails
import com.example.billionairehari.viewmodels.RoomsViewModel
import com.example.billionairehari.viewmodels.current_rooms


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsScreen(
    modifier:Modifier,
    navController: NavController
) {
    val scrollState = rememberScrollState()

//    Log.d("OffsetBillionaire",scrollState.value.toFloat().toString())
    val maxOffset = 100f
    val progress = (scrollState.value.toFloat() / maxOffset).coerceIn(0f,1f)
//    Log.d("OffsetBillionaire",progress.toString())

    val padding = animateDpAsState(targetValue = lerp(50.dp, 9.dp,progress))
    val spacing = animateDpAsState(targetValue = lerp(13.dp,3.dp,progress))
    val fontSize = animateFloatAsState(targetValue = lerp(24.sp, 21.sp,progress).value)



//    val rooms = hiltViewModel<RoomsViewModel>()
//    val filtered_rooms = rooms.rooms.collectAsState()

    val search = rememberSaveable { mutableStateOf<String>("") }
    val final_rooms = current_rooms

    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxSize()
            .background(Color.White)
            .padding(top = padding.value)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = spacing.value),
            modifier =  Modifier
                .animateContentSize()
                .padding(horizontal = 13.dp, vertical = 19.dp)
        ) {
                ROw(
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text("Rooms", fontSize = fontSize.value.sp, fontWeight = FontWeight.Bold)
                }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, color = Color.Black.copy(0.1f), shape = CircleShape)
                    .padding(vertical = 13.dp, horizontal = 13.dp)
            ){
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                    Text("Search Room", fontSize = 16.sp, color = Color.Black.copy(0.6f))
                }
            }
        }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.White)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    final_rooms.forEach {
                            room ->
                        RoomCard(
                            room_detail = room,
                            onClick = {
                                navController.navigate("rooms/${room.id}")
                            }
                        )
                    }
                }
    }
}

@Composable
fun RoomSearchBar(
    value:String,
    onValueChange:(String) -> Unit
){
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, tint = Color.Black,contentDescription = "")
        },
        trailingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(FilterIcon,contentDescription = "")
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text("Search \"Room 6\"")
        },
        maxLines = 1,
        modifier =  Modifier.fillMaxWidth()
            .clip(CircleShape)
            .border(1.dp, color = Color(0xFF909090), shape = CircleShape)
            .padding(horizontal = 13.dp)
    )
}


@Composable
fun FilterCards(
    onFilter:(RoomFilterTypes) -> Unit,
    filter_type: RoomFilterTypes,
    all_count:Int,
    available_count:Int,
    notice_count:Int,
    rent_dues_count:Int
) {
    val filters = listOf<FilterOption<RoomFilterTypes>>(
        FilterOption(name = "All", count = "$all_count",type = RoomFilterTypes.ALL_ROOMS),
        FilterOption(name = "Available", count = "$available_count", type = RoomFilterTypes.AVAILABLE),
        FilterOption(name = "Notice", count = "$notice_count", type = RoomFilterTypes.NOTICE),
        FilterOption(name = "Rent Due", count = "$rent_dues_count", type = RoomFilterTypes.RENT_DUE)
    )

    LazyRow(
       horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(filters.size){
            FilterCard(
                name = filters[it].name,
                count = filters[it].count,
                onClick = {
                    onFilter(filters[it].type)
                },
                isClicked = filter_type == filters[it].type
            )
        }
    }
}


enum class RoomCardTools {
    EDIT,
    SEND_MESSAGE,
    NONE
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCard(room_detail: RoomCardDetails,onClick:() -> Unit) {
    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }
    Box() {
        Card(
            modifier = Modifier.fillMaxWidth()
                .shadow(2.dp, spotColor = Color(0xFF909090), shape = RoundedCornerShape(13.dp))
                .background(Color(0xFFFFFFF)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            onClick = onClick
        ) {
            Column {
                RoomCardHeader(
                    name = room_detail.name,
                    availability = room_detail.is_available,
                    is_open = is_open,
                    onEdit = {
                    },
                    onSendMessage = {
                    },
                    onShare = {

                    }
                )
                HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))
                RoomCardContent(
                    room_detail = room_detail
                )
            }
        }
    }
}

@Composable
fun ROw(
    modifier:Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content:@Composable () -> Unit
){
    Row(
        modifier = Modifier.then(modifier),
       verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ){
        content()
    }
}
@Composable
fun RoomCardIconIntLabel(
    name: String,
    value: Int,
    drawable: Int? = null,
    icon: ImageVector? = null,
    bg:Long,
    border:Long,
    text:Long,
    width: Float = 1f
){
    ROw(
        modifier = Modifier.fillMaxWidth(width)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .border(1.dp,color = Color(0xFFF3F4F6),shape = RoundedCornerShape(13.dp))
            .padding(vertical = 6.dp, horizontal =13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if(icon != null){
                Icon(icon, contentDescription = "",modifier = Modifier.size(16.dp))
            }
            if(drawable != null){
                Icon(painter = painterResource(id = drawable), contentDescription = "",modifier = Modifier.size(16.dp))
            }
            Text(name, color =  Color(0xFF606060), fontSize = 12.sp)
        }
        Box(
            modifier = Modifier.clip(RoundedCornerShape(9.dp))
                .border(1.dp, color = if(value > 0) Color(border) else Color(0xFFDFE0E1),shape=RoundedCornerShape(9.dp))
                .background(if(value > 0) Color(bg) else Color(0xFFF9FAFB))
                .padding(horizontal = 9.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ){
            Text("$value",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if(value > 0) Color(text) else Color(0xFF6A7282)
            )
        }
    }
}

@Composable
fun RoomCardIconStringLabel(
    name: String,
    value: String,
    drawable: Int? = null,
    icon: ImageVector? = null,
    bg:Long,
    border:Long,
    text:Long,
    width: Float = 1f
){
    ROw(
        modifier = Modifier.fillMaxWidth(width)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .border(1.dp,color = Color(0xFFF3F4F6),shape = RoundedCornerShape(13.dp))
            .padding(vertical = 6.dp, horizontal =13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if(icon != null){
                Icon(icon, contentDescription = "",modifier = Modifier.size(16.dp))
            }
            if(drawable != null){
                Icon(painter = painterResource(id = drawable), contentDescription = "",modifier = Modifier.size(16.dp))
            }
            Text(name, color =  Color(0xFF606060), fontSize = 12.sp)
        }
        Box(
            modifier = Modifier.clip(RoundedCornerShape(9.dp))
                .border(1.dp, color = Color(0xFFDFE0E1),shape=RoundedCornerShape(9.dp))
                .background(Color(0xFFF9FAFB))
                .padding(horizontal = 9.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ){
            Text(value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if(value == "null") Color(0xFF6A7282) else Color(text)
            )
        }
    }
}
@Composable
fun RoomCardLabel(
    name:String,
    icon: ImageVector,
    value:String,
    width:Float = 1f
){
    ROw(
        modifier = Modifier.fillMaxWidth(width)
            .clip(RoundedCornerShape(13.dp))
            .background(Color(0xFFF9FAFB))
            .padding(vertical = 6.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(icon, contentDescription = "",modifier = Modifier.clip(RoundedCornerShape(9.dp))
                .size(24.dp).background(Color(0xFFB2B0E8)).padding(6.dp))
            Text(name,fontSize = 12.sp,color =  Color(0xFF606060))
        }
        Text(value,fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCardHeader(
    is_open: MutableState<Boolean>,
    name:String,
    availability:Boolean,
    onEdit:() -> Unit,
    onSendMessage:() -> Unit,
    onShare:() -> Unit
){
    val tooltipState = rememberTooltipState(isPersistent = true)
    val coroutine = rememberCoroutineScope()
    ROw(
        modifier = Modifier.fillMaxWidth().padding(start = 13.dp)
    ) {
        ROw(
            horizontalArrangement =  Arrangement.spacedBy(13.dp)
        ) {
            Icon(RoomIcon,
                contentDescription = "RoomIcon",
                modifier = Modifier.size(30.dp).clip(RoundedCornerShape(13.dp)).background(Color(0xFFF3F4F6)).padding(6.dp)
            )
            Text(name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        ROw {
            val border = if(availability){1.dp} else {0.dp}
            val color = if(availability) Color(0xFFB9F8CF) else Color(0xFFE5E7EB)
            val bg = if(availability) Color(0xFFF0FDF4) else Color(0xFFF9FAFB)
            val content = if(availability) Color(0xFF008236) else Color(0xFF68717F)
            Badge(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .border(width = border,color = color,shape = RoundedCornerShape(9.dp))
                    .background(bg)
                    .padding(horizontal = 13.dp, vertical = 6.dp),
                containerColor = bg,
                contentColor = content
            ){
                Text(if(availability)"Available" else "Full")
            }
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                state = tooltipState,
                tooltip = {
                    RichTooltip(
                        modifier = Modifier.width(100.dp)
                            .padding(0.dp)
                            .shadow(2.dp, shape =  RoundedCornerShape(13.dp))
                            .background(Color.White),
                        colors = TooltipDefaults.richTooltipColors(
                            containerColor = Color.Transparent
                        ),
                        shadowElevation = 0.dp,
                        tonalElevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            TextButton(
                                onClick = onEdit,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(0.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Edit", modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Start)
                            }
                            TextButton(
                                onClick = onSendMessage,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(0.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Edit")
                            }
                            TextButton(
                                    onClick = onShare,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Edit")
                            }
                            TextButton(
                                onClick = {},
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(0.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Edit")
                            }
                        }
                    }
                }
            ) {
                IconButton(
                    onClick = {
                        coroutine.launch {
                            tooltipState.show()
                        }
                    }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "tooolbar")
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomCardContent(room_detail: RoomCardDetails){
    ROw(
        modifier = Modifier.fillMaxWidth().height(160.dp).padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
                ROw(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ){
                    RoomCardLabel(
                        name = "Beds",
                        value = "${room_detail.total_beds}",
                        icon = BedIcon,
                        width = 0.36f
                    )
                    RoomCardIconStringLabel(
                        name = "Due Date",
                        value = "20/03/2024",
                        border = 0xFFF8F8FF,
                        icon = CalendarIcon,
                        text = 0xFF000000,
                        bg = 0xFFF9FAFB
                    )
                }
            ROw(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                RoomCardIconIntLabel(
                    name = "Rent Due",
                    value = room_detail.rent_dues,
                    icon = RentDueIcon,
                    width = 0.46f,
                    bg = 0xFFF7CAC9,
                    text = 0xFFDC143C,
                    border = 0xFFF75270
                )
            }
            ROw(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                FlowRow (
                    modifier = Modifier.fillMaxWidth(0.6f)
                        .clip(RoundedCornerShape(13.dp))
                        .padding(vertical = 6.dp, horizontal =13.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(PersonIcon, contentDescription = "", tint = Color(0xFFB0B0B0),modifier = Modifier.size(24.dp))
                    Icon(PersonIcon, contentDescription = "",tint = Color(0xFFB0B0B0),modifier = Modifier.size(24.dp))
                    Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(24.dp))
                    Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(24.dp))
                }
                AppButton(
                    onClick = {},
                    modifier =Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(13.dp)),
                    containerColor = Color(0xFF000401),
                    contentColor = Color.White,
                    enabled = room_detail.is_available
                ) {
                    val button_name = if(room_detail.is_available) "Add Tenant" else "Full"
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = if(room_detail.is_available)R.drawable.person_add else R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier.size(16.dp),
                            tint = if(room_detail.is_available) Color.White else Color.Black
                        )
                        Text(button_name, fontSize = 13.sp,color = if(room_detail.is_available) Color.White else Color.Black)
                    }
                }
            }
        }
    }
}