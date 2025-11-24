package com.example.billionairehari.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.Screens
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.screens.RoomCard
import com.example.billionairehari.screens.TenantCard
import com.example.billionairehari.viewmodels.RoomSearchViewModel
import com.example.billionairehari.viewmodels.TenantSearchViewModel

@Composable
fun RoomSearchComponentScreen(
    modifier: Modifier = Modifier,
    current_action: MutableState<MODAL_TYPE>,
    navController: NavController,
    viewmodel: RoomSearchViewModel = viewModel(),
){
    /** Focus Requester **/
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val text = remember { mutableStateOf<String>("") }
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()
    val rooms = results.value
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SearchInput(
            query = query.value,
            onChangeValue = {
                viewmodel.update_query(it)
            },
            focus = focusRequester
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(query.value.length === 0 && rooms.isEmpty()){
                     
            }else if(query.value.length > 1 && rooms.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No results found. Try a different word",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    rooms.forEach {
                            room ->
                        RoomCard(
                            current_action = current_action,
                            room_detail = room,
                            onClick = {
                                navController.navigate("rooms/${room.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchInput(
    query:String,
    onChangeValue:(String) -> Unit,
    focus: FocusRequester
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
            }.padding(horizontal = 6.dp)
            .focusRequester(focusRequester = focus),
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