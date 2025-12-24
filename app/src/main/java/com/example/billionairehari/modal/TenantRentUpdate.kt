package com.example.billionairehari.modal

import android.security.keystore.KeyNotYetValidException
import android.util.Log
import android.view.RoundedCorner
import android.widget.SearchView
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.billionairehari.components.ErrorText
import com.example.billionairehari.components.Label
import com.example.billionairehari.components.dialogs.BottomTenantSearchCard
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.PaymentType
import com.example.billionairehari.layout.BottomDialogSearchScreen
import com.example.billionairehari.model.TenantRentRecord
import com.example.billionairehari.screens.StaticSearchBar
import com.example.billionairehari.utils.currentMonth
import com.example.billionairehari.viewmodels.RecordRentViewModel
import com.example.billionairehari.viewmodels.TenantSearchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordRentPriceModal(
    is_open: MutableState<Boolean>,
    tenantWithRentCard: TenantDao.TenantWithRoomRentCard? = null,
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
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StaticSearchBar(
                placeholder = "Search Tenant",
                onClick = {
                    is_search_open.value = true
                },
                filter = false,
                onClickFilter = {
                }
            )
            TenantCard(
                details = selectedTenantCard.value,
                error = data.tenantError,
                onClick = {
                    is_search_open.value = true
                }
            )
            Log.d("BILLIONAREGREATHARI",data.amountError ?: "")
            Input(
                modifier = Modifier.fillMaxWidth(),
                label = "Paying Now",
                inner_label = "Enter Amount",
                leadingIcon = {
                    Icon(imageVector = RupeeCircleIcon, contentDescription = "")
                },
                trailingIcon = {
                    Text("")
                },
                value = data.amount,
                onValueChange = {
                    viewmodel.update_paying_amount(it)
                },
                type = InputType.NUMBER,
                keyBoardType = KeyboardType.Number,
                error = data.amountError,
                readOnly = data.isLoading
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ){
                    DateInput(
                        label = "Payment Date",
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onDate = {
                            if(!data.isLoading){
                                viewmodel.update_payment_date(it)
                            }
                        },
                        date = data.paymentDate
                    )
                    if(data.dateError != null){
                        Text(data.dateError, fontSize = 12.sp, color = Color.Red)
                    }
                }
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
        Box(
            modifier = Modifier.padding(top = 6.dp)
        ){
            AppButton(
                onClick = {
                    viewmodel.submit(is_open = is_open)
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
                    Text("Add")
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
    Box{
        OutlinedInput(
            readOnly = true,
            label = "Payment Method",
            value = current_option.name,
            onValueChange = {},
            onClick = {
                expanded.value = true
            },
            modifier = Modifier.onGloballyPositioned{
                layoutCoordinates ->
                dropboxSize.value = layoutCoordinates.size.width
            }
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
            modifier = Modifier.width(with(LocalDensity.current){
                dropboxSize.value.toDp()
            }),
            containerColor = Color.White,
            shadowElevation = 1.dp,
            tonalElevation = 0.dp,
            shape = RoundedCornerShape(13.dp),
            border = BorderStroke(1.dp, color = Color.Black.copy(0.1f))
        ) {
            payment_method.forEach {
                payment_method ->
                DropdownMenuItem(
                    modifier = Modifier.background(if(current_option.name == payment_method.name) Color.Black.copy(0.06f) else Color.White),
                    onClick = {
                        expanded.value = false
                        onChangeOption(payment_method.type)
                    },
                    text = {
                        ROw(
                            horizontalArrangement = Arrangement.spacedBy(13.dp)
                        ) {
                            Icon(
                                painter = painterResource(payment_method.icon),
                                contentDescription = ""
                            )
                            Text(payment_method.name)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TenantCard(
    details: TenantDao.TenantWithRoomRentCard?= null,
    error:String? = null,
    onClick:() -> Unit
){
    Box(
        modifier = Modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = MutableInteractionSource()
        )
    ){
        if(details == null){
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                NoTenantPreview()
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
fun NoTenantPreview(){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(0.06f)
        )
    ) {
        ROw(
            modifier = Modifier.padding(13.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "",
                tint = Color.Black.copy(0.6f),
                modifier = Modifier.clip(CircleShape)
                    .size(30.dp)
            )
            Text(
                "No Tenant Selected. Pick a tenant to see rent details",
                color =  Color.Black.copy(0.6f)
            )
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
        ROw(
            modifier = Modifier.fillMaxWidth()
                .padding(13.dp)
        ) {
            ROw(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.Z1yKFPWjHLgvy4O3Hh3-oQHaFj%3Fpid%3DApi&f=1&ipt=c1abd002efe0a166727ee32168bb29f447e6c7a120fd5a7e9014fafc240c0a3f&ipo=images",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                        .size(40.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        details.tenantName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            details.roomName,
                            fontSize = 13.sp,
                            color = Color.Black.copy(0.3f)
                        )
                        Text(
                            " Due on ${currentMonth()} ${details.dueDay}",
                            fontSize = 13.sp,
                            color = Color.Black.copy(0.3f),
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    "₹${details.rentPrice}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Current rent",
                    fontSize = 12.sp,
                    color = Color.Black.copy(0.4f)
                )
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