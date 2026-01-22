package com.example.billionairehari.screens.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billionairehari.Screens
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.layout.SearchScreenLayout
import com.example.billionairehari.screens.PaymentCard
import com.example.billionairehari.viewmodels.PaymentSearchViewModel
import com.example.billionairehari.viewmodels.SearchUiState

@Composable
fun PaymentSearchScreen(
    modifier: Modifier,
    navController: NavController,
    viewmodel: PaymentSearchViewModel = hiltViewModel()
){
    val query = viewmodel.query.collectAsState()
    val recent_searches = viewmodel.recent_searches.collectAsState()
    val state = viewmodel.result.collectAsState()

    SearchScreenLayout<PaymentDao.PaymentCard>(
        label = "Search Payments",
        query = query.value.text,
        onChangeQuery = {
            viewmodel.update_query(it)
        },
        state = state.value,
        modifier = Modifier.then(modifier),
        onClickBack = {
            navController.popBackStack()
        },
        loadingState = {},
        dataState = {
            it.forEach {
                payment ->
                PaymentCard(
                    name = payment.tenantName,
                    roomName = payment.roomName,
                    dueDate = payment.dueDate,
                    amount = payment.amount,
                    paymentDate = payment.paymentDate,
                    onClick = {
                        viewmodel.save_recent_search()
                        navController.navigate("${Screens.PAYMENTS_SCREEN}/${payment.id}")
                    }
                )
            }
        },
        defaultState = {
            RecentSearchBoard(
                names = recent_searches.value,
                onPlaceQuery = {
                    viewmodel.update_query(it)
                },
                onClear = {
                    viewmodel.clearRecentSearch()
                }
            )
        }
    )
}