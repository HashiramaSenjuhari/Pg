package com.example.billionairehari.components.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.icons.RemaindIcon
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.ui.theme.robotoFontFamily
import com.example.billionairehari.viewmodels.GetTenantPaidCountViewModel

@Composable
fun Details(
    rent_paid: GetTenantPaidCountViewModel = hiltViewModel()
){
    val paid = rent_paid.paid.collectAsState()
    val not_paid = rent_paid.not_paid.collectAsState()
    val partial_paid = rent_paid.partial_paid.collectAsState()
    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        Text("Rent Info", fontSize = 16.sp,modifier = Modifier.offset(x = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 13.dp, horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                RentDetailCard(
                    modifier = Modifier.fillMaxWidth()
                        .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                    type = RentDetailType.PAID,
                    count = paid.value,
                )
                RentDetailCard(
                    modifier = Modifier.fillMaxWidth()
                        .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                    type = RentDetailType.PARTIAL_PAID,
                    count = partial_paid.value,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(13.dp), spotColor = Color.Black.copy(0.1f))
                    .background(Color.White)
                    .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                RentDetailCard(
                    modifier = Modifier.fillMaxWidth(),
                    type = RentDetailType.NOT_PAID,
                    count = not_paid.value
                )
                Box(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                ){
                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Color.White),
                        border = BorderStroke(2.dp, color = Color.Black.copy(0.1f)),
                        padding = PaddingValues(vertical = 13.dp),
                        onClick = {}
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(RemaindIcon, contentDescription = "", tint =  Color(0xFF1B1B1B), modifier =  Modifier.size(16.dp))
                            Text("Remaind To Pay", fontSize = 12.sp, color =  Color(0xFF1B1B1B))
                        }
                    }
                }
            }
        }
    }
}

enum class RentDetailType {
    PAID,
    NOT_PAID,
    PARTIAL_PAID
}

@Composable
fun RentDetailCard(
    modifier:Modifier = Modifier,
    type: RentDetailType,
    count:Int
){
    Column {
        Row(
            modifier = Modifier.then(modifier)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(13.dp), spotColor = Color.Black.copy(0.1f))
                .background(Color.White)
                .clip(RoundedCornerShape(13.dp))
                .padding(13.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                if(type == RentDetailType.PAID){
                    "Paid"
                }else if(type == RentDetailType.PARTIAL_PAID){
                    "Partial Paid"
                }else {
                    "Not-Paid"
                },
                fontSize = 13.sp,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Medium
            )
            Column(
            ) {
                Text(count.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = robotoFontFamily,color =  if(type == RentDetailType.PAID) {Color.Black} else {Color.Red})
                Text("Tenants",color = Color(0xFF909090), fontSize = 12.sp, fontFamily = robotoFontFamily)
            }
        }
    }
}