package com.example.billionairehari.screens

import android.text.Layout
import android.widget.GridLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.layout.DynamicShowcaseScreen
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.R
import com.example.billionairehari.viewmodels.GetAllPaymentHistoryViewModel

@Composable
fun PaymentHistory(
    modifier:Modifier = Modifier,
    navController: NavController,
    viewmodel: GetAllPaymentHistoryViewModel = hiltViewModel()
){
    val scrollState = rememberScrollState()

    val payments = viewmodel.history.collectAsState()

    DynamicShowcaseScreen(
        title = "Payments History",
        navController = navController,
        placeholder = "Search Payment",
        onClickFilter = {},
        search_route = "",
        scrollState = scrollState
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(bottom = 240.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            payments.value.forEach {
                PaymentCard(
                    name = it.tenantName,
                    roomName = it.roomName,
                    amount = it.amount,
                    onClick = {
                        navController.navigate("${Destinations.PAYMENTS_ROUTE}/${it.id}")
                    },
                    paymentDate = it.paymentDate,
                    dueDate = it.dueDate
                )
            }
        }
    }
}

@Composable
fun PaymentCard(
    name:String,
    roomName:String,
    amount:Int,
    paymentDate:String,
    dueDate:String,
    onClick:() -> Unit
){
    Surface(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .drawBehind{
                drawLine(
                    start = Offset(x = size.width / 6, y = size.height),
                    end = Offset(x = size.width,y = size.height),
                    color = Color.Black.copy(0.3f),
                    strokeWidth = 1f
                )
            },
        color = Color.White,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = "",
                            tint = Color.Black.copy(0.2f),
                            modifier = Modifier.rotate(135f)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(name, fontSize = 16.sp)
                        ROw(
                            horizontalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            Text(roomName, color = Color.Black.copy(0.6f))
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text("₹${amount}", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                Text(paymentDate, fontSize = 13.sp, color = Color.Black.copy(0.6f))
            }
        }
    }
}