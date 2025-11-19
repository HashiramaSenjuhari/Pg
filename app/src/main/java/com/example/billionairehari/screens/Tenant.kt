package com.example.billionairehari.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.copy
import coil.compose.AsyncImage
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.contacts.dial
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.TenantViewModel
import com.google.android.material.chip.Chip

@Composable
fun TenantScreen(
    id:String,
    modifier:Modifier,
    context:Context,
    onNavigateToHistory:() -> Unit
) {
    val state_id = hiltViewModel<TenantViewModel>()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.then(modifier).padding(horizontal = 13.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 13.dp)
        ) {
            TenantProfileBar()
        }
        TenantRentDetail()
        TenantDetailShowcase(
            title = "Personal",
            details = listOf(
                DetailModal(
                    icon = TenantIcon,
                    title = "Age",
                    value = "18 year",
                ),
                DetailModal(
                    icon = TenantIcon,
                    title = "State",
                    value = "Puducherry",
                ),
                DetailModal(
                    icon = TenantIcon,
                    title = "Joined At",
                    value = "Jul 24,2024",
                )
            )
        )
        TenantDetailShowcase(
            title = "Contact Detail",
            details = listOf(
                DetailModal(
                    icon = TenantIcon,
                    title = "Phone Number",
                    value = PhoneNumberUtils.formatNumber("8668072364","IN"),
                    button = CardAction(
                        onClick = {
                            dial(context = context, number = "8668072363")
                        },
                        icon = Icons.Default.Call
                    )
                ),
                DetailModal(
                    icon = TenantIcon,
                    title = "alternate number",
                    value = PhoneNumberUtils.formatNumber("8668072364","IN"),
                    button = CardAction(
                        onClick = {
                            dial(context = context, number = "8668072363")
                        },
                        icon = Icons.Default.Call
                    )
                ),
                DetailModal(
                    icon = TenantIcon,
                    title = "Phone Number",
                    value = PhoneNumberUtils.formatNumber("8668072364","IN"),
                    button = CardAction(
                        onClick = {
                            dial(context = context, number = "8668072363")
                        },
                        icon = Icons.Default.Call
                    )
                )
            )
        )
        RentHistory(
            onNavigateToHistory = onNavigateToHistory
        )
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .padding(vertical = 24.dp)
        )
    }
}

@Composable
fun TenantProfileBar(){
    val expanded = rememberSaveable { mutableStateOf<Boolean>(false) }
    Box(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .height(130.dp)
            .background(Brush.linearGradient(colors = listOf(Color(0xFF97ABFF),Color(0xFF123597))))
            .padding(24.dp)
    ){
        ROw(
            modifier = Modifier.fillMaxSize().zIndex(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box{
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fpm1.narvii.com%2F7677%2Fd685e55df942779a816216f14e8123aea288fe3br1-735-734v2_uhq.jpg&f=1&nofb=1&ipt=2accb0e90ca2ac11b553fcc7a693750de9dd0c8dc4a830b1c5a1fdf152b9fae5",
                    contentDescription = "",
                    modifier = Modifier.clip(CircleShape)
                        .size(76.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    "Billionaire Hari",
                    color = Color.White,
                    fontSize = 24.sp
                )
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text(
                        "Room 101",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f))
                            .padding(horizontal = 9.dp, vertical = 6.dp)
                    )
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.clip(CircleShape)
                            .size(13.dp).background(Color(0xFF5fdba7)))
                        Text("Active",color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TenantRentDetail(){
    Column(
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ){
            DetailCard(
                title = "Rent Paid (current month)",
                icon = CalendarIcon,
                rent_due_price = 0,
                paid_text = "Paid"
            )
            DetailCard(
                title = "Rent Due Date",
                icon = CalendarIcon,
                total = "23 Oct",
                width = 1f
            )
        }
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ){
            DetailCard(
                title = "Due",
                icon = CalendarIcon,
                rent_due_price = 0,
                paid_text = "No Due"
            )
            DetailCard(
                title = "Rent Due Date",
                icon = CalendarIcon,
                total = "23 Oct",
                width = 1f
            )
        }
    }
}

data class CardAction(
    val icon: ImageVector,
    val onClick:() -> Unit
)

data class DetailModal(
    val icon: ImageVector = TenantIcon,
    val title:String,
    val value:String,
    val button: CardAction? = null
)

@Composable
fun TenantDetailsLayout(
    title:String,
    content:@Composable () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(width = 1.dp, color = Color.Black.copy(0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun TenantDetailShowcase(
    title:String,
    details: List<DetailModal>
){
    TenantDetailsLayout(
        title = title
    ) {
        details.forEach {
                (icon, title, value,button) ->
            TenantDetailCard(
                icon = icon,
                title = title,
                great = value,
                button = button
            )
        }
    }
}



@Composable
private fun TenantDetailCard(
    icon: ImageVector,
    title:String,
    great:String,
    button: CardAction? = null
){
    ROw(
        modifier = Modifier.fillMaxWidth()
    ){
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(
                TenantIcon,
                contentDescription = "",
                modifier = Modifier.clip(CircleShape)
                    .size(30.dp)
                    .background(Color(0xFFB2B0E8))
                    .padding(6.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    title.toUpperCase(),
                    color =  Color.Black.copy(alpha = 0.4f),
                    fontSize = 12.sp
                )
                Text(great)
            }
        }
        if(button != null){
            IconButton(
                onClick = button.onClick
            ) {
                Icon(button.icon, contentDescription = "",modifier = Modifier.size(16.dp))
            }
        }
    }
}

data class RentHistory(
    val month:String,
    val amount:String,
    val paid_date:String,
    val paid_amount:String
)

val history = listOf<RentHistory>(
    RentHistory(
        amount = "6000",
        paid_date = "2024-06-01",
        month = "Jun 2024",
        paid_amount = "6000"
    ),
    RentHistory(
        amount = "6000",
        paid_date = "2024-06-01",
        month = "Jun 2024",
        paid_amount = "6000"
    ),
    RentHistory(
        amount = "6000",
        paid_date = "2024-06-01",
        month = "Jun 2024",
        paid_amount = "3000"
    )
)

@Composable
fun RentHistory(
    onNavigateToHistory: () -> Unit
){
    Column(
        modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rent History", fontWeight = FontWeight.SemiBold)
            AppButton(
                onClick = onNavigateToHistory,
                padding = PaddingValues(horizontal = 13.dp)
            ) {
                Text("View All >")
            }
        }
        Text("Last 3 month")
        Column(
            modifier = Modifier.clip(RoundedCornerShape(13.dp))
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            history.slice(0..2).forEach {
                rent ->
                TableRow(
                    month = rent.month,
                    amount = rent.amount,
                    paid_date = rent.paid_date,
                    amount_paid = rent.paid_amount
                )
            }
        }
    }
}

@Composable
fun TableRow(
    month:String,
    amount:String,
    amount_paid:String,
    paid_date:String
){
    val remaning_amount = (amount.toIntOrNull() ?: 0) - (amount_paid.toIntOrNull() ?: 0)
    val paid_status = when {
        remaning_amount === 0 -> "paid"
        remaning_amount < (amount.toIntOrNull() ?: 0) -> "partially paid"
        else -> "not paid"
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.1.dp
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(color = Color.Black.copy(alpha = 0.06f), width = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ROw(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    Text(
                        month,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )

                Badge(
                    modifier = Modifier.clip(CircleShape)
                        .background(
                            if(paid_status == "paid"){
                                Color(0xFFeaf3ec)
                            }
                            else if(paid_status == "partially paid"){
                                Color(0xFFF7CB73)
                            }
                            else{
                                Color(0xFFD0342C)
                            })
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    containerColor = Color.Transparent,
                    contentColor = if(paid_status == "paid"){
                        Color(0xFF79c687)
                    }
                    else{
                        Color.White
                    },
                ) {
                    Text(paid_status.split(" ").map { it -> it.replaceFirstChar { c -> c.uppercaseChar() } }.joinToString(" "))
                }
            }
            ROw (
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Due date: ${paid_date}",
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.4f),)
                Text(
                    "₹${formatIndianRupee(amount_paid)}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun DropDownList(expanded: MutableState<Boolean>){
    DropdownMenu(
        onDismissRequest = {
            expanded.value = false
        },
        expanded = expanded.value,
        modifier = Modifier.width(160.dp)
    ){
        DropdownMenuItem(
            text = {
                Text("Edit") },
                onClick = {}
        )
        DropdownMenuItem(
            text = {
                Text("") },
            onClick = {}
        )
        DropdownMenuItem(
            text = {
                Text("Edit") },
            onClick = {}
        )
        DropdownMenuItem(
            text = {
                Text("Edit") },
            onClick = {}
        )
    }
}