package com.example.billionairehari.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.R
import com.example.billionairehari.components.RecentSearchBoard
import com.example.billionairehari.components.SearchInput


@Composable
fun SearchScreenLayout(
    label:String,
    modifier:Modifier,
    isNotEmpty:Boolean,
    query:String,
    onChangeQuery:(String) -> Unit,
    recent_searches:List<String>,
    onClearRecentSearches:() -> Unit,
    onClickBack:() -> Unit,
    ResultScreen:@Composable () -> Unit
){
    /** Focus Requester **/
    val focusRequester = remember { FocusRequester() }

    /** Requestion focus on entering screen **/
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SearchInput(
            label = label,
            query = query,
            onChangeValue = {
                onChangeQuery(it)
            },
            focus = focusRequester,
            onClickBack = onClickBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(query.length <= 2) {
                RecentSearchBoard(
                    onClear = onClearRecentSearches,
                    onPlaceQuery = {
                        onChangeQuery(it)
                    },
                    names = recent_searches
                )
            }
            else if(isNotEmpty){
                ResultScreen.invoke()
            }
            else {
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
        }
    }
}
