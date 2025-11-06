package com.example.billionairehari.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun Activate(onCheckChange:(Boolean) -> Unit,checked:Boolean){
    Switch(
        checked = checked,
        onCheckedChange = {
            onCheckChange(it)
        },
        colors = SwitchDefaults.colors(
            checkedTrackColor = Color(0xFFB2B0E8),
            uncheckedBorderColor = Color(0xFFB3B3B3),
            uncheckedTrackColor = Color(0xFFFFFF)
        )
    )
}