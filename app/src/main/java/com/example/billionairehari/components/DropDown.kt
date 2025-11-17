package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.components.sheets.DropDownItem
import com.example.billionairehari.components.sheets.fakeItems
import kotlin.collections.forEach

@Composable
fun DropDown(
    modifier:Modifier = Modifier,
    onChangeValue:(String) -> Unit,
    value:String,
    items: List<DropDownItem>,
    is_important:Boolean,
    label:String,
    error:String? = null,
    size: Float = 100f
) {
    val interactionSource = remember { MutableInteractionSource() }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val parentSize = remember { mutableStateOf(IntSize.Zero) }
    if(interactionSource.collectIsPressedAsState().value){
        expanded.value = true
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(size)
        ) {
            Box(
                modifier = Modifier.clickable{
                    expanded.value = true
                }.background(Color.White)
            ){
                OutlinedInput(
                    value = value,
                    onValueChange = {},
                    modifier = Modifier.then(modifier).onGloballyPositioned{
                            layoutCoordinates ->
                        parentSize.value = layoutCoordinates.size
                    },
                    interactionSource = interactionSource,
                    label = "Choose Room",
                    label_font_size = 12.sp,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            modifier = Modifier.rotate(
                                if(expanded.value) {
                                    180f
                                }else{
                                    0f
                                }
                            )
                        )
                    }
                )

            }
            DropdownMenu(
                shape = RoundedCornerShape(13.dp),
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                modifier = Modifier.width(with(LocalDensity.current){
                    parentSize.value.width.toDp()
                }).background(Color.White)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier.background(if (value == item.name) Color(0xFFF0F0F0) else Color.Transparent),
                        onClick = {
                            onChangeValue(item.name)
                            expanded.value = false
                        },
                        text = {
                            Text(item.name)
                        }
                    )
                }
            }
        }
        if(error != null){
            Text(error!!, fontSize = 12.sp, color = Color.Red)
        }
    }

}
