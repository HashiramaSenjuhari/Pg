package com.example.billionairehari.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.Screens
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.dashboard.ActionButton
import com.example.billionairehari.core.data.local.entity.PaymentStatus
import com.example.billionairehari.core.data.local.entity.PaymentType
import com.example.billionairehari.icons.ClockIcon
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.utils.formatDate
import com.example.billionairehari.viewmodels.GetPaymentDetailViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Payment(
    id:String,
    navController: NavController
){
    val viewmodel:GetPaymentDetailViewModel = hiltViewModel(
        creationCallback = { factory: GetPaymentDetailViewModel.GetPaymentDetailFactory ->
            factory.create(id)
        }
    )
    val details = viewmodel.paymentDetails.collectAsState()
    val createdAt = details.value.createdAt.formatDate("DD MMM YYYY")
    val dueDate = details.value.due_date.formatDate("dd MMM YYYY")

    Column {
        PaymentScreen(
            paymentStatus = details.value.payment_status,
            createdAt = "${details.value.paymentTime} on ${createdAt}",
            onClick = {
                navController.popBackStack()
            }
        )
        PaymentDetailCard(
            name = details.value.tenantName,
            roomName = details.value.roomName,
            amountPaid = details.value.amount,
            paymentType = details.value.payment_type,
            dueDate = dueDate,
            onClickHistory = {
                navController.navigate("${Screens.TENANT_PAYMENTS_SCREEN}/${details.value.tenantId}")
            },
            onClickEdit = {},
            onClickShare = {}
        )
    }
}

@Composable
fun PaymentScreen(
    paymentStatus: PaymentStatus,
    createdAt:String,
    onClick:() -> Unit
){
    val isPaid = if(paymentStatus == PaymentStatus.PAID) true else false
    val paymentHeaderStatus = if(isPaid) "Paid Completely" else "Paid Partial"
    val headerContainerColor = if(isPaid) 0xff198754 else 0xFFf1c232
    val headerContentColor = if(isPaid) Color.White else Color.Black
    CompositionLocalProvider(
        LocalContentColor provides headerContentColor
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth()
                .background(Color(headerContainerColor))
                .padding(top = 30.dp, start = 6.dp, end = 13.dp, bottom = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -3.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(paymentHeaderStatus, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(createdAt ,fontSize = 13.sp, color = Color.White.copy(0.9f))
            }
        }
    }
}

@Composable
fun PaymentUserDetail(
    name:String,
    roomName:String,
    amountPaid:Int,
    paymentType: PaymentType
){
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier.clip(RoundedCornerShape(9.dp))
                        .size(34.dp)
                        .background(Color.Black.copy(0.1f))
                        .padding(6.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(name, fontSize = 16.sp)
                    Text(roomName, color = Color.Black.copy(0.4f))
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text("₹${amountPaid}", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(paymentType.name, fontSize = 13.sp, color = Color.Black.copy(0.6f))
            }
        }
    }
}

@Composable
fun PaymentDetailCard(
    name:String,
    roomName:String,
    amountPaid:Int,
    paymentType: PaymentType,
    dueDate:String,
    onClickHistory:() -> Unit,
    onClickEdit:() -> Unit,
    onClickShare:() -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(0.05f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            PaymentUserDetail(
                name = name,
                roomName = roomName,
                amountPaid = amountPaid,
                paymentType = paymentType
            )
            ROw(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "",modifier = Modifier.size(19.dp))
                    Text("Due Date", fontSize = 13.sp, color = Color.Black.copy(0.6f), fontWeight = FontWeight.SemiBold)
                }
                Text(dueDate, fontWeight = FontWeight.SemiBold)
            }
            HorizontalDivider(color = Color.Black.copy(0.09f))
            ROw(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ActionButton(
                    onClick = onClickHistory,
                    name = "View History",
                    icon = ClockIcon,
                    containerColor = Color.White,
                    border = BorderStroke(width = 0.dp, color = Color.Transparent),
                )
                ActionButton(
                    onClick = onClickEdit,
                    name = "Edit",
                    icon = Icons.Default.Edit,
                    containerColor = Color.White,
                    border = BorderStroke(width = 0.dp, color = Color.Transparent)
                )
                ActionButton(
                    onClick = onClickShare,
                    name = "Share Receipt",
                    icon = Icons.Default.Share,
                    containerColor = Color.White,
                    border = BorderStroke(width = 0.dp, color = Color.Transparent)
                )
            }
        }
    }
}