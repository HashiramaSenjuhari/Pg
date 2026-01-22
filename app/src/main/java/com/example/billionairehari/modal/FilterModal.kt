package com.example.billionairehari.modal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.FilterOption
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.screens.FilterType
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>FilterDialog(
    title:String,
    filter_types: List<FilterType<T>>,
    onReset:() -> Unit,
    onFilter:(T) -> Unit,
    filter_type: MutableState<T>,
    is_open: MutableState<Boolean>
){
    val local_filter_type = remember { mutableStateOf<T>(filter_type.value) }
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        dragHandle = null,
        containerColor = Color.White,
        onDismissRequest = {
            is_open.value = false
        },
        scrimColor = Color.Black.copy(0.1f)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 24.dp,horizontal = 16.dp),
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    filter_types.forEach {
                            (name, filter) ->
                        FilterOption(
                            onClick = {
                                local_filter_type.value = filter
                            },
                            option = name,
                            selected = local_filter_type.value == filter
                        )
                    }
                }
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    AppButton(
                        onClick = onReset,
                        modifier = Modifier.fillMaxWidth(0.5f),
                        border = BorderStroke(1.dp, color = Color.Black.copy(0.3f))
                    ) {
                        Text("Reset")
                    }
                    AppButton(
                        onClick = {
                            onFilter(local_filter_type.value)
                        },
                        modifier = Modifier.fillMaxWidth(1f),
                        containerColor = Color.Black.copy(0.9f),
                        contentColor = Color.White
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}