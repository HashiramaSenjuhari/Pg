package com.example.billionairehari.components

import android.icu.text.SimpleDateFormat
import android.webkit.WebSettings
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.icons.CalendarIcon
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePick(
    onDateChange: (Long?) -> Unit,
    onDismiss:() -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    DatePickerDialog(
        confirmButton = {
            TextButton(
                onClick = {
                    onDateChange(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ){
                Text("Cancel")
            }
        },
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize()
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun DateInput(
    label:String,
    onDate:(Long) -> Unit,
    date:Long,
    error:String? = null,
    modifier:Modifier = Modifier.border(1.dp,color = Color(0xFFBEBEBE),shape = RoundedCornerShape(13.dp)),
    fontSize: TextUnit = 12.sp
    ) {
    val interactionSource = remember { MutableInteractionSource() }
    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateFormats = if (date != 0L) dateFormat.format(Date(date)) else "DD/MM/YYYY"

    if(interactionSource.collectIsPressedAsState().value){
        is_open.value = true
    }
    Column {
        OutlinedInput(
            modifier = Modifier.then(modifier),
            value = dateFormats,
            onValueChange = {},
            interactionSource = interactionSource,
            label = label,
            label_font_size = 12.sp,
            trailingIcon = {
                IconButton(
                    onClick = {
                        is_open.value = true
                    }
                ) {
                    Icon(
                        imageVector = CalendarIcon,
                        contentDescription = "calender",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            readOnly = true
        )
        if(error != null){
            Text(error!!, color = Color.Red,fontSize = 12.sp,modifier = Modifier.padding(top = 6.dp))
        }
    }
    if(is_open.value) {
        DatePick(
            onDateChange = {
                current_date ->
                    onDate(current_date!!)
            },
            onDismiss = {
                is_open.value = false
            }
        )
    }
}