package com.example.billionairehari.screens

import android.graphics.drawable.VectorDrawable
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Label
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.billionairehari.icons.BedIcon
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.PersonIcon
import com.example.billionairehari.icons.RentDueIcon
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.viewmodels.RoomViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.example.billionairehari.R.drawable
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.DateInput
import com.example.billionairehari.components.DatePick
import com.example.billionairehari.components.FormButton
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.InputType
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.icons.ChatIcon
import com.example.billionairehari.icons.DeleteIcon
import com.example.billionairehari.icons.EditIcon
import com.example.billionairehari.icons.ShareIcon
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.ChildLayout
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.model.TenantRentRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.billionairehari.R
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.utils.currentMonth
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


val fake_tenants = listOf(
    RoomDao.RoomTenantDetails(
        id = "1",
        image = "",
        name = "BillionaireHari",
        phoneNumber = "",
        paymentStatus = 2
    )
)

val photos = listOf(
    "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
    "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
    "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
    "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\""
)

@Composable
fun RoomScreen(
    id:String,
    modifier: Modifier,
    current_action:MutableState<MODAL_TYPE>,

    onRoomEdit:(Room) -> Unit,
    onRoomMessage:(String) -> Unit,
    onRoomShare:(String) -> Unit,
    onRoomDelete:(String) -> Unit,

    onTenant:(String) -> Unit,
    onTenantRentUpdate:() -> Unit,
    onTenantDelete:(String) -> Unit,
    onTenantMessage:(String) -> Unit,
    onTenantShare:(String) -> Unit,
    onTenantNotice:(String) -> Unit,

    onBackNavigation:() -> Unit,
    onTenantNavigate:(String) -> Unit,
) {
    val viewmodel: RoomViewModel = hiltViewModel(
        creationCallback = { factory: RoomViewModel.RoomViewModelFactory -> factory.create(id) }
    )
    val room_details = viewmodel.room_detail.collectAsState()
    val tenants = viewmodel.tenants.collectAsState()
    val data = room_details.value
    val is_open = remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(initialPage = 0, pageCount = {
        photos.size
    })
    ChildLayout(
        label = "Rooms",
        modifier = Modifier.then(modifier)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(240.dp).zIndex(0f)
        ) {
            StaticBar(
                onRoomEdit = {
                    current_action.value = MODAL_TYPE.UPDATE_ROOM(id = data.id)
                },
                onRoomShare = {
//                    onRoomShare(room.room.value.id)
                },
                onRoomDelete = {
//                    onRoomDelete(room.room.value.id)
                },
                onRoomMessage = {
//                    onRoomMessage(room.room.value.id)
                },
                onBackNavigation = onBackNavigation
            )
            ImagePreview(
                pagerState = pagerState,
                photos = photos
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                RoomHeader(
                    name = data.name,
                    location = data.location,
                    available = (data.bed_count - data.tenantCount > 0)
                )
                RoomBriefDetail(
                    beds = data.bed_count,
                    due_date = data.due_day,
                    filled = data.tenantCount,
                    rent_due = data.dueCount
                )
            }

            Tenants(
                tenants = tenants.value,
                onTenant = {
//                    onTenant(it)
                },
                onTenantDelete = {
//                    onTenantDelete(it)
                },
                onTenantRentUpdate = {
                    val details = TenantDao.TenantWithRoomRentCard(
                        id = it.id,
                        roomId = data.id,
                        rentPrice = data.rent_price,
                        dueDay = data.due_day,
                        roomName = data.name,
                        tenantName = it.name
                    )
                    current_action.value = MODAL_TYPE.UPDATE_TENANT_RENT(tenantRentDetails = details)
                },
                onTenantMessage = {
//                    onTenantMessage(it)
                },
                onTenantNotice = {
//                    onTenantNotice(it)
                }
            )
            PriceDetails(
                deposit = data.deposit,
                current_rent_due = data.dueCount,
                room_rent = data.rent_price
            )
            val features = data.features.split(",").toList()
                .filter { it -> it.isNotEmpty() }
            if(features.size > 0) {
                FeaturesPreview(
                    features = features
                )
            }
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}

@Composable
fun RoomHeader(
    name:String,
    location:String,
    available:Boolean
){
    Column {
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Icon(
                    RoomIcon,
                    contentDescription = "",
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                        .size(40.dp)
                        .background(Color(0xFFF8F8FF))
                        .padding(6.dp)
                )
                val roomName = if(name.length > 16){
                    name.take(16) + "..."
                } else {
                    name
                }
                Column {
                    Text(roomName, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    Text(location, fontSize = 13.sp, fontWeight = FontWeight.Medium,color = Color.Black.copy(0.3f))
                }
            }
            Badge(
                modifier = Modifier.clip(CircleShape)
                    .border(1.dp, color = Color(0xFFB9F8CF),shape = CircleShape)
                    .background(Color(0xFFF0FDF4))
                    .padding(vertical = 6.dp, horizontal = 13.dp),
                containerColor = Color.Transparent,
                contentColor = Color.Black,
            ){
                Text(if(available) "Available" else "Full")
            }
        }
        Spacer(modifier = Modifier.height(13.dp))
        Divider()
    }
}

data class DropDownParams(
    val name:String,
    val icon: ImageVector? = null,
    val onClick:() -> Unit
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticBar(
    onBackNavigation:() -> Unit,

    onRoomEdit: () -> Unit,
    onRoomMessage:() -> Unit,
    onRoomShare:() -> Unit,
    onRoomDelete:() -> Unit
){

    val dropdowns = listOf<DropDownParams>(
        DropDownParams(name = "Edit Room", icon = EditIcon, onClick = onRoomEdit),
        DropDownParams(name = "Message", icon = ChatIcon, onClick = {}),
        DropDownParams(name = "Share", icon = ShareIcon, onClick = {}),
        DropDownParams(name = "Delete", icon = DeleteIcon, onClick = {}),
    )

    val coroutine = rememberCoroutineScope()
    ROw(
        modifier = Modifier.fillMaxWidth()
            .offset(y = 24.dp)
            .padding(horizontal = 24.dp)
            .zIndex(3f),
        horizontalArrangement = Arrangement.End
    ) {
        DropDownButton(
            icon = Icons.Outlined.MoreVert,
            dropDowns = dropdowns
        )
    }
}

@Composable
fun DropDownButton(
    icon: ImageVector,
    dropDowns:List<DropDownParams>,
    modifier:Modifier = Modifier,
    width: Float = 0.4f
){
    val expanded = rememberSaveable { mutableStateOf<Boolean>(false) }
    Box{
        IconButton(
            onClick = {
                expanded.value = true
            },
            modifier = Modifier.then(modifier),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White
            )
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
            modifier = Modifier.fillMaxWidth(width),
            shape = RoundedCornerShape(13.dp),
            containerColor = Color.White
        ) {
            dropDowns.forEach {
                DropDownButton(
                    name = it.name,
                    contentColor = if(it.name === "Delete") Color.Red else Color.Black,
                    icon = it.icon,
                    onClick = {
                        it.onClick.invoke()
                    }
                )
            }
        }
    }
}

@Composable
fun DropDownButton(
    name:String,
    icon: ImageVector? = null,
    contentColor:Color = Color.Black,
    onClick:() -> Unit,
){
    DropdownMenuItem(
        text = {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if(icon != null){
                    Icon(icon, contentDescription = name, tint = contentColor)
                }
                Text(name, color = contentColor, fontWeight = FontWeight.Normal)
            }
        },
        onClick = onClick
    )
}

@Composable
fun ImagePreview(
    photos:List<String>,
    pagerState: PagerState,
    modifier:Modifier = Modifier
){
    Box{
        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState
        ) {
            Box(
                modifier = Modifier.padding(13.dp).shadow(2.dp, shape = RoundedCornerShape(24.dp))
            ) {
                AsyncImage(
                    model = photos[it],
                    contentDescription = "",
                    modifier =  Modifier.clip(RoundedCornerShape(24.dp)).fillMaxWidth().height(260.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        ROw(
            modifier = Modifier.align(Alignment.BottomCenter).offset(y = (-24).dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(0,1,2,3).forEach {
                val is_image = it == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(6.dp)
                        .width(if(is_image) 70.dp else 16.dp)
                        .background(if(is_image) Color.White else Color.Black.copy(alpha = 0.6f))
                        .zIndex(3f)
                )
            }
        }
    }
}

@Composable
fun Divider(){
    HorizontalDivider(
        modifier = Modifier.background(Brush.horizontalGradient(colors = listOf(Color.Transparent,Color(0xFFF1F1F1),Color.Transparent))),
        color = Color.Transparent
    )
}

@Composable
fun RoomBriefDetail(
    beds:Int,
    filled:Int,
    due_date:String,
    rent_due:Int
){
    Log.d("BEDCOUNT",filled.toString())
    ROw(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            DetailCard(
                icon = BedIcon,
                title = "Beds",
                text = "${beds}"
            )
            DetailCard(
                icon = BedIcon,
                title = "Due Date",
                text = "${currentMonth()} ${due_date}"
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            DetailCard(
                icon = Icons.Outlined.AccountCircle,
                title = "Count",
                text = "${filled}/${beds}",
                width = 1f
            )
            DetailCard(
                icon = BedIcon,
                title = "Due Count",
                width = 1f
            )
        }
    }
}

@Composable
fun DetailCard(
    icon: ImageVector? = null,
    title:String,
    text:String = "All paid",
    width: Float = .5f,
    textColor:Long = 0xFFB2B0E8
){
    ROw(
        modifier = Modifier.clip(RoundedCornerShape(13.dp))
            .fillMaxWidth(width)
            .border(1.dp, shape = RoundedCornerShape(13.dp), color = Color.Black.copy(alpha = 0.1f))
            .padding(horizontal = 13.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            ROw(
                horizontalArrangement =  Arrangement.spacedBy(13.dp)
            ) {
                Box{
                    if(icon != null){
                        Icon(icon,
                            contentDescription = "beds",
                            modifier = Modifier.clip(CircleShape)
                                .size(30.dp)
                                .background(Color(0xFFB2B0E8).copy(alpha = 0.6f))
                                .padding(6.dp)
                        )
                    }
                }
                Box {
                    Text(text,fontSize = 16.sp,color = Color(0xFF3B38A0))
                }
            }
            Text(title, fontSize = 13.sp,color = Color.Black.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun PriceDetails(
    room_rent:Int,
    deposit:Int,
    current_rent_due:Int
){
    Column(
        modifier = Modifier.clip(RoundedCornerShape(13.dp))
            .fillMaxWidth()
            .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(13.dp))
            .padding(vertical = 16.dp, horizontal = 13.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Pricing", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            PriceLabel(name = "Room Rent", price = room_rent, per_moth = "/month")
            PriceLabel(name = "Security Deposit", price = deposit)
        }
    }
}

@Composable
fun PriceLabel(
    name:String,
    price:Int,
    per_moth:String = ""
){
    ROw(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(name,color = Color.Black.copy(alpha = 0.6f))
        Text("₹${price}${per_moth}", fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturesPreview(features:List<String>){
    Column(
        modifier = Modifier.clip(RoundedCornerShape(13.dp))
            .fillMaxWidth()
            .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(13.dp))
            .padding(vertical = 16.dp, horizontal = 13.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Text("Features", fontWeight = FontWeight.Bold)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            features.forEach {
                feature ->
                FeatureLabel(title = feature)
            }

        }
    }
}

@Composable
fun FeatureLabel(
    title:String
){
    Box(
        modifier = Modifier.clip(CircleShape)
            .border(1.dp,color = Color.Black.copy(alpha = 0.3f),shape = CircleShape)
            .background(Color.Black.copy(alpha = 0.04f))
            .padding(vertical = 9.dp,horizontal = 13.dp)
    ){
        Text(title, fontSize = 12.sp)
    }
}

@Composable
fun Tenants(
    tenants: List<RoomDao.RoomTenantDetails>,
    onTenant:(String) -> Unit,
    onTenantRentUpdate: (RoomDao.RoomTenantDetails) -> Unit,
    onTenantMessage: (String) -> Unit,
    onTenantNotice: (String) -> Unit,
    onTenantDelete: (String) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Tenants", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        if(tenants.size > 0) {
            ROw {
                Column (
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ){
                    tenants.forEach {
                            tenant ->
                        Tenant(
                            tenant = tenant,
                            onTenant = {
                                onTenant(tenant.id)
                            },
                            onTenantRentUpdate = {
                                onTenantRentUpdate(tenant)
                            },
                            onTenantMessage = {
                                onTenantMessage(tenant.id)
                            },
                            onTenantNotice = {
                                onTenantNotice(tenant.name)
                            },
                            onTenantDelete = {
                                onTenantDelete(tenant.id)
                            }
                        )
                    }
                }
            }
        }
        else {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp))
                    .padding(13.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    painter = painterResource(R.drawable.no_user),
                    contentDescription = "",
                    modifier = Modifier.size(130.dp)
                )
                Text("No Tenants Found")
            }
        }
    }
}
data class OptionModal(
    val icon:Int,
    val title:String,
    val onClick:() -> Unit
)

@Composable
fun Tenant(
    tenant: RoomDao.RoomTenantDetails,
    onTenant:() -> Unit,
    onTenantRentUpdate: () -> Unit,
    onTenantMessage: () -> Unit,
    onTenantNotice: () -> Unit,
    onTenantDelete: () -> Unit
){
    val isPaid = if(tenant.paymentStatus == 1) true else false
    val isPartial = if(tenant.paymentStatus == 2) true else false
    val is_notice_applied = false
    val expanded = remember { mutableStateOf<Boolean>(false) }
    val is_delete_dialog = remember { mutableStateOf<Boolean>(false) }
    val is_rent_dialog_open = remember { mutableStateOf<Boolean>(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onTenant
    ) {
        ROw(
            modifier = Modifier.clip(RoundedCornerShape(13.dp))
                .fillMaxWidth()
                .border(1.dp,color = Color.Black.copy(alpha = 0.1f),shape = RoundedCornerShape(13.dp))
                .padding(vertical = 13.dp, horizontal = 13.dp)
        ){
            ROw(
                horizontalArrangement =  Arrangement.spacedBy(13.dp)
            ) {
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%2Fid%2FOIP.Zw3N2LWriAvzVtUMVjk8tQHaE8%3Fr%3D0%26pid%3DApi&f=1&ipt=0795aefdffb76fa8d214f82ca5f558d260b176b7592dd170a6e0e414db8494f7&ipo=images",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).size(52.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(tenant.name, fontWeight = FontWeight.Medium)
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Badge(
                            modifier = Modifier.border(1.dp, color = if(isPaid || isPartial) Color.Unspecified else Color(0xFFF75270), shape = CircleShape),
                            containerColor = if(isPaid) Color(0xFF59AC77) else if(isPartial) Color(0xFFFFBF00) else Color(0xFFF7CAC9),
                            contentColor = Color.White
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ){
                                Text(
                                    if(isPaid) "Paid" else if(isPartial) "Partial" else "Not Paid",
                                    fontSize = 12.sp,
                                    color =  if(isPaid) Color.White else if(isPartial) Color.Black else Color(0xFFDC143C)
                                )
                            }
                        }
                        if(is_notice_applied){
                            Badge {
                                Box(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                ){
                                    Text("Notice",fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
            Box{
                IconButton(
                    onClick = {
                        expanded.value = true
                    }
                ) {
                    Icon(painter = painterResource(drawable.outline_more_horiz_24), contentDescription = "")
                }

                val options = listOf<OptionModal>(
                    OptionModal(
                        onClick = onTenantRentUpdate,
                        title = "Set Paid",
                        icon = drawable.outline_paid_24
                    ),
                    OptionModal(
                        onClick = onTenantMessage,
                        title = "Message",
                        icon = drawable.outline_chat_24
                    ),
                    OptionModal(
                        onClick = onTenantNotice,
                        title = "Notice",
                        icon = drawable.outline_warning_24
                    )
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {
                        expanded.value = false
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                    options.forEach {
                        option ->
                        DropdownMenuItem(
                            onClick = {
                                option.onClick()
                            },
                            text = {
                                ROw(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        painter =  painterResource(option.icon),
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        option.title,
                                        fontSize = 13.sp
                                    )
                                }
                            },
                        )
                    }
                    DropdownMenuItem(
                        onClick = onTenantDelete,
                        text = {
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "",
                                    modifier = Modifier.size(16.dp),
                                    tint =  Color(0xFFff2236)
                                )
                                Text(
                                    "Delete",
                                    fontSize = 13.sp,
                                    color = Color(0xFFff2236)
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}




