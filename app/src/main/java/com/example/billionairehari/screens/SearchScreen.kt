package com.example.billionairehari.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.billionairehari.viewmodels.SearchViewModel

@Composable
fun SearchComponentScreen(
    id:String,
    modifier: Modifier = Modifier,
    viewmodel: SearchViewModel = viewModel()
){
    val text = remember { mutableStateOf<String>("") }
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .then(modifier)
    ) {
        SearchInput(
            query = query.value,
            onChangeValue = {
                viewmodel.update_query(it)
            }
        )
        Column(
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            results.value.forEach {
                Text(it.name)
            }
        }

    }
}

@Composable
fun SearchInput(
    query:String,
    onChangeValue:(String) -> Unit
){
    TextField(
        value = query,
        onValueChange = {
            onChangeValue(it)
        },
        placeholder = {
            Text("Search Rooms")
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
            }.padding(horizontal = 6.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        interactionSource = MutableInteractionSource(),
        leadingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
        }
    )
}