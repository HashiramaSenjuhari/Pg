package com.example.billionairehari.components.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import com.composables.Close
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomModalLayout(
    name:String = "",
    sheetState: SheetState,
    is_open: MutableState<Boolean>,
    onDismiss:() -> Unit,
    scrimColor:Color = Color.Black.copy(alpha = 0.6f),
    shouldDismissOnBackPress:Boolean = false,
    height:Float = 1f,
    modifier:Modifier = Modifier,
    content:@Composable () -> Unit
){
    val scrollState = rememberScrollState()
    if(is_open.value) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            dragHandle = null,
            sheetState = sheetState,
            modifier = Modifier.wrapContentSize(),
            containerColor = Color.White,
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.SecureOn,
                shouldDismissOnBackPress = shouldDismissOnBackPress
            ),
            scrimColor = scrimColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .drawBehind{
                            val width = size.width
                            val height = size.height
                            drawLine(
                                start = Offset(x = 0f, y = height),
                                end = Offset(x = width,y = height),
                                strokeWidth = 1f,
                                color = Color.Black.copy(0.3f)
                            )
                        }.padding(top = 30.dp, bottom = 16.dp, start = 13.dp, end = 13.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(name,
                        modifier = Modifier.offset(13.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                        )
                    Box(
                        modifier = Modifier.clickable(onClick = onDismiss)
                    ) {
                        Icon(Icons.Default.Close, tint = Color.Black, contentDescription = "")
                    }
                }
                if(name != "Announce"){
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        content()
                    }
                }else{
                    content()
                }
            }
        }
    }
}