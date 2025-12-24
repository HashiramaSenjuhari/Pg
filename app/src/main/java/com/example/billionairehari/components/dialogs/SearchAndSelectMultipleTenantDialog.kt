package com.example.billionairehari.components.dialogs

import androidx.annotation.OptIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.modal.Grid
import com.example.billionairehari.modal.SearchBar
import com.example.billionairehari.layout.component.ROw


@Composable
fun SearchAndSelectMultipleTenants(
    expanded: MutableState<Boolean>,
    search: MutableState<String>,
    isTenants:Boolean,
    scrollState: ScrollState,
    tenants:List<String>,
    onDone:(List<String>) -> Unit
){

    val focusRequest = remember { FocusRequester() }
    val selected = remember { mutableStateOf<HashSet<String>>(hashSetOf<String>(), policy = referentialEqualityPolicy()) }

    if(expanded.value) {
        Dialog(
            onDismissRequest = {
                expanded.value = false
            },
            properties = DialogProperties(dismissOnBackPress = true)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.clip(RoundedCornerShape(24.dp))
                        .fillMaxWidth(0.96f)
                        .background(Color.White)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    SearchBar(
                        expanded = expanded,
                        query = "",
                        onChangeQuery = {},
                        readOnly = false
                    )
                    if(isTenants){
                        Column(
                            modifier = Modifier
                                .zIndex(2f)
                                .clip(RoundedCornerShape(24.dp))
                                .fillMaxWidth()
                                .heightIn(min = 60.dp, max = 340.dp)
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    color = Color.Black.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(24.dp)
                                ).focusRequester(focusRequest)
                        ) {
                            Text(
                                "Select the tenant to update rent",
                                fontSize = 12.sp,
                                color = Color.Black.copy(alpha = 0.6f),
                                modifier = Modifier.padding(horizontal = 13.dp, vertical = 6.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState),
                            ) {
                                HorizontalDivider()
                                tenants.forEach {

                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                    else {
                        Column {

                        }
                    }
                    ROw(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        AppButton(
                            shape = RoundedCornerShape(13.dp),
                            border = BorderStroke(1.dp, color = Color.Black.copy(0.1f)),
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text("Close")
                        }
                        AppButton(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            shape = RoundedCornerShape(13.dp),
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BottomTenantSearchCard(
    name:String,
    roomName:String,
    isSelected:Boolean,
    onClick:() -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .background(Color.White)
            .drawBehind{
                drawLine(
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width,y = size.height),
                    strokeWidth = 1f,
                    color = Color.Black.copy(0.6f)
                )
            }
            .padding(11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            AsyncImage(
                model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2Fed%2F7c%2Fb0%2Fed7cb0a40619f5f710325b71e6b411e3.jpg&f=1&nofb=1&ipt=06a432166eb12e9a037390617c2d300e9d154349494e45fe3e2e0e25d0d10592",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(40.dp)
            )
            Column {
                Text(
                    name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(roomName, color = Color.Black.copy(alpha = 0.4f))
            }
        }

        if(isSelected){
            ROw(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(24.dp)
                    .background(if(isSelected) Color(0xFF21c063) else Color.White)
                    .border(1.dp, shape = CircleShape, color = Color.Black.copy(0.1f)),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}