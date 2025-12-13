package com.example.billionairehari.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.billionairehari.components.Input


@Composable
fun BottomDialogSearchScreen(
    value:String,
    onChangeValue:(String) -> Unit,
    search_label:String,
    is_open: MutableState<Boolean>,
    content:@Composable () -> Unit
){
    Dialog(
        onDismissRequest = {
            is_open.value = false
        },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(13.dp)
            ) {
                Input(
                    value = value,
                    onValueChange = {
                        onChangeValue(it)
                    },
                    inner_label = search_label,
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "")
                    },
                    trailingIcon = {
                        if(value.length > 3) {
                            Icon(Icons.Default.Close, contentDescription = "")
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    content.invoke()
                }
            }
        }
    }
}