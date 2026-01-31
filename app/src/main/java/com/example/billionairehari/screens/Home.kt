package com.example.billionairehari.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.Query
import com.example.billionairehari.Screens
import com.example.billionairehari.components.dashboard.ActionBar
import com.example.billionairehari.components.dashboard.DashboardBoard
import com.example.billionairehari.components.dashboard.Details
import com.example.billionairehari.components.dashboard.TopBar
import com.example.billionairehari.utils.MODAL_TYPE
import kotlinx.coroutines.CoroutineScope


@Composable
fun HomeScreen(
    navActions: NavigationAction,
    navController: NavController,
    coroutine: CoroutineScope,
    current_action: MutableState<MODAL_TYPE>,
    is_open: MutableState<Boolean>,
    modifier:Modifier = Modifier
){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .padding(horizontal = 13.dp, vertical = 12.dp)
    ) {
        TopBar(
            name = "Billionaire's Pg",
            navActions = navActions
        )
        DashboardBoard(
            navController = navController
        )
        ActionBar(
            navAction = navActions,
            is_open = is_open,
            current_action = current_action
        )
        Details(
            onClickPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.PAID}")
            },
            onClickNotPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.NOT_PAID}")
            },
            onClickPartialPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.PARTIAL_PAID}")
            }
        )
    }
}


fun formatIndianRupee(number:Int) :String {
    val rupee = number.toString()
    val length = rupee.length
    if(length <= 3) return rupee

    val currency = StringBuilder()
    var count = 0
    for(i in length - 1 downTo 0){
        currency.append(rupee[i])
        count ++
        if(count == 3 || (count > 3 && (count - 3) % 2 == 0) && i != 0){
            currency.append(",")
        }
    }
    return currency.reverse().toString()
}