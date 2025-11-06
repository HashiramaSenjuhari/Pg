package com.example.billionairehari.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.billionairehari.components.FilterCard
import com.example.billionairehari.components.FilterOption
import com.example.billionairehari.components.SearchBar
import com.example.billionairehari.icons.CalendarIcon
import com.example.billionairehari.icons.CancelIcon
import com.example.billionairehari.icons.FilterIcon
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.model.Tenant
import com.example.billionairehari.model.TenantUi
import kotlin.math.min


@Composable
fun TenantsScreen(
    modifier:Modifier,
    navController: NavController
) {
//    val tenants = hiltViewModel<TenantsViewModel>()
//    val tenants_main = tenants.filtered.collectAsState().value
//    val filter = tenants_main.filter
    val search = rememberSaveable { mutableStateOf<String>("") }
//    val tenants_final = tenants_main.tenant.filter { tenant ->  tenant.name.contains(search.value, ignoreCase = true) }
//
//    val all_count = tenants_main.tenant.count()
//    val rent_dues_count = tenants_main.tenant.count { tenant -> !tenant.rent_paid }
//    val notice_count = tenants_main.tenant.count { tenant -> tenant.under_notice }

//    val maxHeight = 120.dp
//    val minheight = 40.dp
//
//    val headerSize = remember { mutableStateOf<Dp>(maxHeight) }
//    val opacity = remember { mutableFloatStateOf(1f) }
//    val scroll = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                val delta = available.y
//                val new = headerSize.value + delta.dp
//                val prev = headerSize.value
//
//                headerSize.value = new.coerceIn(minheight,maxHeight)
//                val consumed = new - prev
//
//                opacity.value = headerSize.value / maxHeight
//                return Offset(0f,consumed.value)
//            }
//        }
//    }
    Column(
        modifier = Modifier.then(modifier)
            .fillMaxHeight()
            .background(Color(0xFFF6F6F6))
            .padding(top = 24.dp,start = 13.dp,end = 13.dp)
//            .nestedScroll(scroll)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SearchBar(
                value = search.value,
                placeholder = "Search Tenant",
                onValueChange = {
                    search.value = it
                },
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ){
                        Icon(FilterIcon, contentDescription = "")
                    }
                }
            )
//            TenantFilterCards(
//                filter = filter,
//                onFilter = {
//                    tenants.update_filter(it)
//                },
//                all_count = all_count,
//                notice_count = notice_count,
//                rent_dues_count = rent_dues_count
//            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
//        TenantsCard(
//            tenants = tenants_final,
//            navController = navController
//        )
    }
}

//
//@Composable
//fun TenantFilterCards(
//    filter: TenantFilterType,
//    onFilter:(TenantFilterType) -> Unit,
//    all_count:Int,
//    rent_dues_count:Int,
//    notice_count:Int
//){
//    val filters = listOf<FilterOption<TenantFilterType>>(
//        FilterOption<TenantFilterType>(name = "All", count = "$all_count", type = TenantFilterType.ALL),
//        FilterOption<TenantFilterType>(name = "Rent Due",count = "$rent_dues_count",type = TenantFilterType.RENT_DUES),
//        FilterOption<TenantFilterType>(name = "Notice",count = "$notice_count",type = TenantFilterType.NOTICE),
//    )
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(6.dp)
//    ) {
//        items(filters.size){
//            FilterCard(
//                name = filters[it].name,
//                count = filters[it].count,
//                isClicked = filters[it].type == filter,
//                onClick = {
//                    onFilter(filters[it].type)
//                }
//            )
//        }
//    }
//}

@Composable
fun TenantsCard(
    tenants:List<TenantUi>,
    navController: NavController
){
    val scrollState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(13.dp),
        modifier = Modifier.drawWithContent{
            drawContent()
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.06f),Color.Transparent),
                    startY = 0f,
                    endY = 60f
                )
            )
        },
        contentPadding = PaddingValues(top = 13.dp),
        state = scrollState
    ) {
        items(tenants.size){
            TenantCard(
                tenant = tenants[it],
                onClick = {
                    navController.navigate("tenants/${tenants[it].id}")
                }
            )
        }
    }
}

@Composable
fun CardLabel(
    name:String,
    icon: ImageVector,
    value:String
){
    ROw {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = "",modifier = Modifier.size(20.dp))
            Text("$name: ",fontSize = 13.sp)
        }
        Text(value,fontSize = 13.sp)
    }
}

@Composable
fun TenantCard(tenant: TenantUi,onClick:() -> Unit){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            ROw(
                horizontalArrangement =  Arrangement.spacedBy(17.dp)
            ) {
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%2Fid%2FOIP.Zw3N2LWriAvzVtUMVjk8tQHaE8%3Fr%3D0%26pid%3DApi&f=1&ipt=0795aefdffb76fa8d214f82ca5f558d260b176b7592dd170a6e0e414db8494f7&ipo=images",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp).clip(CircleShape),
                    contentDescription = ""
                )
                Column (
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ){
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text("Billionaire hari", fontSize = 16.sp)
                        if(tenant.under_notice){
                            Badge{
                                ROw(
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.Warning, contentDescription = "",modifier = Modifier.size(16.dp))
                                    Text("Notice")
                                }
                            }
                        }
                    }
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.offset(x = (-4).dp)
                    ) {
                        Icon(RoomIcon, contentDescription = "",tint = Color(0xFF8e8e9c),modifier = Modifier.size(18.dp))
                        Text(tenant.name, fontSize = 13.sp,color = Color(0xFF8e8e9c))
                    }
                }
            }
            HorizontalDivider(color = Color(0xFFf2f2f2))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val color = if(tenant.current_rent_paid) Color(0xFF1DB054) else Color(0xFFFC7B81)
                    val text = if(tenant.current_rent_paid) "Paid" else "Not Paid"
                    val icon = if(tenant.current_rent_paid) Icons.Default.CheckCircle else Icons.Default.Close
                    Icon(icon, contentDescription = "",tint = color,modifier = Modifier.size(16.dp))
                    Text(text,color = color, fontSize = 12.sp)
                }
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(CalendarIcon, contentDescription = "",modifier = Modifier.size(16.dp))
                    Text("20 Sep 2025", fontSize = 12.sp)
                }
            }
        }
    }
}