package com.example.billionairehari.screens

import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.drawable.VectorDrawable
import android.util.Log
import android.view.RoundedCorner
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
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
import com.example.billionairehari.components.DropDown
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.Rupee
import com.example.billionairehari.model.RoomCardDetails
import com.example.billionairehari.viewmodels.RoomsViewModel
import com.example.billionairehari.viewmodels.current_rooms
import dagger.hilt.android.lifecycle.HiltViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsScreen(
    viewmodel: RoomsViewModel,
    modifier:Modifier,
    navController: NavController
) {
    /** header Animation module - Start **/
    val scrollState = rememberScrollState()

//    Log.d("OffsetBillionaire",scrollState.value.toFloat().toString())
    val maxOffset = 100f
    val progress = (scrollState.value.toFloat() / maxOffset).coerceIn(0f,1f)
//    Log.d("OffsetBillionaire",progress.toString())

    val padding = animateDpAsState(targetValue = lerp(50.dp, 9.dp,progress))
    val spacing = animateDpAsState(targetValue = lerp(13.dp,3.dp,progress))
    val fontSize = animateFloatAsState(targetValue = lerp(24.sp, 21.sp,progress).value)
    /** header Animation module - End **/

    /** viewmodel - start **/
    val filtered_rooms = viewmodel.rooms.collectAsState()
    /** viewmodel - end **/

    val final_rooms = filtered_rooms.value

    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxSize()
            .background(Color.White)
            .padding(top = padding.value)
    ) {
        DynamicTopHeader(
            onClickFilter = {},
            onClickSearch = {},
            fontSize = fontSize.value.sp,
            spacing = spacing.value
        )
        RoomCards(
            scrollState = scrollState,
            navController = navController,
            final_rooms = final_rooms
        )
    }
}

@Composable
fun DynamicTopHeader(
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
            Text("Rooms", fontSize = fontSize, fontWeight = FontWeight.Bold)
        }
        StaticSearchBar(
            onClick = {},
            onClickFilter = {}
        )
    }
}

@Composable
fun RoomCards(
    scrollState: ScrollState,
    navController: NavController,
    final_rooms: List<RoomCardDetails>,
){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(start = 6.dp, end = 6.dp,bottom = 90.dp),
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

@Composable
fun StaticSearchBar(
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
                onClick = onClickFilter,
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
                Text("Search Room", fontSize = 16.sp, color = Color.Black.copy(0.6f))
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
fun RoomCard(room_detail: RoomCardDetails,onClick:() -> Unit) {
    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp))
                .background(Color(0xFFFFFFF))
                .padding(vertical = 3.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            onClick = onClick
        ) {
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
        modifier = Modifier
            .fillMaxWidth(width)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .border(1.dp, color = Color(0xFFF3F4F6), shape = RoundedCornerShape(13.dp))
            .padding(vertical = 6.dp, horizontal = 13.dp),
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
            modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .border(
                    1.dp,
                    color = if (value > 0) Color(border) else Color(0xFFDFE0E1),
                    shape = RoundedCornerShape(9.dp)
                )
                .background(if (value > 0) Color(bg) else Color(0xFFF9FAFB))
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
fun RoomCardLabel(
    name:String,
    icon: ImageVector,
    value:String,
    width:Float = 1f
){
    Column(
        modifier = Modifier
            .fillMaxWidth(width)
            .clip(RoundedCornerShape(13.dp))
            .background(Color(0xFFF9FAFB))
            .padding(13.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(icon, contentDescription = "",modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .size(24.dp)
                .background(Color(0xFFB2B0E8).copy(0.6f))
                .padding(6.dp))
            Text(value,fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Text(name,fontSize = 13.sp,color =  Color(0xFF606060))
    }
}

data class DropDownData(
    val name:String,
    val icon: ImageVector,
    val onClick:() -> Unit
)

val dropdowns = listOf<DropDownData>(
    DropDownData(name = "Edit Room", icon = Icons.Default.Edit, onClick = {}),
    DropDownData(name = "Send Message", icon = Icons.Default.Edit, onClick = {}),
    DropDownData(name = "Delete", icon = Icons.Default.Delete, onClick = {})
)

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
    val expanded = remember { mutableStateOf<Boolean>(false) }
    ROw(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 13.dp)
    ) {
        ROw(
            horizontalArrangement =  Arrangement.spacedBy(13.dp)
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
                        .padding(6.dp)
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
                        .border(width = border, color = color, shape = RoundedCornerShape(9.dp))
                        .background(bg)
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    containerColor = bg,
                    contentColor = content
                ){
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.clip(CircleShape).size(10.dp).background(if(!availability) Color.Green.copy(0.4f) else Color.Red.copy(0.8f)))
                        Text(if(!availability)"Available" else "Full")
                    }
                }
            }
        }
        Box(){
            IconButton(
                onClick = {
                    expanded.value = true
                }
            ){
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(13.dp),
                modifier = Modifier.width(160.dp)

            ) {
                dropdowns.forEach {
                    dropDownData ->
                    DropdownMenuItem(
                        text = {
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(dropDownData.icon, contentDescription = "",modifier = Modifier.size(16.dp))
                                Text(dropDownData.name)
                            }
                        },
                        onClick = {
                            dropDownData.onClick.invoke()
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomCardContent(room_detail: RoomCardDetails){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            RoomCardLabel(
                name = "beds",
                value = "${room_detail.total_beds}",
                icon = BedIcon,
                width = 0.30f
            )
            RoomCardLabel(
                name = "Due Date",
                value = "${room_detail.total_beds}",
                icon = CalendarIcon,
                width = 0.50f
            )
            RoomCardLabel(
                name = "Rent",
                value = "₹${room_detail.rent_per_tenant}",
                icon = Rupee,
                width = 1f
            )
        }
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            containerColor = Color.Black.copy(0.8f),
            contentColor = Color.White,
            shape = CircleShape,
            border = BorderStroke(1.dp, color = Color.Black.copy(0.1f))
        ) {
            Text("Add Room")
        }
    }
}