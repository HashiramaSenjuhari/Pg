package com.example.billionairehari.components.sheets

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.components.ChipType
import com.example.billionairehari.components.ChooseMultiplePhoto
import com.example.billionairehari.components.FormButton
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.InputType
import com.example.billionairehari.components.Label
import com.example.billionairehari.components.OutlinedInput
import com.example.billionairehari.icons.RoomIcon
import com.example.billionairehari.icons.Rupee
import com.example.billionairehari.modal.CustomChip
import com.example.billionairehari.modal.Grid
import com.example.billionairehari.layout.component.ROw
import kotlinx.coroutines.selects.select

@Composable
fun AddRoomSheet(
    images:List<ByteArray>,
    room_name:String,
    bed_count:String,
    rent:String,
    deposit:String,
    features:List<String>,
    images_error:String? = null,
    room_name_error:String? = null,
    beds_error:String? = null,
    rent_error:String? = null,
    deposit_error:String? = null,
    features_error:String? = null,
    onImageAdd:(ByteArray) -> Unit,
    onImageRemove:(Int) -> Unit,
    onImageError:() -> Unit,
    onRoomNameChange:(String) -> Unit,
    onNoOfBedChange:(String) -> Unit,
    onRentChange:(String) -> Unit,
    onDepositChange:(String) -> Unit,
    onFeatureAdd:(String) -> Unit,
    onFeatureRemove: (String) -> Unit,
    onReset:() -> Unit,
    onSubmit:() -> Unit,
    isLoading:Boolean,
    scrollState: ScrollState
){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(state = scrollState)
            .padding(top = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ChooseMultiplePhoto(
                images = images,
                modifier = Modifier.clip(CircleShape),
                contentPadding = PaddingValues(horizontal = 13.dp),
                onImageChange = { onImageAdd(it) },
                onImageRemove = { onImageRemove(it) },
                onError = { onImageError() },
                size = 90.dp
            )
            if(images_error != null){
                Text(images_error.toString(), fontSize = 13.sp, color =  Color.Red)
            }
            OutlinedInput(
                value = room_name,
                onValueChange = { value ->
                    onRoomNameChange(value) },
                label = "Room Name",
                error = room_name_error,
                leadingIcon = RoomIcon,
                modifier = Modifier.fillMaxWidth()
            )
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Input(
                    value = bed_count,
                    onValueChange = {
                        value ->
                        onNoOfBedChange(value)
                    },
                    label = "No. Of Beds",
                    error =beds_error,
                    type = InputType.NUMBER,
                    keyBoardType = KeyboardType.Number,
                    maxLength = 2,
                    inner_label = "Eg. 4",
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                Input(
                    value = rent.toString(),
                    onValueChange = {
                        onRentChange(it)
                    },
                    label = "Rent",
                    error = rent_error,
                    keyBoardType = KeyboardType.Number,
                    type = InputType.NUMBER,
                    inner_label = "Eg. 2000",
                    modifier =  Modifier.fillMaxWidth(),
                    maxLength = 9
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Input(
                    value = deposit.toString(),
                    onValueChange = {
                        onDepositChange(it)
                    },
                    keyBoardType = KeyboardType.Number,
                    type = InputType.NUMBER,
                    label = "Deposit",
                    error = deposit_error,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    maxLength = 9,
                    inner_label = "Eg. 2000"
                )
            }
            HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))
            Aminities(
                features = features,
                onFeatureAdd = {
                    onFeatureAdd(it)
                },
                onFeatureRemove = {
                    onFeatureRemove(it)
                },
                features_error = features_error
            )
        }
        Box(
            modifier = Modifier.padding(top = 60.dp)
        ){
            FormButton(
                isLoading = isLoading,
                onReset = onReset,
                onSubmit = onSubmit
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Aminities(
    features:List<String>,
    onFeatureAdd:(String) -> Unit,
    onFeatureRemove: (String) -> Unit,
    features_error:String? = null
){
    val aminities = listOf<String>("Wi-fi",
        "Air Conditioning",
        "TV",
        "Parking",
        "Kitchen",
        "Laundry",
        "Balcony"
    )
    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Amenities", fontSize = 16.sp,fontWeight = FontWeight.SemiBold)
                    Text("(Optional)", color = Color.Black.copy(alpha = 0.6f))
                }
                Text(
                    "Select the amenities you offer",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.6f)
                    )
            }
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.offset(x = (-6).dp)
            ) {
                aminities.forEach { aminitie ->
                    val is_selected = features.contains(aminitie)
                    Card(
                        onClick = {
                            if(features.contains(aminitie)){
                                onFeatureRemove(aminitie)
                            }else {
                                onFeatureAdd(aminitie)
                            }
                        },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = if(is_selected) Color.Black else Color.White,
                            contentColor = if(is_selected) Color.White else Color.Black
                        ),
                        border = BorderStroke(1.dp, color = Color.Black.copy(0.1f))
                    ) {
                        Text(
                            aminitie,modifier = Modifier.padding(13.dp))
                    }
                }
            }
        }
    }
}