package com.example.billionairehari.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.layout.component.ROw

@Composable
fun OtpVerificationScreen(
    modifier: Modifier
){
    Column(
        modifier = Modifier.then(modifier)
            .fillMaxSize()
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "")
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(bottom = 60.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text("OTP Verification", fontSize = 24.sp)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Please enter the 6-digit code sent to", color = Color.Black.copy(0.6f))
                        Text("+91 8668072363", fontWeight = FontWeight.SemiBold)
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text("Enter Code")
                    val otp = remember { mutableStateOf("") }
                    BasicTextField(
                        value = otp.value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        onValueChange = {
                            if(it.length <= 6){
                                otp.value = it
                            }
                        },
                        decorationBox = {
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                repeat(6){
                                    val char = when {
                                        it >= otp.value.length -> ""
                                        else -> otp.value[it].toString()
                                    }
                                    val isFocused = otp.value.length === it
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(13.dp))
                                            .size(50.dp)
                                            .border(1.dp, color = if(isFocused) Color.Black else Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(char)
                                    }
                                }
                            }
                        }
                    )
                    AppButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ) {
                        Text("Verify Code")
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text("Didn't receive the code?")
                    Text("Resend code", color = Color.Blue)
                }
            }

        }
    }
}
