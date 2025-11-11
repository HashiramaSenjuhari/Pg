package com.example.billionairehari.components

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.billionairehari.components.dashboard.DisableRippleEffect
import com.example.billionairehari.screens.ROw
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class MonthFilterOption(
    val name:String
)

val options = listOf<MonthFilterOption>(
    MonthFilterOption(name = "Last 1 month"),
    MonthFilterOption(name = "Last 3 month"),
    MonthFilterOption(name = "Last 6 month"),
    MonthFilterOption(name = "Last 1 year")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFilterSheet(
    selectedOption:String,
    is_open: MutableState<Boolean>,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss:() -> Unit,
    onConfirm:(String) -> Unit
){
    val selected = remember { mutableStateOf<String>(selectedOption) }
    val isSelected = remember { mutableStateOf<Int>(0) }

    // custom date selection part
    val localDate = LocalDate.now()
    val currentTime = localDate
    val lastTime = localDate.minusMonths(1)

    val startDateInMilli = remember { mutableStateOf<Long>(
            convertLocalToLong(lastTime)
        )
    }
    val endDateInMilli = remember { mutableStateOf<Long>(
            convertLocalToLong(currentTime)
        )
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            is_open.value = false
        },
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Text(
                "Select Time Period to Filter",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                options.forEachIndexed { index,option ->
                    FilterOption(
                        selected = isSelected.value == index,
                        onClick = {
                            if(index !== isSelected.value){
                                isSelected.value = index
                                selected.value = option.name
                            }
                        },
                        option = option.name
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilterOption(
                        option = "Select Date Range",
                        selected = isSelected.value == 5,
                        onClick = {
                            isSelected.value = 5
                        }
                    )
                    if(isSelected.value === 5 && selected.value.contains("-")){
                        CustomDatePicker(
                            is_open = is_open,
                            startDateInMilli = startDateInMilli,
                            endDateInMilli = endDateInMilli
                        )
                    }
                }
            }
            AppButton(
                onClick = {
                    if(isSelected.value <= 4){
                        onConfirm(selected.value)
                    }else {
                        selected.value = "${convertMilliToDate(startDateInMilli.value)} - ${convertMilliToDate(endDateInMilli.value)}"
                        onConfirm(selected.value)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Text("Filter")
            }
        }
    }
}

@Composable
fun FilterOption(
    option:String,
    onClick:() -> Unit,
    selected:Boolean
){
    ROw(
        modifier = Modifier.fillMaxWidth()
            .selectable(
                onClick = onClick,
                selected = selected,
                enabled = true,
                role = Role.RadioButton,
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            option,
            fontWeight = if(selected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 15.sp,
            color = if(selected) Color.Black else Color.Black.copy(0.6f)
        )
        Box(
            modifier = Modifier.size(26.dp)
                .border(1.dp, color = if(selected) Color.Black else Color.Black.copy(0.1f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ){
            if(selected){
                Box(
                    modifier = Modifier.clip(CircleShape)
                        .size(13.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}

fun convertMilliToDate(milli:Long): String{
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(milli))
}

@Composable
fun CustomDatePicker(
    startDateInMilli: MutableState<Long>,
    endDateInMilli: MutableState<Long>,
    is_open: MutableState<Boolean>,
){


    val startDateString = convertMilliToDate(startDateInMilli.value)
    val endDateString = convertMilliToDate(endDateInMilli.value)

    // date dialog state
    val firstDateDialog = remember { mutableStateOf<Boolean>(false) }
    val nextDateDialog = remember { mutableStateOf<Boolean>(false) }

    val startDate = remember { mutableStateOf<String>(startDateString) }
    val endDate = remember { mutableStateOf<String>(endDateString) }

        ROw(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedInput(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    value = startDate.value ?: "",
                    onValueChange = {},
                    readOnly = true,
                    onClick = {
                        firstDateDialog.value = true
                    },
                    label = "Start Date",
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "")
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedInput(
                    modifier = Modifier.fillMaxWidth(),
                    label = "End Date",
                    value = endDate.value ?: "",
                    readOnly = true,
                    onValueChange = {

                    },
                    onClick = {
                        nextDateDialog.value = true
                    },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "")
                    }
                )
            }
        }

    if(firstDateDialog.value){
        DateDialog(
            is_open = firstDateDialog,
            onConfirm = {
                it?.let {
                    startDate.value = convertMilliToDate(it)
                    startDateInMilli.value = it
                }
            },
            selectedDate = startDateInMilli.value
        )
    }
    if(nextDateDialog.value){
        DateDialog(
            is_open = nextDateDialog,
            onConfirm = {
                it?.let {
                    endDate.value = convertMilliToDate(it)
                    startDateInMilli.value = it
                }
            },
            selectedDate = endDateInMilli.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    selectedDate:Long,
    is_open: MutableState<Boolean>,
    onConfirm:(Long?) -> Unit
){
    /* TODO */
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
//        selectableDates = object : SelectableDates {
//            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//                return utcTimeMillis in min..max
//            }
//        },
    )

    DatePickerDialog(
        onDismissRequest = {
            is_open.value = false
        },
        dismissButton = {
            TextButton(
                onClick = {
                    is_open.value = false
                }
            ) {
                Text("Cancel")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false),
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                    is_open.value = false
                }
            ) {
                Text("Confirm")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

fun convertLocalToLong(date: LocalDate): Long {
    val zone = ZoneId.of("Asia/Kolkata")
    val localDate = date.atStartOfDay(zone).toInstant().toEpochMilli()
    return localDate
}