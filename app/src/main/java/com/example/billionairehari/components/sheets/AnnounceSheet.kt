package com.example.billionairehari.components.sheets

import android.Manifest
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaDataSource
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.GestureDetector
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.ChipType
import com.example.billionairehari.components.FormButton
import com.example.billionairehari.components.ImagePreview
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.Label
import com.example.billionairehari.components.SingleImageSelector
import com.example.billionairehari.icons.Mic
import com.example.billionairehari.modal.CustomChip
import com.example.billionairehari.modal.Grid
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.viewmodels.AnnounceMessage
import kotlin.math.log
import kotlin.math.roundToInt
import com.example.billionairehari.R
import com.example.billionairehari.components.dialogs.SearchAndSelectMultipleTenants
import com.example.billionairehari.icons.FilterIcon
import com.example.billionairehari.viewmodels.AudioRecordViewModel
import com.example.billionairehari.viewmodels.State
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.IOException

sealed class SelectedType {
    data class Tenant (
        val name:String,
        val phone:String
    ) : SelectedType()

    data class Group(
        val id:String,
        val name:String
    ) : SelectedType()
}

enum class Brodcast {
    EVERYONE,
    TENANT_WITH_DUES,
    ONLY_UNIQUE
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnounceSheet(
    receivers:List<SelectedType>,
    brodcast_type: Brodcast,
    onSearchAdd:(SelectedType) -> Unit,
    onReceiverRemove:(Int) -> Unit,
    title:String,
    onTitleChanged:(String) -> Unit,
    onChangeBrodcast:(Brodcast) -> Unit,
    message:String,
    onMessageUpdate:(String) -> Unit,
    image:Uri? = null,
    onImageUpdate:(Uri) -> Unit,
    onImageRemove:() -> Unit,
    onImageLimit:()-> Unit,
    image_error:String,
    title_error:String,
    onReset:() -> Unit,
    onSubmit:() -> Unit,
    isLoading:Boolean,
    context: Context
){
    val scrollState = rememberScrollState()
    val tenants = listOf<String>("Billionaire","BillionaireHari","billionaire","great")
    val coroutineScope = rememberCoroutineScope()

    val is_selected = remember { mutableStateOf<Boolean>(false) }
    val is_clicked = remember { mutableStateOf<Boolean>(false) }

    val expanded = remember { mutableStateOf<Boolean>(false) }
    val search = remember { mutableStateOf<String>("") }

    val filtered = tenants.filter { it.contains(search.value) }
    val isTenants = filtered.size > 0
    Column(
        modifier = Modifier.fillMaxHeight()
            .padding(16.dp),
    ) {
        com.example.billionairehari.modal.SearchBar(
            query = "",
            onChangeQuery = {},
            expanded = expanded,
            readOnly = true
        )
        Body(
            value = message,
            image = image,
            context = context,
            onValueChange = { onMessageUpdate(it) },
            onImageAdd = { onImageUpdate(it) },
            onImageError = { onImageLimit() },
            onRemoveImage = { onImageRemove() },
            error = image_error,
            onClick = {
                is_clicked.value = true
            }
        )
    }
    SearchAndSelectMultipleTenants(
        expanded = expanded,
        onDone = {},
        isTenants = isTenants,
        scrollState = scrollState,
        tenants = filtered,
        search = search
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Body(
    onImageAdd:(Uri) -> Unit,
    value:String,
    onValueChange:(String) -> Unit,
    error:String? = null,
    context:Context,
    onImageError:(String) -> Unit,
    image:Uri? = null,
    onRemoveImage:() -> Unit,
    onClick: () -> Unit
){
    val is_pressed = remember { mutableStateOf(false) }
    val is_paused = remember { mutableStateOf(false) }
    val is_playing = remember { mutableStateOf(false) }
    val size = animateDpAsState(targetValue = if(is_pressed.value) 60.dp else 50.dp)
    val input_value = remember { mutableStateOf("") }
    val mic_permission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val is_message = input_value.value.length > 0
    val context = LocalContext.current
    val applicationContext = context.applicationContext as Application
    val mediaRecorder = AudioRecordViewModel()
    val state = mediaRecorder.state.collectAsState()
    val audio_error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if(mic_permission.status is PermissionStatus.Denied){
            mic_permission.launchPermissionRequest()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if(!is_pressed.value){
            InputBody(
                onClick = {
                    if(is_message){
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT,input_value.value)
                            setPackage("com.whatsapp")
                            putExtra("jid","${"8668072363"}@s.whatsapp.net")
                        }
                        context.startActivity(intent)
                    }else {
                    }
                },
                onClickInput = onClick,
                is_pressed = is_pressed,
                input_value = input_value,
                is_message = is_message
            )
        }
            if(is_pressed.value && mic_permission.status is PermissionStatus.Granted){
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    ROw(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.06f))
                            .padding(
                                vertical = if(!is_paused.value) 13.dp else 1.dp,
                                horizontal = if(!is_paused.value) 13.dp else 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(!is_paused.value){
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Mic, contentDescription = "")
                                Text("0:00", fontSize = 16.sp)
                            }
                        }
                        else {
                            ROw {
                                IconButton(
                                    onClick = {
                                        is_playing.value = !is_playing.value
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            if(is_playing.value) com.example.billionairehari.R.drawable.baseline_play_arrow_24
                                            else com.example.billionairehari.R.drawable.baseline_pause_24
                                        ),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                    ROw (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                painter = painterResource(com.example.billionairehari.R.drawable.outline_delete_24),
                                contentDescription = "",
                                tint = Color.Black.copy(alpha = 0.6f)
                            )
                        }
                        IconButton(
                            onClick = {
//                                if(state.value == State.PLAYING || state.value == State.RESUME){
//                                    mediaRecorder.pause()
//                                }else if(state.value == State.PAUSE) {
//                                    mediaRecorder.resume()
//                                }
                                is_paused.value = !is_paused.value
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                painter = painterResource(
                                    if(is_paused.value) com.example.billionairehari.R.drawable.outline_mic_24
                                    else com.example.billionairehari.R.drawable.outline_pause_24
                                ),
                                contentDescription = "",
                                modifier = Modifier.size(60.dp),
                                tint = Color.Red
                            )
                        }
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Black
                            ),
                            onClick = {
//                                if(state.value == State.PLAYING || state.value == State.RESUME){
//                                    mediaRecorder.stop()
//                                }
                                if(!is_pressed.value && !is_message){
                                    is_pressed.value = true
                                }else if(is_pressed.value && !is_message) {
                                    is_pressed.value = false
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(com.example.billionairehari.R.drawable.outline_send_24),
                                contentDescription = "button",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun InputBody(
    onClick:() -> Unit,
    onClickInput:() -> Unit,
    input_value: MutableState<String>,
    is_pressed: MutableState<Boolean>,
    is_message:Boolean
){
    ROw(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = input_value.value,
            onValueChange = {
                input_value.value = it
            },
            placeholder = {
                Text("Message", color = Color(0xFF909090))
            },
            modifier = Modifier
                .fillMaxWidth(0.84f)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    1.dp,
                    color = Color.Black.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ),
            enabled = true,
            minLines = 1,
            maxLines = 9,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF909090)
            )
        )
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Black
            ),
            onClick = onClick,

        ) {
            Icon(
                painter = painterResource(
                    if(!is_pressed.value && !is_message) com.example.billionairehari.R.drawable.outline_mic_24
                    else com.example.billionairehari.R.drawable.outline_send_24
                ),
                contentDescription = "button",
                tint = Color.White
            )
        }
    }
}