package com.example.billionairehari.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    shape:Shape = RoundedCornerShape(16.dp),
    modifier:Modifier = Modifier,
    onClick:() -> Unit,
    padding: PaddingValues = PaddingValues(0.dp),
    enabled:Boolean = true,
    border: BorderStroke = BorderStroke(0.dp, color = Color.Transparent),
    containerColor:Color = Color.Transparent,
    contentColor:Color = Color.Black,
    content:@Composable () -> Unit,
){
    Button(
        modifier = Modifier.then(modifier),
        shape = shape,
        contentPadding = padding,
        onClick = onClick,
        enabled = enabled,
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        content()
    }
}