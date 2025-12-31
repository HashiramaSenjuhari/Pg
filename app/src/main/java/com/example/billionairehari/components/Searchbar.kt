package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.icons.FilterIcon


@Composable
fun SearchBar(
    placeholder:String,
    value:String,
    onValueChange:(String) -> Unit,
    trailingIcon:(@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
){
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, tint = Color.Black,contentDescription = "",modifier = Modifier.padding(end = 16.dp))
        },
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(placeholder, fontSize = 16.sp)
        },
        maxLines = 1,
        modifier =  Modifier
            .then(modifier)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 13.dp),
        textStyle = TextStyle(textMotion = TextMotion.Animated, fontSize = 16.sp)
    )
}