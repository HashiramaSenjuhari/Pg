package com.example.billionairehari.layout

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.screens.StaticSearchBar
import com.example.billionairehari.viewmodels.FILTER


@Composable
fun DynamicShowcaseScreen(
    scrollState: ScrollState,
    navController: NavController,
    title: String,
    placeholder: String,
    search_route:String,
    onClickFilter:() -> Unit,
    content:@Composable () -> Unit
){

    /** header Animation module - Start **/
    /** header Animation module - End **/


    val maxOffset = 100f
    val progress = (scrollState.value.toFloat() / maxOffset).coerceIn(0f,1f)

    val padding = animateDpAsState(targetValue = lerp(50.dp, 9.dp,progress))
    val spacing = animateDpAsState(targetValue = lerp(13.dp,3.dp,progress))
    val fontSize = animateFloatAsState(targetValue = lerp(24.sp, 21.sp,progress).value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Blue.copy(0.2f),Color.White,Color.White,Color.White,Color.White,Color.White)))
            .padding(top = padding.value)
    ) {
        DynamicTopHeader(
            title = title,
            placeholder = placeholder,
            onClickFilter = onClickFilter,
            onClickSearch = {
                navController.navigate(search_route)
            },
            fontSize = fontSize.value.sp,
            spacing = spacing.value
        )
        content.invoke()
    }
}

@Composable
fun DynamicTopHeader(
    title:String,
    placeholder:String,
    spacing: Dp,
    fontSize: TextUnit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(space = spacing),
        modifier =  Modifier
            .animateContentSize()
            .padding(horizontal = 13.dp, vertical = 13.dp)
    ) {
        ROw(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(title, fontSize = fontSize, fontWeight = FontWeight.Bold)
        }
        StaticSearchBar(
            placeholder = placeholder,
            onClick = onClickSearch,
            onClickFilter = onClickFilter
        )
    }
}