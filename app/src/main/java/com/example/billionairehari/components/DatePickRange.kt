package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.screens.ROw
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerRange(
    onDismiss:() -> Unit,
    onConfirm:(String?,String?) -> Unit
){
    val datePickerRange = rememberDateRangePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    val start = datePickerRange.selectedStartDateMillis?.let {
        convertMilliToDate(it).toString()
    } ?: null

    val end = datePickerRange.selectedEndDateMillis?.let {
        convertMilliToDate(it).toString()
    } ?: null

    val selectedRange = if(start != null && end != null) "$start - $end" else null
    DatePickerDialog(
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(start,end)
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        }
    ) {
        DateRangePicker(
            state = datePickerRange,
            title = null,
            showModeToggle = true,
            modifier = Modifier.fillMaxWidth()
                .background(Color.White),
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                currentYearContentColor = Color.Blue
            ),
            headline = {
                ROw(
                    modifier = Modifier.fillMaxWidth()
                        .offset(x = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            start ?: "Start Date",
                            fontSize = 16.sp
                        )
                        Text("-")
                        Text(
                            end ?: "End Date",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        )
    }
}

fun convertMilliToDate(milli:Long): String{
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(milli))
}