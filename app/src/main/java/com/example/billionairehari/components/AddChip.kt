package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChipType (
    val name:String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddChip(
    chips: List<ChipType>? = null,
    onAddChip:() -> Unit,
    onRemoveChip: (Int) -> Unit,
    label:String,
    error:String? = null
){
    Column (
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            if(error != null){
                Text(error.toString(), color =  Color.Red,fontSize = 12.sp)
            }
        }
        FlowRow (
            verticalArrangement = Arrangement.spacedBy(13.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            modifier = Modifier.offset((-6).dp)
        ) {
            if(chips != null){
                chips.forEachIndexed {
                        index,chip ->

                }
            }
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(60.dp))
                    .height(36.dp)
                    .background(Color.Black),
                    onClick = onAddChip
            ) {
                Icon(Icons.Outlined.Add, tint = Color.White,contentDescription = "Add")
            }
        }

    }
}

@Preview
@Composable
fun TestingAddChip(){
    AddChip(
        chips = listOf(ChipType(name = "")),
        onAddChip = {},
        onRemoveChip = {},
        error = "",
        label = "Features"
    )
}