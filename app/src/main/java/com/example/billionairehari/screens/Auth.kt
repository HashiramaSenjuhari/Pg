package com.example.billionairehari.screens

import android.view.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.R
import com.example.billionairehari.components.OutlinedInput
import com.example.billionairehari.screens.auth.SignInScreen

@Composable
fun AuthScreen(
    modifier:Modifier,
    navController: NavController
){
    Column(
        modifier = Modifier.then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.80f)
                .fillMaxWidth()
        ) {

        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                AppButton(
                    onClick = {
                        navController.navigate(Destinations.SIGNIN_ROUTE)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    padding = PaddingValues(vertical = 13.dp),
                    border = BorderStroke(1.dp, color = Color.Black.copy(0.3f))
                ) {
                    Text("Sign in")
                }
                AppButton(
                    onClick = {
                        navController.navigate(Destinations.SIGNUP_ROUTE)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    padding = PaddingValues(vertical = 13.dp),
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    Text("Sign up")
                }
            }
        }
    }
}
