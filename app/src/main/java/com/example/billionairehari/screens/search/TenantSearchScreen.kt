package com.example.billionairehari.screens.search

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.Screens
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.layout.ChildLayout
import com.example.billionairehari.layout.SearchScreenLayout
import com.example.billionairehari.screens.TenantCard
import com.example.billionairehari.viewmodels.TenantSearchViewModel


@Composable
fun TenantSearchComponentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: TenantSearchViewModel = viewModel(),
){
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()
    val tenants = results.value
    val scrollState = rememberScrollState()


//    SearchScreenLayout(
//        modifier = Modifier.then(modifier),
//        label = "Search Tenants",
//        query = query.value,
//        onChangeQuery = {
//            viewmodel.update_query(it)
//        },
//        isNotEmpty = tenants.isNotEmpty(),
//        onClearRecentSearches = {},
//        recent_searches = listOf("BillionaireHari","BillionaireHari","BillionaireHari"),
//        onClickBack = {
//            navController.popBackStack()
//        }
//    ) {
//        tenants.forEach {
//            TenantCard(
//                tenant = it,
//                onClick = {
//                    navController.navigate("${Screens.TENANTS_SCREEN}/${it}")
//                },
//                onCLickMessage = {},
//                onClickCall = {}
//            )
//        }
//    }
}