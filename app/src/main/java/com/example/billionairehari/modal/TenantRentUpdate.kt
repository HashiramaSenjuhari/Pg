package com.example.billionairehari.modal

import android.security.keystore.KeyNotYetValidException
import android.util.Log
import android.view.RoundedCorner
import android.widget.SearchView
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.billionairehari.R.drawable
import com.example.billionairehari.components.DateInput
import com.example.billionairehari.components.FormButton
import com.example.billionairehari.components.Input
import com.example.billionairehari.components.InputType
import com.example.billionairehari.components.OutlinedInput
import com.example.billionairehari.icons.CashIcon
import com.example.billionairehari.icons.RupeeCircleIcon
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.screens.formatIndianRupee
import kotlin.math.exp
import kotlin.system.exitProcess
import com.example.billionairehari.R
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.CustomDateInput
import com.example.billionairehari.components.CustomDatePick
import com.example.billionairehari.components.DateDialog
import com.example.billionairehari.components.DatePick
import com.example.billionairehari.components.ErrorText
import com.example.billionairehari.components.Label
import com.example.billionairehari.components.dialogs.BottomTenantSearchCard
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.PaymentType
import com.example.billionairehari.icons.SearchIcon
import com.example.billionairehari.layout.BottomDialogSearchScreen
import com.example.billionairehari.model.TenantRentRecord
import com.example.billionairehari.screens.StaticSearchBar
import com.example.billionairehari.utils.MODAL_TYPE
import com.example.billionairehari.utils.currentMonth
import com.example.billionairehari.utils.toDateFormat
import com.example.billionairehari.viewmodels.RecordRentViewModel
import com.example.billionairehari.viewmodels.TenantSearchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordRentPriceModal(
    tenantWithRentCard: TenantDao.TenantWithRoomRentCard? = null,
    amount:String? = null,
    paymentDate:Long? = null,
    paymentType: PaymentType? = PaymentType.CASH,
    current_action: MutableState<MODAL_TYPE>,
    viewmodel: RecordRentViewModel = hiltViewModel()
){
    val query = viewmodel.query.collectAsState()
    val search_results = viewmodel.search_result.collectAsState()

    val scrollState = rememberScrollState()
    val is_search_open = remember { mutableStateOf<Boolean>(false) }
    val selectedTenantCard = remember { mutableStateOf<TenantDao.TenantWithRoomRentCard?>(null) }

    val data = viewmodel.record.value

    LaunchedEffect(Unit) {
        if(tenantWithRentCard != null){
            selectedTenantCard.value = tenantWithRentCard
            viewmodel.update_tenant(tenantWithRentCard)
        }
        else if(data.tenantAndRent != null){
            selectedTenantCard.value = data.tenantAndRent
        }

        if(amount != null) viewmodel.update_paying_amount(amount)
        if(paymentDate != null) viewmodel.update_payment_date(paymentDate)
        if(paymentType != null) viewmodel.update_payment_method(paymentType)
    }


    val interactionSource = remember { MutableInteractionSource() }
    val is_open = remember { mutableStateOf<Boolean>(false) }

    if(interactionSource.collectIsPressedAsState().value){
        is_open.value = true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            TenantCard(
                details = selectedTenantCard.value,
                error = data.tenantError,
                onClickAdd = {
                    is_search_open.value = true
                }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 6.dp)
            ) {
                Column {
                    Text("Payment Amount".toUpperCase(), fontSize = 13.sp, color = Color.Black.copy(0.4f))
                    Spacer(modifier = Modifier.height(13.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .drawBehind{
                                drawLine(
                                    start = Offset(x = 0f, y = size.height),
                                    end = Offset(x = size.width, y = size.height),
                                    color = Color.Black.copy(0.3f),
                                    strokeWidth = 1f
                                )
                            }.padding(vertical = 13.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text("$", fontSize = 24.sp, color = Color.Black.copy(0.6f))
                        BasicTextField(
                            value = viewmodel.record.value.amount,
                            onValueChange = {
                                viewmodel.update_paying_amount(it)
                            },
                            modifier = Modifier.fillMaxWidth()
                                .border(0.dp, color = Color.Transparent),
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                textMotion = TextMotion.Animated
                            ),
                            singleLine = true,
                        )
                    }
                    Spacer(modifier = Modifier.height(11.dp))
                    if(data.amountError != null){
                        ErrorText(error = data.amountError)
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy( 24.dp)
                ) {
                    PaymentDateInput(
                        value = data.dueDate?.toDateFormat("DD MMM YYYY"),
                        is_open = is_open,
                        error = data.dateError
                    )
                    PaymentMethod(
                        current_option = data.paymentType,
                        onChangeOption = {
                            if(!data.isLoading){
                                viewmodel.update_payment_method(it)
                            }
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier.padding(top = 6.dp)
        ){
            AppButton(
                onClick = {
                    viewmodel.submit(current_action = current_action)
                },
                containerColor = Color.Black.copy(0.9f),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth(),
                padding = PaddingValues(vertical = 13.dp)
            ) {
                if(data.isLoading){
                    CircularProgressIndicator(modifier = Modifier.size(20.4.dp), strokeWidth = 2.dp)
                }else {
                    Text("Add payment")
                }
            }
        }
    }
    if(is_search_open.value){
        TenantWithRoomRentSearch(
            query = query.value,
            onChangeQuery = {
                viewmodel.update_query(it)
            },
            onClick = {
                viewmodel.update_tenant(it)
                is_search_open.value = false
                selectedTenantCard.value = it
            },
            is_search_open = is_search_open,
            search_results = search_results.value,
            selected_tenant = selectedTenantCard.value
        )
    }
    if(is_open.value){
        CustomDatePick(
            onDismiss = {
                is_open.value = false
            },
            onDateChange = {
                viewmodel.update_payment_date(it)
            },
            date = selectedTenantCard.value?.dueDay ?: 1
        )
    }
}

@Composable
fun PaymentDateInput(
    value:String? = null,
    is_open: MutableState<Boolean>,
    error:String? = null
){
    val interactionSource = remember { MutableInteractionSource() }

    if(interactionSource.collectIsPressedAsState().value){
        is_open.value = true
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        ROw(
            modifier = Modifier.fillMaxWidth()
                .drawBehind{
                    drawLine(
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width,y = size.height),
                        color = Color.Black.copy(0.4f),
                        strokeWidth = 1f
                    )
                }
        ) {
            Text("Payment date".toUpperCase(), fontSize = 13.sp, color = Color.Black.copy(0.4f))
            Spacer(modifier = Modifier.fillMaxWidth(0.26f))
            TextField(
                readOnly = true,
                value = if(value == null) "" else value,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text("DD/MM/YYYY", fontSize = 16.sp, color = Color.Black.copy(0.3f))
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.End
                ),
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "",modifier = Modifier.size(24.dp).offset(x = 13.dp))
                },
                interactionSource = interactionSource
            )
        }
        if(error != null){
            Text(error, fontSize = 12.sp, color = Color.Red)
        }
    }
}

@Composable
fun TenantWithRoomRentSearch(
    is_search_open: MutableState<Boolean>,
    query:String,
    onChangeQuery:(String) -> Unit,
    onClick:(TenantDao.TenantWithRoomRentCard) -> Unit,
    search_results: List<TenantDao.TenantWithRoomRentCard>,
    selected_tenant: TenantDao.TenantWithRoomRentCard? = null
){
    BottomDialogSearchScreen(
        is_open = is_search_open,
        value = query,
        onChangeValue = {
            onChangeQuery(it)
        },
        search_label = "Search Tenant"
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            search_results.forEach {
                BottomTenantSearchCard(
                    name = it.tenantName,
                    roomName = it.roomName,
                    onClick = {
                        onClick(it)
                    },
                    isSelected = if(selected_tenant != null) selected_tenant == it else false
                )
            }
        }
    }
}

data class PaymentMethodData (
    val icon:Int,
    val name:String,
    val type: PaymentType
)

val payment_method = listOf<PaymentMethodData>(
    PaymentMethodData(icon = R.drawable.outline_payments_24, name = PaymentType.CASH.name, type = PaymentType.CASH),
    PaymentMethodData(icon = R.drawable.outline_upi_pay_24, name = PaymentType.UPI.name, type = PaymentType.UPI),
)

@Composable
fun PaymentMethod(
    current_option: PaymentType,
    onChangeOption:(PaymentType) -> Unit
){
    val expanded = remember { mutableStateOf<Boolean>(false) }
    val dropboxSize = remember { mutableIntStateOf(0) }
    ROw(
        modifier = Modifier.fillMaxWidth()
            .drawBehind{
                drawLine(
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width,y = size.height),
                    color = Color.Black.copy(0.4f),
                    strokeWidth = 1f
                )
            }
            .padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Payment type".toUpperCase(), fontSize = 13.sp,modifier = Modifier.fillMaxWidth(0.50f), color = Color.Black.copy(0.4f))
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Cash" to PaymentType.CASH,"Upi" to PaymentType.UPI).forEach {
                val selected = it.second == current_option
                val bg = animateColorAsState(
                    targetValue = if(selected) Color.Black else Color.White
                )
                val text = animateColorAsState(
                    targetValue = if(selected) Color.White else Color.Black
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(color = Color.Black, width = if(selected) 1.dp else 0.dp, shape = CircleShape)
                        .background(bg.value)
                        .clickable(
                            onClick = {
                                onChangeOption(it.second)
                            }
                        )
                        .padding(horizontal = 24.dp, vertical = 13.dp)
                ){
                    Text(it.first, color = text.value)
                }
            }
        }
    }
}

@Composable
fun TenantCard(
    details: TenantDao.TenantWithRoomRentCard? = null,
    error:String? = null,
    onClickAdd:() -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Surface(
            color = Color.Black.copy(0.01f),
            shape = RoundedCornerShape(16.dp),
            onClick = onClickAdd
        ) {
            ROw(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp, bottom = 24.dp, start = 24.dp, end = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("TENANT SELECTION", fontSize = 11.sp, color = Color.Black.copy(0.6f))
                    Text("Tap to choose", fontSize = 16.sp)
                }
                Icon(
                    Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if(details == null){
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                NoTenantPreview(
                    onClick = onClickAdd
                )
                ErrorText(error = error)
            }
        }else{
            TenantPreview(
                details = details
            )
        }
    }
}

@Composable
fun NoTenantPreview(
    onClick:() -> Unit
){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(0.03f)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    SearchIcon,
                    contentDescription = "",
                    tint = Color.Black.copy(0.6f),
                    modifier = Modifier.clip(CircleShape)
                        .size(60.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No Tenant Selected",
                        color =  Color.Black.copy(0.6f)
                    )
                }
            }
        }
}

@Composable
fun TenantPreview(
    details: TenantDao.TenantWithRoomRentCard
){
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, color = Color.Black.copy(0.1f)),
        modifier = Modifier.shadow(1.dp, shape = RoundedCornerShape(24.dp), spotColor = Color.Black.copy(0.1f))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp,horizontal = 16.dp)
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.Z1yKFPWjHLgvy4O3Hh3-oQHaFj%3Fpid%3DApi&f=1&ipt=c1abd002efe0a166727ee32168bb29f447e6c7a120fd5a7e9014fafc240c0a3f&ipo=images",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                        .size(90.dp)
                        .background(Color.Black)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                details.tenantName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                            ROw(
                                horizontalArrangement = Arrangement.spacedBy(13.dp)
                            ) {
                                Text(
                                    details.roomName,
                                    fontSize = 13.sp,
                                    color = Color.Black.copy(0.3f)
                                )
                                ROw(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(modifier = Modifier.clip(CircleShape).size(6.dp).background(Color.Black.copy(0.6f)))
                                    Text(
                                        "Due on ${details.dueDay}",
                                        fontSize = 13.sp,
                                        color = Color.Black.copy(0.3f),
                                    )
                                }
                            }
                        }
                        ROw(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "₹${details.rentPrice}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    expanded: MutableState<Boolean>,
    query: String,
    onChangeQuery:(String) -> Unit,
    readOnly:Boolean = false
){
    TextField(
        readOnly = readOnly,
        value = query,
        onValueChange = {
            onChangeQuery(it)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text("Search or Select Tenant")
        },
        textStyle = TextStyle.Default.copy(fontSize = 16.sp),
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .border(
                1.dp,
                shape = RoundedCornerShape(24.dp),
                color = Color.Black.copy(0.1f)
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.changes.any { it.pressed }) {
                            expanded.value = true
                        }
                        event.changes.forEach { it.consume() }
                    }
                }
            },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            val size = query.length
            if (size > 1 || expanded.value) {
                IconButton(
                    onClick = {
                        expanded.value = false
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }
            }
        }
    )
}