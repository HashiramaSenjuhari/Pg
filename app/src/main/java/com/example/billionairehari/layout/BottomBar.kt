package com.example.billionairehari.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composables.TicketsIcon
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.icons.Dashboard
import com.example.billionairehari.icons.Rooms
import com.example.billionairehari.icons.TenantsIcon

@Composable
fun BottomBar(navigation: NavigationAction){

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
        MainBar(id = 3,name = "Tickets",route = "tickets",icon = TicketsIcon,navigation = {
            navigation.navigateToContacts()
        })
    )

    val selectedDestination = rememberSaveable { mutableStateOf(bars[0].name) }
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(13.dp)),
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy((-9).dp)
        ) {
            bars.forEach { bar ->
                NavigationBarItem(
                    onClick = {
                        bar.navigation()
                        selectedDestination.value = bar.name
                    },
                    label = {
                        Text(bar.name, fontWeight = if(selectedDestination.value == bar.name) FontWeight.Bold else FontWeight.Normal)
                    },
                    selected =  selectedDestination.value == bar.name,
                    icon = {
                        Icon(imageVector = bar.icon,bar.name)
                    }
                )
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