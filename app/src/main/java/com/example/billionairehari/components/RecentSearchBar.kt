package com.example.billionairehari.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchInput(
    label:String,
    query:String,
    onChangeValue:(String) -> Unit,
    focus: FocusRequester,
    onClickBack:() -> Unit
){
    TextField(
        value = query,
        onValueChange = {
            onChangeValue(it)
        },
        placeholder = {
            Text(label)
        },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind{
                drawLine(
                    start = Offset(x = 0f,y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    color = Color.Black,
                    strokeWidth = 1f
                )
            }.padding(horizontal = 6.dp)
            .focusRequester(focusRequester = focus),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            selectionColors = TextSelectionColors(
                handleColor = Color.Black,
                backgroundColor = Color.Black.copy(0.1f)
            )
        ),
        singleLine = true,
        interactionSource = MutableInteractionSource(),
        leadingIcon = {
            IconButton(
                onClick = onClickBack
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        trailingIcon = {
            if(query.length > 0){
                IconButton(
                    onClick = {
                        onChangeValue("")
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }
            }
        }
    )
}