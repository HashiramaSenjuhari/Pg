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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.SearchBar
import com.example.billionairehari.components.SearchInput


@Composable
fun BottomDialogSearchScreen(
    value:String,
    onChangeValue:(String) -> Unit,
    search_label:String,
    is_open: MutableState<Boolean>,
    content:@Composable () -> Unit
){
    val focusRequester = remember { FocusRequester() }
    if(is_open.value){
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
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
                    SearchBar(
                        value = value,
                        onValueChange = {
                            onChangeValue(it)
                        },
                        trailingIcon = {

                        },
                        placeholder = "Search Room",
                        modifier = Modifier.focusRequester(focusRequester = focusRequester)
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
}