package com.example.billionairehari.screens

import android.text.Layout
import android.widget.GridLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.billionairehari.Destinations
import com.example.billionairehari.layout.DynamicShowcaseScreen
import com.example.billionairehari.layout.component.ROw
import com.example.billionairehari.R
import com.example.billionairehari.Screens
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.core.data.local.entity.PaymentStatus
import com.example.billionairehari.core.data.local.entity.PaymentType
import com.example.billionairehari.layout.ModalUi
import com.example.billionairehari.viewmodels.GetAllPaymentHistoryViewModel

enum class PaymentFilterType {
    MONTHS,
    PAYMENTS_TYPES
}

@Composable
fun PaymentHistory(
    modifier:Modifier = Modifier,
    navController: NavController,
    viewmodel: GetAllPaymentHistoryViewModel = hiltViewModel()
){
    val scrollState = rememberScrollState()
    val payments = viewmodel.history.collectAsState()

    val is_open = remember { mutableStateOf<Boolean>(false) }

    DynamicShowcaseScreen(
        title = "Payments History",
        navController = navController,
        placeholder = "Search Payment",
        onClickFilter = {
            is_open.value = true
        },
        search_route = Destinations.PAYMENT_SEARCH_ROUTE,
        scrollState = scrollState
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(bottom = 240.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            payments.value.forEach {
                PaymentCard(
                    name = it.tenantName,
                    roomName = it.roomName,
                    amount = it.amount,
                    onClick = {
                        navController.navigate("${Destinations.PAYMENTS_ROUTE}/${it.id}")
                    },
                    paymentDate = it.paymentDate,
                    dueDate = it.dueDate
                )
            }
        }
    }

    val filter_type = remember { mutableStateOf<PaymentFilterType>(PaymentFilterType.MONTHS) }
    val monthsSelected = remember { mutableStateListOf<String>() }
    val paymentTypesSelected = remember { mutableStateListOf<PaymentType>() }

    if(is_open.value){
        PaymentFilterModal(
            months = listOf("Jan 2026","Feb 2026","Mar 2026","Apr 2026","May 2026","Jun 2026"),
            is_open = is_open,
            filter_type = filter_type,
            monthsSelected = monthsSelected,
            paymentTypesSelected = paymentTypesSelected,
            onApplyChange = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentFilterModal(
    is_open: MutableState<Boolean>,
    months:List<String>,
    filter_type: MutableState<PaymentFilterType>,
    monthsSelected: SnapshotStateList<String>,
    paymentTypesSelected: SnapshotStateList<PaymentType>,
    onApplyChange:() -> Unit
) {

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = {
            is_open.value = false
        },
        dragHandle = null,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FilterHeader(
                isClearAllowed = monthsSelected.size > 0 || paymentTypesSelected.size > 0,
                onClickClear = {
                    monthsSelected.clear()
                    paymentTypesSelected.clear()
                }
            )
            ROw(
                modifier = Modifier.fillMaxHeight(0.90f)
            ) {
                FilterOption(
                    selected = filter_type.value,
                    onSelect = {
                            filterType -> filter_type.value = filterType
                    },
                    paymentTypeSelectedSize = paymentTypesSelected.size,
                    monthsSelectedSize = monthsSelected.size
                )
                FilterLayout(
                    filter_type = filter_type.value,
                    paymentTypes = paymentTypesSelected,
                    monthsSelected = monthsSelected,
                    months = months
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Black.copy(0.1f))
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ){
                AppButton(
                    onClick = onApplyChange,
                    containerColor = Color.Black,
                    modifier = Modifier.fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f),
                    shape = RoundedCornerShape(13.dp),
                    contentColor = Color.White
                ) {
                    Text("Apply")
                }
            }
        }
    }
}

@Composable
fun FilterCheckBox(
    name:String,
    checked:Boolean,
    onCheckedChange:(Boolean) -> Unit
){
    ROw(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, fontSize = 15.sp)
        Checkbox(
            enabled = true,
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
    }
}

@Composable
fun FilterLayout(
    filter_type: PaymentFilterType,
    paymentTypes: SnapshotStateList<PaymentType>,
    monthsSelected: SnapshotStateList<String>,
    months:List<String>
){

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(1f)
    ) {
        when(filter_type){
            PaymentFilterType.MONTHS -> MonthFilters(
                months = months,
                monthsSelected = monthsSelected.toList(),
                onFilterClick = {
                    if(monthsSelected.contains(it)){
                        monthsSelected.remove(it)
                    }else {
                        monthsSelected.add(it)
                    }
                }
            )
            PaymentFilterType.PAYMENTS_TYPES -> PaymentStatusFilterOptions(
                types = paymentTypes.toList(),
                onFilterClicked = {
                    if(paymentTypes.contains(it)){
                        paymentTypes.remove(it)
                    }else {
                        paymentTypes.add(it)
                    }
                }
            )
        }
    }
}

@Composable
fun MonthFilters(
    months:List<String>,
    monthsSelected:List<String>,
    onFilterClick:(String) -> Unit
){
    Column {
        months.forEach {
            month ->
            FilterCheckBox(
                name = month,
                checked = monthsSelected.contains(month),
                onCheckedChange = {
                    onFilterClick(month)
                }
            )
        }
    }
}

@Composable
fun PaymentStatusFilterOptions(
    types:List<PaymentType>,
    onFilterClicked:(PaymentType) -> Unit
){
    listOf("UPI" to PaymentType.UPI,"Cash" to PaymentType.CASH).forEach {
        paymentType ->
        FilterCheckBox(
            name = paymentType.first,
            checked = types.contains(paymentType.second),
            onCheckedChange = {
                onFilterClicked(paymentType.second)
            }
        )
    }
}

@Composable
fun FilterOption(
    selected: PaymentFilterType,
    onSelect:(PaymentFilterType) -> Unit,
    monthsSelectedSize:Int,
    paymentTypeSelectedSize:Int
){
    Column(
        modifier = Modifier.fillMaxHeight()
            .fillMaxWidth(0.4f)
            .drawBehind{
                drawLine(
                    start = Offset(x = size.width, y = 0f),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 1f,
                    color = Color.Black.copy(0.3f)
                )
            }
    ) {
        listOf("Months" to PaymentFilterType.MONTHS,"Payment types" to PaymentFilterType.PAYMENTS_TYPES).forEach {
            val selected = it.second == selected
            val size = if(it.second == PaymentFilterType.MONTHS) monthsSelectedSize else paymentTypeSelectedSize
            Surface(
                onClick = {
                    onSelect(it.second)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .background(if(selected)Color.Black.copy(0.1f) else Color.Transparent)
                        .drawBehind{
                            drawLine(
                                start = Offset(x = 0f, y = 1f),
                                end = Offset(x = 1f, y = 1f),
                                color = Color.Black.copy(0.3f)
                            )
                        }
                        .padding(horizontal = 13.dp, vertical = 19.dp)
                ){
                    ROw(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(it.first, fontWeight = if(selected) FontWeight.SemiBold else FontWeight.Normal, fontSize = 15.sp)
                        if(size > 0){
                            Text("${size}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterHeader(
    isClearAllowed:Boolean,
    onClickClear:() -> Unit
){
    ROw(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind{
                drawLine(
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 1f,
                    color = Color.Black.copy(0.3f)
                )
            }
            .padding(top = 24.dp, start = 19.dp, end = 19.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "")
            Text("Filters", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        Surface(
            onClick = onClickClear,
            enabled = isClearAllowed,
            color = Color.Transparent
        ) {
            Text("Clear all",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black.copy(0.6f),
                modifier = Modifier.padding(13.dp)
            )
        }
    }
}

@Composable
fun PaymentCard(
    name:String,
    roomName:String,
    amount:Int,
    paymentDate:String,
    dueDate:String,
    onClick:() -> Unit
){
    Surface(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .drawBehind{
                drawLine(
                    start = Offset(x = size.width / 6, y = size.height),
                    end = Offset(x = size.width,y = size.height),
                    color = Color.Black.copy(0.3f),
                    strokeWidth = 1f
                )
            },
        color = Color.White,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(13.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = "",
                            tint = Color.Black.copy(0.2f),
                            modifier = Modifier.rotate(135f)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(name, fontSize = 16.sp)
                        ROw(
                            horizontalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            Text(roomName, color = Color.Black.copy(0.6f))
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text("₹${amount}", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                Text(paymentDate, fontSize = 13.sp, color = Color.Black.copy(0.6f))
            }
        }
    }
}