package com.example.billionairehari.screens

import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
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
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.model.Room
import com.example.billionairehari.model.Tenant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RoomScreen(
    modifier: Modifier,

    onRoomEdit:(Room) -> Unit,
    onRoomMessage:(String) -> Unit,
    onRoomShare:(String) -> Unit,
    onRoomDelete:(String) -> Unit,

    onTenant:(String) -> Unit,
    onTenantRentUpdate:(String) -> Unit,
    onTenantDelete:(String) -> Unit,
    onTenantMessage:(String) -> Unit,
    onTenantShare:(String) -> Unit,
    onTenantNotice:(String) -> Unit,

    onBackNavigation:() -> Unit,
    onTenantNavigate:(String) -> Unit
) {
//    val room = hiltViewModel<RoomViewModel>()
    val scrollState = rememberScrollState()
    val is_more = remember { mutableStateOf<Boolean>(false) }
    Column(
        modifier = Modifier.then(modifier)
            .wrapContentHeight()
            .verticalScroll(state = scrollState)
    ) {
        val photos = listOf(
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\"",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F6d%2F80%2F73%2F6d80739dc844d81325c81a73d3fa5cc6.jpg&f=1&nofb=1&ipt=e7fb2752441029b26dce1e075c76003253d17a36339ef2aa990c536d77173484\""
        )
        val pagerState = rememberPagerState(initialPage = 0, pageCount = {
            photos.size
        })
        Box(
            modifier = Modifier.fillMaxWidth().height(240.dp).zIndex(0f)
        ){
            StaticBar(
                onRoomEdit = {
//                    onRoomEdit(room.room.value)
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
        RoomDetails(
            name = "",
            filled = 0,
            rent_due = 0,
            due_date = "",
            beds = "4",
            available = true,

            rent = 4000,
            deposit = 4000,

            tenants = listOf(
                Tenant(
                    name = "Billionaire",
                    room = "Billionairehari",
                    deposit = true,
                    rent_paid_first = true,
                    image = null,
                    joining_date = 0L,
                    phone_number = "343492834",
                    automatic_remainder = true
                ),
                Tenant(
                    name = "Billionaire",
                    room = "Billionairehari",
                    deposit = true,
                    rent_paid_first = true,
                    image = null,
                    joining_date = 0L,
                    phone_number = "343492834",
                    automatic_remainder = true
                ),
                Tenant(
                    name = "Billionaire",
                    room = "Billionairehari",
                    deposit = true,
                    rent_paid_first = true,
                    image = null,
                    joining_date = 0L,
                    phone_number = "343492834",
                    automatic_remainder = true
                ),
                Tenant(
                    name = "Billionaire",
                    room = "Billionairehari",
                    deposit = true,
                    rent_paid_first = true,
                    image = null,
                    joining_date = 0L,
                    phone_number = "343492834",
                    automatic_remainder = true
                ),
                Tenant(
                    name = "Billionaire",
                    room = "Billionairehari",
                    deposit = true,
                    rent_paid_first = true,
                    image = null,
                    joining_date = 0L,
                    phone_number = "343492834",
                    automatic_remainder = true
                )
            ),
            features = listOf("Billionaire"),
            onTenant = {
                onTenant(it)
            },
            onTenantRentUpdate = {
                onTenantRentUpdate(it)
            },
            onTenantDelete = {
                onTenantDelete(it)
            },
            onTenantMessage = {
                onTenantMessage(it)
            },
            onTenantNotice = {
                onTenantNotice(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticBar(
    onBackNavigation:() -> Unit,

    onRoomEdit: () -> Unit,
    onRoomMessage:() -> Unit,
    onRoomShare:() -> Unit,
    onRoomDelete:() -> Unit
){
    val coroutine = rememberCoroutineScope()
    val expanded = rememberSaveable { mutableStateOf<Boolean>(false) }
    ROw(
        modifier = Modifier.fillMaxWidth()
            .offset(y = 24.dp)
            .padding(horizontal = 24.dp)
            .zIndex(3f)
    ) {
        IconButton(
            onClick = onBackNavigation,
            modifier = Modifier.size(40.dp)
                .shadow(6.dp, shape = CircleShape)
                .background(Color.White)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "")
        }
        Box{
            IconButton(
                onClick = {
                    expanded.value = true
                },
                modifier = Modifier.size(40.dp)
                    .shadow(6.dp, shape = CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                modifier = Modifier.fillMaxWidth(0.4f),
                offset = DpOffset(x = 24.dp,y = 6.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Edit Room")
                    },
                    onClick = onRoomEdit
                )
                DropdownMenuItem(
                    text = {
                        Text("Message")
                    },
                    onClick = onRoomMessage
                )
                DropdownMenuItem(
                    text = {
                        Text("Share")
                    },
                    onClick = onRoomShare
                )
                DropdownMenuItem(
                    text = {
                        Text("Delete")
                    },
                    onClick = onRoomDelete
                )
            }
        }
    }
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
fun RoomDetails(
    name:String,
    available:Boolean,

    beds:String,
    filled: Int,
    due_date: String,
    rent_due: Int,
    tenants:List<Tenant>,
    rent:Int,
    deposit:Int,
    features:List<String>,

    onTenant:(String) -> Unit,
    onTenantRentUpdate: (String) -> Unit,
    onTenantDelete:(String) -> Unit,
    onTenantNotice:(String) -> Unit,
    onTenantMessage:(String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
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
                Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 13.dp)
        ) {
            BedDetails(
                beds = beds,
                due_date = due_date,
                filled = filled,
                rent_due = rent_due
            )
            Tenants(
                tenants = tenants,
                onTenant = {
                    onTenant(it)
                },
                onTenantDelete = {
                    onTenantDelete(it)
                },
                onTenantRentUpdate = {
                    onTenantRentUpdate(it)
                },
                onTenantMessage = {
                    onTenantMessage(it)
                },
                onTenantNotice = {
                    onTenantNotice(it)
                }
            )
            PriceDetails(
                deposit = deposit,
                current_rent_due = rent_due,
                room_rent = rent
            )
            FeaturesPreview(
                features = features
            )
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
fun BedDetails(
    beds:String,
    filled:Int,
    due_date:String,
    rent_due:Int
){
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
                total = "${beds}"
            )
            DetailCard(
                vector_icon = drawable.due_icon,
                title = "Due Date",
                total = "${due_date}"
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if((beds.toIntOrNull() ?: 0) <= 4) {
                DetailCard(
                    icon = TenantIcon,
                    title = "Tenants",
                    content = {
                        ROw (
                            modifier = Modifier
                                .clip(RoundedCornerShape(13.dp))
                                .padding(vertical = 6.dp),
                        ) {
                            Icon(PersonIcon, contentDescription = "", tint = Color(0xFFB0B0B0),modifier = Modifier.size(19.dp))
                            Icon(PersonIcon, contentDescription = "",tint = Color(0xFFB0B0B0),modifier = Modifier.size(19.dp))
                            Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(19.dp))
                            Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(19.dp))
                        }
                    },
                    width = 1f
                )
            } else {
                DetailCard(
                    icon = TenantIcon,
                    title = "Tenants",
                    total = "${filled}/${beds}",
                    width = 1f
                )
            }
            DetailCard(
                vector_icon = drawable.due_icon,
                title = "Rent Due",
                rent_due_price = rent_due,
                width = 1f
            )
        }
    }
}

@Preview
@Composable
fun billionaire(){
    RoomsScreen(
        navController = rememberNavController(),
        modifier = Modifier.padding(24.dp)
    )

    ROw (
        modifier = Modifier
            .clip(RoundedCornerShape(13.dp))
            .padding(vertical = 6.dp),
    ) {
        Icon(PersonIcon, contentDescription = "", tint = Color(0xFFB0B0B0),modifier = Modifier.size(24.dp))
        Icon(PersonIcon, contentDescription = "",tint = Color(0xFFB0B0B0),modifier = Modifier.size(24.dp))
        Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(24.dp))
        Icon(PersonIcon, contentDescription = "",modifier = Modifier.size(24.dp))
    }
}

@Composable
fun DetailCard(
    icon: ImageVector? = null,
    vector_icon: Int? = null,
    title:String,
    width: Float = .5f,
    total:String? = null,
    rent_due_price:Int? = null,
    paid_text:String = "All paid",
    content:(@Composable () -> Unit)? = null,
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
                if(icon != null){
                    Icon(icon,
                        contentDescription = "beds",
                        modifier = Modifier.clip(CircleShape)
                            .size(30.dp)
                            .background(Color(0xFFB2B0E8).copy(alpha = 0.6f))
                            .padding(6.dp)
                    )
                }
                if(vector_icon != null && icon == null){
                    Icon(
                        painter = painterResource(vector_icon),
                        contentDescription = title,
                        modifier = Modifier.clip(CircleShape)
                            .size(30.dp)
                            .background(Color(textColor))
                            .padding(6.dp)
                    )
                }
                if(total != null) {
                    Text(total,fontSize = 16.sp,color = Color(0xFF3B38A0))
                }
                if(rent_due_price != null) {
                    Text(
                        if(rent_due_price == 0) paid_text else "₹ ${formatIndianRupee(number = "$rent_due_price")}",
                        fontSize = 16.sp,
                        color = if(rent_due_price== 0) Color(0xFF3B38A0) else Color(0xFFE4004B)
                    )
                }
                if(content != null){
                    content()
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
            PriceLabel(name = "Room Rent", price = room_rent, per_moth = "/ mo")
            PriceLabel(name = "Security Deposit", price = deposit)
            PriceLabel(name = "Current Rent Due", price = current_rent_due)
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
        Text(name,color = Color.Black.copy(alpha = 0.6f), fontSize = 13.sp)
        Text("₹ ${price}${per_moth}", fontWeight = FontWeight.SemiBold)
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
    tenants: List<Tenant>,
    onTenant:(String) -> Unit,
    onTenantRentUpdate: (String) -> Unit,
    onTenantMessage: (String) -> Unit,
    onTenantNotice: (String) -> Unit,
    onTenantDelete: (String) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
//            .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(13.dp)),
//            .padding(vertical = 24.dp, horizontal = 13.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Tenants", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        ROw {
            Column (
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ){
                tenants.forEach {
                    tenant ->
                    Tenant(
                        tenant = tenant,
                        onTenant = {
                            onTenant(tenant.name)
                        },
                        onTenantRentUpdate = {
                            onTenantRentUpdate(tenant.name)
                        },
                        onTenantMessage = {
                            onTenantMessage(tenant.name)
                        },
                        onTenantNotice = {
                            onTenantNotice(tenant.phone_number)
                        },
                        onTenantDelete = {
                            onTenantDelete(tenant.name)
                        }
                    )
                }
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
    tenant: Tenant,
    onTenant:() -> Unit,
    onTenantRentUpdate: () -> Unit,
    onTenantMessage: () -> Unit,
    onTenantNotice: () -> Unit,
    onTenantDelete: () -> Unit
){
    val is_rent_paid = true
    val is_notice_applied = false
    val expanded = remember { mutableStateOf<Boolean>(false) }
    val is_delete_dialog = remember { mutableStateOf<Boolean>(false) }
    val is_rent_dialog_open = remember { mutableStateOf<Boolean>(false) }
    val rent = remember { mutableStateOf<String>("") }
    val date = remember { mutableStateOf<Long>(0L) }
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
                .background(Color(0xFFebeef3).copy(alpha = 0.3f))
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
                    Text("Billionaire", fontWeight = FontWeight.Medium)
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Badge(
                            modifier = Modifier.border(1.dp, color = if(is_rent_paid) Color.Unspecified else Color(0xFFF75270), shape = CircleShape),
                            containerColor = if(is_rent_paid) Color(0xFF59AC77) else Color(0xFFF7CAC9),
                            contentColor = Color.White
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ){
                                Text(
                                    if(is_rent_paid) "Paid" else "Not Paid",
                                    fontSize = 12.sp,
                                    color =  if(is_rent_paid) Color.White else Color(0xFFDC143C)
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




