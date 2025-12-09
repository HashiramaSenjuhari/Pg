package com.example.billionairehari.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.R
import com.example.billionairehari.Screens
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.layout.ChildLayout
import com.example.billionairehari.layout.SearchScreenLayout
import com.example.billionairehari.screens.RoomCard
import com.example.billionairehari.screens.TenantCard
import com.example.billionairehari.screens.TenantData
import com.example.billionairehari.viewmodels.SearchUiState
import com.example.billionairehari.viewmodels.TenantSearchViewModel


@Composable
fun TenantSearchComponentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: TenantSearchViewModel = hiltViewModel(),
){
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()
    val state = results.value
    val scrollState = rememberScrollState()


    SearchScreenLayout(
        modifier = Modifier.then(modifier),
        label = "Search Tenants",
        query = query.value.text,
        onChangeQuery = {
            viewmodel.update_query(it)
        },
        onClickBack = {
            navController.popBackStack()
        }
    ) {
        when(state){
            is SearchUiState.Data<TenantData> -> {
                val size = state.data.size
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
                    state.data.forEach {
                        TenantCard(
                            tenant = it,
                            onClick = {
                                navController.navigate("${Screens.TENANTS_SCREEN}/${it}")
                            },
                            onCLickMessage = {},
                            onClickCall = {}
                        )
                    }
                }
            }
            is SearchUiState.Default -> {
                RecentSearchBoard(
                    onClear = {},
                    onPlaceQuery = {
                        viewmodel.update_query(it)
                    },
                    names = state.recent_searches
                )
            }
            SearchUiState.Loading -> {}
        }
    }
}