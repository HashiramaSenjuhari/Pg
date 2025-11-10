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
import java.util.Date
import java.util.Locale

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
    is_open: MutableState<Boolean>,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss:() -> Unit,
    onConfirm:(String?,String?) -> Unit
){
    val isSelected = remember { mutableStateOf<Int>(0) }
    val is_custom_date = remember { mutableStateOf<Boolean>(false) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            is_open.value = false
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Select Time Period to Filter", fontWeight = FontWeight.Bold)
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                options.forEachIndexed { index,option ->
                    FilterOption(
                        selected = isSelected.value == index,
                        onClick = {
                            is_custom_date.value = false
                            if(index !== isSelected.value){
                                isSelected.value = index
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
                            is_custom_date.value = true
                        }
                    )
                    if(is_custom_date.value){
                        CustomDatePicker(is_open = is_open)
                    }
                }
            }
            AppButton(
                onClick = {},
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
    is_open: MutableState<Boolean>,
){
    // date dialog state
    val firstDateDialog = remember { mutableStateOf<Boolean>(false) }
    val nextDateDialog = remember { mutableStateOf<Boolean>(false) }

    // date value state
    val calendar = Calendar.getInstance()
    val startRange = remember { mutableStateOf<Long>(
        Calendar.getInstance().apply {
            set(2025, Calendar.SEPTEMBER,24)
        }.timeInMillis)
    }
    val endRange = remember { mutableStateOf<Long>(
        calendar.timeInMillis
    )
    }

    val selectedFirstRange = remember { mutableStateOf<String?>(null) }
    val selectedSecondRange = remember { mutableStateOf<String?>(null) }

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
                    value = selectedFirstRange.value ?: "",
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
                    value = selectedSecondRange.value ?: "",
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
            min = startRange.value,
            max = endRange.value,
            is_open = firstDateDialog,
            onConfirm = {
                it?.let {
                    startRange.value = it
                    selectedFirstRange.value = convertMilliToDate(it)
                }
            }
        )
    }
    if(nextDateDialog.value){
        DateDialog(
            min = startRange.value,
            max = endRange.value,
            is_open = nextDateDialog,
            onConfirm = {
                it?.let {
                    endRange.value = it
                    selectedSecondRange.value = convertMilliToDate(it)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    min:Long,
    max: Long,
    is_open: MutableState<Boolean>,
    onConfirm:(Long?) -> Unit
){
    Log.d(
        "MinDate",convertMilliToDate(min)
    )
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in min..max
            }
        }
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