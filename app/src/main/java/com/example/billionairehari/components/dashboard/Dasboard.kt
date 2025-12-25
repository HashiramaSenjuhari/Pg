package com.example.billionairehari.components.dashboard

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.billionairehari.components.DateFilterSheet
import com.example.billionairehari.components.DateRangeType
import com.example.billionairehari.components.convertLocalToLong
import com.example.billionairehari.components.convertMilliToDate
import com.example.billionairehari.components.mergeDates
import com.example.billionairehari.screens.formatIndianRupee
import com.example.billionairehari.viewmodels.GetTotalRevenueAndInfoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBoard(
    viewModel: GetTotalRevenueAndInfoViewModel = hiltViewModel()
){
    val amount = viewModel.revenue.collectAsState()
    val is_open = rememberSaveable { mutableStateOf(false) }
    val dynamic_height = if(is_open.value) 340.dp else 180.dp

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .animateContentSize()
                .height(dynamic_height),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1B1B1B),
                contentColor = Color.White
            ),
            shape = RevenueBoardShape()
        ){
            Column(
                modifier =  Modifier.fillMaxWidth().heightIn(160.dp)
                    .background(Color(0xFF1B1B1B))
                    .padding(top = 24.dp, start = 24.dp, bottom = 16.dp, end = 16.dp)
                    .zIndex(0f),
            ){
                BriefBoard(
                    is_open = is_open,
                    percentage = "",
                    revenue = amount.value.revenue.toString()
                )
                if(is_open.value){
                    Text("Billionaire")
                }
            }
        }

        val isSheetOpen = rememberSaveable { mutableStateOf(false) }

        /** custom date selection part **/
        val localDate = LocalDate.now()

        val currentTime = localDate.withDayOfMonth(1)
        val lastTime = localDate.withDayOfMonth(localDate.lengthOfMonth())

        val startDateInMilli = remember { mutableStateOf<Long>(
                convertLocalToLong(currentTime)
            )
        }
        val endDateInMilli = remember { mutableStateOf<Long>(
                convertLocalToLong(lastTime)
            )
        }

        val date_range = remember { mutableStateOf<DateRangeType>(DateRangeType.Dynamic(mergeDates(startDateInMilli.value,endDateInMilli.value))) }
        val selectedOptionIndex = remember { mutableStateOf<Int>(5) }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.47f)
                .height(32.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(1.dp, color = Color.Black.copy(alpha = 0.9f), shape = RoundedCornerShape(24.dp))
                .zIndex(1f)
                .clickable(
                    enabled = true,
                    onClick = {
                        isSheetOpen.value = true
                    }
                )
                .padding(horizontal = 6.dp),
            contentAlignment = Alignment.Center
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (val date = date_range.value) {
                    is DateRangeType.Static -> {
                        Text(
                            "Last ${date.date.toString()} month",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B1B1B)
                        )
                    }
                    is DateRangeType.Dynamic -> {
                        Text(
                            date.date,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B1B1B)
                        )
                   }
                }
            }
        }
        /** Filter Sheet **/
        if(isSheetOpen.value){
            DateFilterSheet(
                selectedOption = date_range.value,
                selectedOptionIndex = selectedOptionIndex,
                startDateInMilli = startDateInMilli,
                endDateInMilli = endDateInMilli,
                is_open = isSheetOpen,
                onDismiss = {
                    isSheetOpen.value = false
                },
                onConfirm = {
                        date ->
                    date_range.value = date
                    isSheetOpen.value = false
                    viewModel.update_filter(date_range.value)
                }
            )
        }
    }
}

@Composable
fun BriefBoard(
    revenue:String,
    percentage:String,
    is_open: MutableState<Boolean>
){

    val money = formatIndianRupee(revenue) /** TODO: Add Revenue **/
    val fontSize = when {
        money.length > 9 -> {
            30.sp
        }
        else -> {
            40.sp
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text("REVENUE", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF8F8FF))
        Text("₹${money}",fontSize = fontSize, fontWeight = FontWeight.Black,color = Color(0xFFF8F8FF))
    }
    Spacer(modifier = Modifier.height(13.dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            verticalArrangement =  Arrangement.spacedBy(4.dp)
        ) {
            Text("+ 0.13%",fontSize =13.sp, fontWeight = FontWeight.Black, color = Color.Green)
            Text("From last month",fontSize =12.sp,color = Color(0xFFDCDCDC))
        }
        IconButton(
            onClick = {
                is_open.value = !is_open.value
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFFF8F8FF),
                contentColor = Color(0xFF1B1B1B)
            ),
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "",
                tint =  Color(0xFF1B1B1B),
                modifier = Modifier.size(24.dp).rotate(if(is_open.value)180f else 0f)
            )
        }
    }
}

fun getCurrentDate(localDate: LocalDate):String{
    val end = localDate.withDayOfMonth(LocalDate.now().lengthOfMonth()).dayOfMonth

    val full_month = localDate.month.name.lowercase().replaceFirstChar { it.uppercaseChar() }
    val year = localDate.year
    val month = full_month.slice(if(full_month == "SEPTEMBER") 0..3 else 0..2)

    val format = "$01 $month $year - $end $month $year"
    return format
}