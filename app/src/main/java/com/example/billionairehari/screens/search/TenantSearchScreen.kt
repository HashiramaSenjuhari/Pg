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
import com.example.billionairehari.layout.ChildLayout
import com.example.billionairehari.screens.TenantCard
import com.example.billionairehari.viewmodels.TenantSearchViewModel


@Composable
fun TenantSearchComponentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: TenantSearchViewModel = viewModel(),
){
    /** focus Requester **/
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val text = remember { mutableStateOf<String>("") }
    val query = viewmodel.query.collectAsState()
    val results = viewmodel.result.collectAsStateWithLifecycle()
    val tenants = results.value
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
            if(query.value.length === 0 && tenants.isEmpty()){

            }else if(query.value.length > 1 && tenants.isEmpty()){
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
                    tenants.forEach {
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
        }

    }
}