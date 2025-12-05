package com.example.billionairehari.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.billionairehari.layout.component.ROw


@Composable
fun RecentSearchBoard(
    names:List<String>,
    onPlaceQuery:(String) -> Unit,
    onClear:() -> Unit
) {
    if(names.size > 0){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ROw(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text("RECENT SEARCHES", color = Color.Black.copy(0.4f))
                AppButton(
                    padding = PaddingValues(horizontal = 13.dp),
                    onClick = onClear
                ) {
                    Text("Clear All")
                }
            }
            names.forEach{
                SearchCard(
                    name = it,
                    onClickTenant = {
                        onPlaceQuery(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchCard(
    name:String,
    onClickTenant:() -> Unit
){
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onClickTenant
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(19.dp)
        ) {
            Icon(Icons.Outlined.Search, contentDescription = "")
            Text(name)
        }
    }
}