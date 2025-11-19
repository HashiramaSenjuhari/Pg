package com.example.billionairehari.modal

import android.security.keystore.KeyNotYetValidException
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.billionairehari.screens.StaticSearchBar
import com.example.billionairehari.viewmodels.PaymentMethod
import com.example.billionairehari.viewmodels.RecordRentViewModel
import com.example.billionairehari.viewmodels.SearchViewModel
import com.example.billionairehari.viewmodels.TenantSearchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordRentPriceModal(
    is_open: MutableState<Boolean>,
    tenant_name:String,
    actual_rent_price:String,
    rent_price:String,
    date:Long,
    onRentPriceChange:(String) -> Unit,
    onDateChange:(Long) -> Unit,
    rent_price_error:String? = null,
    onReset:() -> Unit,
    onSubmit:() -> Unit,
    isLoading:Boolean,
    viewmodel: RecordRentViewModel = viewModel()
){

    val scrollState = rememberScrollState()
    val expanded = remember { mutableStateOf<Boolean>(false) }
    val data = viewmodel.record.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StaticSearchBar(
                onClick = {
                    expanded.value = true
                },
                onClickFilter = {
                    expanded.value = true
                }
            )
            TenantCard(
                name = data.tenant.name
            )
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
                value = data.paying_amount,
                onValueChange = {
                    viewmodel.update_paying_amount(it)
                },
                type = InputType.NUMBER,
                keyBoardType = KeyboardType.Number
            )
            ROw(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                DateInput(
                    label = "Payment Date",
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onDate = {
                        viewmodel.update_payment_date(it)
                    },
                    date = data.date
                )
                PaymentMethod(
                    current_option = data.payment_method,
                    onChangeOption = {
                        viewmodel.update_payment_method(it)
                    }
                )
            }
        }
        Box(
            modifier = Modifier.padding(top = 6.dp)
        ){
            AppButton(
                onClick = {},
                containerColor = Color.Black.copy(0.9f),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add")
            }
        }
    }
    SearchDialog(
        expanded = expanded,
        scrollState = scrollState,
        onSelectTenant = {
            viewmodel.update_tenant(it)
        }
    )
}

data class PaymentMethodData (
    val icon:Int,
    val name:String,
    val type: PaymentMethod
)

val payment_method = listOf<PaymentMethodData>(
    PaymentMethodData(icon = R.drawable.outline_payments_24, name = PaymentMethod.CASH.toString(), type = PaymentMethod.CASH),
    PaymentMethodData(icon = R.drawable.outline_upi_pay_24, name = PaymentMethod.UPI.toString(), type = PaymentMethod.UPI),
)

@Composable
fun PaymentMethod(
    current_option: PaymentMethod,
    onChangeOption:(PaymentMethod) -> Unit
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
    image:String? = null,
    name:String? = "",
    room:String? = null,
    rent:String? = null,
    due_date:String? = null
){
    if(name == null){
        NoTenantPreview()
    }else{
        TenantPreview(
            name = name
        )
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
    name:String
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
                        name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    ROw(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            "Room 112",
                            fontSize = 13.sp,
                            color = Color.Black.copy(0.3f)
                        )
                        Text(
                            " Due on May 01",
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
                    "₹2000",
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
fun SearchDialog(
    expanded: MutableState<Boolean>,
    onSelectTenant: (TenantSearchCard) -> Unit,
    scrollState: ScrollState,
){

    if(expanded.value){
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
            ){
                SearchTenants(
                    expanded = expanded,
                    scrollState = scrollState,
                    onSelectTenant = {
                       onSelectTenant(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchTenants(
    expanded: MutableState<Boolean>,
    scrollState: ScrollState,
    onSelectTenant:(TenantSearchCard) -> Unit,
    viewmodel:SearchViewModel = viewModel()
){
    val search = viewmodel.query.collectAsState()
    val result = viewmodel.result.collectAsState()

    Column(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .fillMaxWidth(0.96f)
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        SearchBar(
            expanded = expanded,
            query = search.value,
            onChangeQuery = {
                viewmodel.update_query(it)
            },
            readOnly = false
        )
        TenantsContainer(
            onSelectTenant = {
                onSelectTenant(it)
            },
            scrollState = scrollState,
            expanded = expanded,
            onResetSearch = {
                viewmodel.reset_query()
            },
            result = result.value
        )
    }
}

@Composable
fun TenantsContainer(
    onResetSearch:() -> Unit,
    onSelectTenant:(TenantSearchCard) -> Unit,
    scrollState: ScrollState,
    result:List<TenantSearchCard>,
    expanded: MutableState<Boolean>
){
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
            )
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
            result.forEach {
                TenantCard(
                    tenant = it,
                    onResetSearch = onResetSearch,
                    expanded = expanded,
                    onSelectTenant = {
                        onSelectTenant(it)
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun TenantCard(
    tenant: TenantSearchCard,
    onResetSearch:() -> Unit,
    onSelectTenant:(TenantSearchCard) -> Unit,
    expanded: MutableState<Boolean>
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    onSelectTenant(tenant)
                    expanded.value = false
                    onResetSearch()
                }
            )
            .background(Color.White)
            .padding(11.dp),
        verticalAlignment = Alignment.CenterVertically,
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
                tenant.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text("Room 121", color = Color.Black.copy(alpha = 0.4f))
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