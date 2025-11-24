package com.example.billionairehari.layout


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.layout.component.ROw

@Composable
fun ChildLayout(
    label:String,
    modifier: Modifier,
    scrollState: ScrollState = rememberScrollState(),
    contentModifier:Modifier = Modifier,
    verticalArrangement: Dp = 0.dp,
    content:@Composable () -> Unit
){
    Column(
        modifier = Modifier.then(modifier)
            .fillMaxSize()
            .background(Color.White)
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
            Text(label, fontSize = 16.sp)
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .then(contentModifier)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(verticalArrangement)
        ) {
            content.invoke()
        }
    }
}