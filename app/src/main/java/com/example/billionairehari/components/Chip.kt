package com.example.billionairehari.components

import android.R
import androidx.annotation.ColorInt
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(
    onDismiss:() -> Unit,
    label:String,
){
    FilterChip(
        onClick = onDismiss,
        label = {
            Text(
                label,
                fontSize = 12.sp
            )
        },
        trailingIcon = {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "close",modifier = Modifier.clickable{
                    }.size(13.dp),
                )
        },
        selected = true,
        shape = RoundedCornerShape(60.dp),
        modifier = Modifier.wrapContentSize(),
    )
}