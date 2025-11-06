package com.example.billionairehari.components.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.screens.ROw
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RemoveDialog(
    title:String,
    content:String,
    is_open:MutableState<Boolean>,
    onRemove:() -> Unit
) {
    val is_removing = rememberSaveable { mutableStateOf<Boolean>(false) }
    if(is_open.value){
        Dialog(
            onDismissRequest = {
                is_open.value = false
            },
            properties = DialogProperties(dismissOnBackPress = false)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(content,
                            color = Color.Black.copy(
                                alpha = 0.6f
                            ))
                    }
                    ROw(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        AppButton (
                            onClick = {
                                is_open.value = false
                            },
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            border = BorderStroke(width = 1.dp, color = Color.Black.copy(alpha = 0.6f))
                        ) {
                            Text("Cancel")
                        }
                        AppButton (
                            onClick = {
                                CoroutineScope(Dispatchers.Default).launch{
                                    is_removing.value = true
                                    onRemove()
                                    delay(2000L)
                                    is_removing.value = false
                                    is_open.value = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                if(is_removing.value){
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        trackColor = Color.Black.copy(alpha = 0.1f)
                                    )
                                }
                                else {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp),
                                        tint = Color(0xFFED2939)
                                    )
                                    Text("Remove",color = Color(0xFFED2939))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}