package com.example.billionairehari.components

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.layout.component.ROw

@Composable
fun FormButton(
    onSubmit : () -> Unit,
    onReset : () -> Unit,
    isLoading:Boolean,
    primaryColor: Color = Color(0xFF060606)
){
    ROw(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        AppButton(
            onClick = onReset,
            containerColor = Color.White,
            contentColor = Color.Black,
            border = BorderStroke(1.dp, color = Color.Black.copy(0.1f)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(0.5f),
            enabled = !isLoading,
            padding = PaddingValues(vertical = 13.dp)
        ) {
            Text("Reset")
        }
        AppButton(
            onClick = onSubmit,
            containerColor = Color.Black.copy(0.8f),
            contentColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            padding = PaddingValues(vertical = 13.dp)
        ) {
            if(isLoading){
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(24.dp),
                    color = Color.Black
                )
            }else {
                Text("Add")
            }
        }
    }
}