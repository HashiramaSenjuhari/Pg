package com.example.billionairehari.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.billionairehari.Destinations
import com.example.billionairehari.components.FilterCard
import com.example.billionairehari.components.FilterOption
import com.example.billionairehari.components.SearchBar
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.CancelIcon
import com.example.billionairehari.icons.FilterIcon
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.TenantViewModel
import com.example.billionairehari.viewmodels.TenantsViewModel
import kotlin.math.min
import com.example.billionairehari.R


@Composable
fun TenantsScreen(
    modifier:Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    DynamicShowcaseScreen(
        title = "Tenants",
        placeholder = "Search Tenant",
        scrollState = scrollState,
        navController = navController,
        search_route = Destinations.TENANT_SEARCH_ROUTE
    ) {
        TenantCards(
            scrollState = scrollState,
            context = context,
            navController = navController
        )
    }
}


data class TenantData(
    val id:String = "",
    val name:String = "",
    val room_name:String = "",
    val image:String = "",
    val is_paid:Boolean = true,
    val is_noticed:Boolean = true,
    val phone:String = "8668072363"
)


val tenants = listOf<TenantData>(
    TenantData(
        id = "",
        name = "Apple",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = false
    ),
    TenantData(
        id = "",
        name = "BillionaireHari",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = false
    ),
    TenantData(
        id = "",
        name = "BillionaireHari",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    ),
    TenantData(
        id = "",
        name = "Great",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    ),
    TenantData(
        id = "",
        name = "Hari",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    ),
    TenantData(
        id = "",
        name = "Mercedez",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    ),
    TenantData(
        id = "",
        name = "BillionaireHari Billionaire",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    ),
    TenantData(
        id = "",
        name = "BillionaireHari",
        room_name = "Billionaire",
        image = "",
        is_noticed = false,
        is_paid = true
    )

)

@Composable
fun TenantCards(
    scrollState: ScrollState,
    context: Context,
    navController: NavController,
    viewmodel: ViewModel = TenantsViewModel()
){
    Column(
        modifier = Modifier.verticalScroll(state = scrollState)
            .padding(bottom = 240.dp)
    ) {
        tenants.sortedBy { it -> it.name.first().uppercase() }.forEach {
            it ->
                    TenantCard(
                        tenant = it,
                        onClick = {
                            navController.navigate("tenants/${it.id}")
                        },
                        onClickCall = {
                            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                                val uri = Uri.parse("tel:${it.phone}")
                                setData(uri)
                            }
                            context.startActivity(callIntent)
                        },
                        onCLickMessage = {
                            val sendMessage = Intent(Intent.ACTION_VIEW).apply {
                                type = "text/plain"
                                data = Uri.parse("https://api.whatsapp.com/send?phone=${it.phone}")
                                setPackage("com.whatsapp")
                            }
                            context.startActivity(sendMessage)
                        }
                    )
        }
    }
}

@Composable
fun TenantCard(
    tenant:TenantData,
    onClick:() -> Unit,
    onClickCall:() -> Unit,
    onCLickMessage:() -> Unit
){
    val color = if(tenant.is_paid)Color.Green else Color.Red
    val name = if(tenant.name.length > 24){
        tenant.name.take(16) + "..."
    }else {
        tenant.name
    }
    ROw(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind{
                drawLine(
                    start = Offset(x = (size.width / 5f).toFloat(), y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    color = Color.Black.copy(alpha = 0.6f),
                    strokeWidth = 1f
                )
            }
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = MutableInteractionSource(),
                indication = null
            )
            .padding(horizontal = 13.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            AsyncImage(
                model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%2Fid%2FOIP.Zw3N2LWriAvzVtUMVjk8tQHaE8%3Fr%3D0%26pid%3DApi&f=1&ipt=0795aefdffb76fa8d214f82ca5f558d260b176b7592dd170a6e0e414db8494f7&ipo=images",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(
                    RoundedCornerShape(13.dp)
                ).size(50.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.width(190.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        name,
                        fontSize = 16.sp
                    )
                    Badge(
                        modifier = Modifier
                            .width(80.dp)
                            .background(Brush.horizontalGradient(colors = listOf(Color.White,color,color,Color.White)))
                            .padding(horizontal = 13.dp),
                        containerColor = Color.Transparent
                    ){
                        Text(if(tenant.is_paid) "Paid" else "Not Paid", color = if(tenant.is_paid) Color.Black else Color.White)
                    }
                }
                Text(
                    tenant.room_name,
                    fontSize = 13.sp,
                    color =  Color.Black.copy(0.6f)
                )
            }
        }
        ROw {
            IconButton(
                onClick = onClickCall
            ) {
                Icon(Icons.Outlined.Call, contentDescription = "", tint = Color.Black.copy(0.6f),modifier = Modifier.size(24.dp))
            }
            IconButton(
                onClick = onCLickMessage
            ) {
                Icon(painter = painterResource(R.drawable.outline_chat_24), contentDescription = "", tint = Color.Black.copy(0.6f))
            }
        }
    }
}