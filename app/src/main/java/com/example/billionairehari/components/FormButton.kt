package com.example.billionairehari.components

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormButton(
    onSubmit : () -> Unit,
    onReset : () -> Unit,
    primaryColor: Color = Color(0xFF060606),
    isLoading:Boolean
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ){
        Button(
            onClick = onReset,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(0.5f).height(50.dp)
                .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(14.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(14.dp), spotColor = Color(0xFFD1D1D1))
                .background(Color.White),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFF)
            ),
            enabled = !isLoading
        ){
            Text("Reset",fontSize = 14.sp,color = Color(0xFF060606))
        }
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor
            ),
            enabled = !isLoading
        ) {
            Text( if(isLoading) "Billionaire" else "Apply",fontSize = 14.sp)
        }
    }
}