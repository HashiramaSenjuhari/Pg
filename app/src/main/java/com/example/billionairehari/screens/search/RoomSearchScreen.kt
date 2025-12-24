package com.example.billionairehari.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.layout.MODAL_TYPE
import com.example.billionairehari.screens.RoomCard
import com.example.billionairehari.viewmodels.RoomSearchViewModel
import com.example.billionairehari.R
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.layout.SearchScreenLayout
import com.example.billionairehari.viewmodels.SearchUiState

@Composable
fun RoomSearchComponentScreen(
    modifier: Modifier = Modifier,
    current_action: MutableState<MODAL_TYPE>,
    navController: NavController,
    viewmodel: RoomSearchViewModel = hiltViewModel()
){

    /** viewmodel **/
    val recent_searches = viewmodel.recent_searches.collectAsState()
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.results.collectAsStateWithLifecycle()
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
                SearchUiState.Default -> {
                    RecentSearchBoard(
                        onClear = {
                            viewmodel.clear_room_recent_search()
                        },
                        onPlaceQuery = {
                            viewmodel.update_query(it)
                        },
                        names = recent_searches.value
                    )
                }
                is SearchUiState.Data<RoomDao.RoomCard> -> {
                    val size = state.data.size
                    if(size == 0){
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
                        state.data.forEach {
                                room ->
                            RoomCard(
                                current_action = current_action,
                                room_detail = room,
                                onClick = {
                                    Log.d("SEARCHQUERY",query.value.text)
                                    viewmodel.save_recent_search()
//                                    navController.navigate("rooms/${room.id}")
                                }
                            )
                        }
                    }
                }
                SearchUiState.Loading -> {
                }
            }
        }
    }
}


