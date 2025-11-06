package com.example.billionairehari.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.components.ChipType

data class Feature(
//    val id:String,
    val name:String
)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Grid(space: Dp = 6.dp,content:@Composable () -> Unit){
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalArrangement = Arrangement.spacedBy(space)
    ) {
        content()
    }
}

@Composable
fun CustomChip(
    fontSize: TextUnit = 12.sp,
    feature:ChipType,
    onRemove:() -> Unit,
    modifier:Modifier = Modifier
){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(13.dp))
            .then(modifier)
            .background(Color(0xFFf3f3f5))
            .padding(horizontal = 13.dp, vertical = 9.dp)
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(feature.name, fontSize = fontSize)
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(13.dp)
        ) {
            Icon(Icons.Default.Close,feature.name)
        }
    }
}