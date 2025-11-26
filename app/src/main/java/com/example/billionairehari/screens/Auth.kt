package com.example.billionairehari.screens

import android.view.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.billionairehari.modal.Grid

@OptIn(ExperimentalMaterial3Api::class)
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
                .fillMaxHeight()
                .padding(start = 40.dp, end = 40.dp, top = 240.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Column {
                        Text("Manage", fontSize = 60.sp, fontWeight = FontWeight.Black)
                        Text("Track", fontSize = 60.sp, fontWeight = FontWeight.Black)
                        Text("Connect ", fontSize = 60.sp, fontWeight = FontWeight.Black)
                    }
                    Text("PG Simplified in one app", fontSize = 19.sp, fontWeight = FontWeight.Medium)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AppButton(
                        onClick = {
                        },
                        modifier = Modifier.fillMaxWidth(),
                        padding = PaddingValues(vertical = 13.dp),
                        border = BorderStroke(1.dp, color = Color.Black.copy(0.3f))
                    ) {
                        ROw(
                            horizontalArrangement = Arrangement.spacedBy(13.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.google_icon_logo),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                            Text("Sign with Google", fontSize = 16.sp)
                        }
                    }
                    Column {
                        Grid {
                            LightLabel("By signing up, you argree to our")
                            Link("Terms, Privacy Policy", onClick = {})
                            LightLabel("and")
                            Link("Cookie Use", onClick = {})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LightLabel(text:String){
    Text(text, fontSize = 13.sp,color = Color.Black.copy(0.6f))
}

@Composable
fun Link(
    text:String,
    onClick:() -> Unit
){
    Text(text, color = Color.Blue, fontSize = 13.sp,modifier = Modifier.clickable(
        onClick = onClick
    ))
}