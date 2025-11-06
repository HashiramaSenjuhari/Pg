package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.screens.ROw

data class FilterOption<T>(
    val name:String,
    val count:String,
    val type: T
)

@Composable
fun FilterCard(
    name: String,
    count:String,
    onClick:() -> Unit,
    isClicked:Boolean
){
    val color = if(isClicked) Color.White else Color.Black
        ROw(
            modifier = Modifier.clip(CircleShape)
                .background(if(isClicked) Color.Black else Color.White)
                .border(1.dp, color = if(isClicked) Color.Transparent else Color(0xFF909090),shape = CircleShape)
                .padding(horizontal = 13.dp, vertical = 13.dp)
                .clickable(
                    enabled = true,
                    onClick = onClick
                ),
            horizontalArrangement =  Arrangement.spacedBy(13.dp)
        ) {
            Text(name, color = color,fontSize = 12.sp)
            Text(count, fontSize = 12.sp,color = color)
        }
}