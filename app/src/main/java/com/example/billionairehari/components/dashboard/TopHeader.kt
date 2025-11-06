package com.example.billionairehari.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.NavigationAction

@Composable
fun TopBar(
    name:String,
    navActions: NavigationAction
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = {
                    navActions.navigateToProfile()
                },
                modifier = Modifier
                    .border(1.dp, shape = RoundedCornerShape(13.dp), color = Color.Black)
                    .shadow(2.dp, shape = RoundedCornerShape(13.dp), spotColor = Color(0xFF909090))
                    .size(40.dp)
                    .background(Color(0xFF310A69)),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "",modifier = Modifier.size(21.dp))
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Welcome", fontSize =  12.sp)
                Text("Billionaire's Pg", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
        }
        IconButton(
            onClick = {
                navActions.navigateToSetting()
            }
        ) {
            Icon(Icons.Default.Settings, contentDescription = "")
        }
    }
}