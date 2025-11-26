package com.example.billionairehari.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.OutlinedInput
import com.example.billionairehari.layout.component.ROw

@Composable
fun SignInScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 130.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Text("Sign In", fontSize = 24.sp)
                Text("Welcome back please login to your account")
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(13.dp))
                    .fillMaxWidth()
                    .padding(13.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    OutlinedInput(
                        label = "Phone",
                        leadingIconInput = {
                            Text("+91")
                        },
                        leadingIcon = Icons.Outlined.Call,
                        value = "",
                        onValueChange = {},
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    "We'll send an OTP to this number for verification",
                    fontSize = 13.sp,
                    color = Color.Black.copy(0.6f)
                )
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                    padding = PaddingValues(vertical = 13.dp),
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    Text("Sign Up", fontWeight = FontWeight.Normal)
                }
            }
            ROw(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Create an account?",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(Destinations.SIGNUP_ROUTE)
                        }
                    )
                )
            }
//            ROw(
//                modifier = Modifier.padding(horizontal = 40.dp),
//                horizontalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.46f))
//                Text("or", color = Color.Black.copy(0.6f))
//                HorizontalDivider()
//            }

        }
    }
}