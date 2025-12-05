package com.example.billionairehari.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.Screens
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.screens.RoomCard
import com.example.billionairehari.screens.TenantCard
import com.example.billionairehari.viewmodels.RoomSearchViewModel
import com.example.billionairehari.viewmodels.TenantSearchViewModel
import com.example.billionairehari.R
import com.example.billionairehari.layout.SearchScreenLayout
import com.example.billionairehari.viewmodels.SearchUiState

@Composable
fun RoomSearchComponentScreen(
    modifier: Modifier = Modifier,
    current_action: MutableState<MODAL_TYPE>,
    navController: NavController,
    viewmodel: RoomSearchViewModel = viewModel(),
){


    /** mutable state **/
    val text = remember { mutableStateOf<String>("") }

    /** viewmodel **/
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()
    val state = results.value
    val scrollState = rememberScrollState()

    SearchScreenLayout(
        label = "Search Rooms",
        modifier = Modifier.then(modifier),
        query = query.value.text,
        onChangeQuery = {
            viewmodel.update_query(it)
        },
        onClickBack = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            when(state){
                is SearchUiState.Default -> {
                    RecentSearchBoard(
                        onClear = {},
                        onPlaceQuery = {
                            viewmodel.update_query(it)
                        },
                        names = listOf("Room 101","Room 102")
                    )
                }
                is SearchUiState.Rooms -> {
                    val size = state.rooms.size
                    if(size === 0){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = "")
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(13.dp)
                                ) {
                                    Text("No results found", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    Text("Check the spelling or try different word", color = Color.Black.copy(0.6f))
                                }
                            }
                        }
                    }
                    else {
                        state.rooms.forEach {
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
                is SearchUiState.Loading -> {
                }
            }
        }
    }
}


