package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun SearchDropDown(
    modifier: Modifier = Modifier
){
    val query = remember { mutableStateOf<String>("") }
    val parent = remember { mutableStateOf(IntSize.Zero) }
    val expanded = remember { mutableStateOf<Boolean>(false) }
    val interaction = remember { MutableInteractionSource() }
    if(interaction.collectIsPressedAsState().value){
        expanded.value = true
    }
    Box{
        OutlinedInput(
            value = query.value,
            onValueChange = {
                query.value = it
            },
            interactionSource = interaction,
            trailingIcon = {},
            modifier = Modifier.then(modifier)
                .onGloballyPositioned{
                layoutCoordinates ->
                parent.value = layoutCoordinates.size
            },
            label = "Select Rooms"
        )
        DropdownMenu(
            modifier = Modifier.width(parent.value.width.dp),
            onDismissRequest = {
                expanded.value = false
            },
            expanded = expanded.value
        ) {
            DropdownMenuItem(
                text = {
                    Text("BIllionaire")
                },
                onClick = {
                    expanded.value = false
                }
            )
        }
    }
}

@Preview(name = "SearchDropDown", backgroundColor = 0)
@Composable
fun BackgroundDropDown(){
    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        SearchDropDown()
    }
}