package com.example.billionairehari.layout

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composables.TicketsIcon
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.icons.Dashboard
import com.example.billionairehari.icons.Rooms
import com.example.billionairehari.icons.TenantsIcon

@Composable
fun BottomBar(
    route:String,
    navigation: NavigationAction){

    val bars = listOf<MainBar>(
        MainBar(id = 0,name = "Dashboard",route = "dashboard", icon = Dashboard, navigation = {
            navigation.navigateToDashboard()
        }),
        MainBar(id = 1,name = "Rooms",route = "rooms",icon = Rooms, navigation = {
            navigation.navigateToRooms()
        }),
        MainBar(id = 2,name = "Tenants",route = "tenants",icon = TenantsIcon, navigation = {
            navigation.navigateToTenants()
        }),
//        MainBar(id = 3,name = "Tickets",route = "tickets",icon = TicketsIcon,navigation = {
//            navigation.navigateToContacts()
//        })
    )

    val selectedDestination = rememberSaveable { mutableStateOf(bars[0].name) }
    NavigationBar(
        modifier = Modifier.height(60.dp)
            .drawBehind{
                drawLine(
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = size.width, y = 0f),
                    color = Color.Black.copy(0.6f),
                    strokeWidth = 1f
                )
            },
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bars.forEach { bar ->
                val selected = route == bar.route
                val color = animateColorAsState(
                    targetValue = if(selected) Color.Black else Color.Black.copy(0.6f)
                )
                val top_color = animateColorAsState(
                    targetValue = if(selected) Color.Black else Color.Transparent
                )
                Column(
                    modifier = Modifier
                        .width(70.dp)
                        .fillMaxHeight()
                        .clickable(
                        enabled = true,
                        onClick = {
                            if(!selected){
                                bar.navigation()
                            }
                        },
                        role = Role.Button,
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .animateContentSize()
                            .clip(RoundedCornerShape(bottomStart = 13.dp, bottomEnd = 13.dp))
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(top_color.value)
                    )
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                bar.icon,
                                contentDescription = "",
                                modifier = Modifier.size(26.dp),
                                tint = color.value
                            )
                            Text(
                                bar.name,
                                fontSize = 13.sp,
                                color = color.value,
                                fontWeight = if(selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

data class MainBar(
    val id:Int,
    val name:String,
    val route:String,
    val icon: ImageVector,
    val navigation:() -> Unit
)