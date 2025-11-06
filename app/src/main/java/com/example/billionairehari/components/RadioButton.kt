package com.example.billionairehari.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.screens.ROw

data class RentOption(
    val option:String = "Not Paid",
    val boolean:Boolean = false
)

val rent_paid = mapOf("Paid" to true,"Not Paid" to false)

@Composable
fun SelectOption(onValueChange:(Boolean) -> Unit,selectedOption:Boolean){
    ROw(
        modifier = Modifier.selectableGroup()
            .clip(RoundedCornerShape(13.dp))
            .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
    ) {
        rent_paid.forEach {
                value ->
            val is_selected = selectedOption === value.value
            Box(
                modifier = Modifier.width(80.dp)
                    .selectable(
                    onClick = {
                        onValueChange(value.value)
                              },
                    selected = selectedOption == value.value,
                    role = Role.RadioButton
                ).background(if(is_selected) Color(0xFFB2B0E8) else Color.White)
                    .padding(horizontal = 6.dp, vertical = 13.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(value.key, fontSize = 12.sp,fontWeight = FontWeight.Medium,color = if(is_selected) Color.White else Color.Black)
            }
        }
    }
}