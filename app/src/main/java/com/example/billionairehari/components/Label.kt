package com.example.billionairehari.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun Label(name:String, is_important:Boolean = false, fontSize: TextUnit= 13.sp, modifier: Modifier = Modifier, textModifier:Modifier = Modifier) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(name, fontWeight = FontWeight.Medium, fontSize = fontSize,modifier = Modifier.then(modifier))
        if(is_important){
            Text("*",color = Color.Red)
        }
    }
}