package com.example.billionairehari.screens

import android.R
import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.credentials.CredentialManager
import androidx.navigation.NavAction
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.billionairehari.Arguments
import com.example.billionairehari.Destinations
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.Query
import com.example.billionairehari.Screens
import com.example.billionairehari.components.AppButton
import com.example.billionairehari.components.dashboard.ActionBar
import com.example.billionairehari.components.dashboard.DashboardBoard
import com.example.billionairehari.components.dashboard.Details
import com.example.billionairehari.components.dashboard.TopBar
import com.example.billionairehari.components.sheets.AddRoomSheet
import com.example.billionairehari.components.sheets.AnnounceSheet
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.icons.ContactIcon
import com.example.billionairehari.icons.RemaindIcon
import com.example.billionairehari.utils.MODAL_TYPE
import com.example.billionairehari.viewmodels.AnnounceModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


@Composable
fun DashboardScreen(
    navActions: NavigationAction,
    navController: NavController,
    coroutine: CoroutineScope,
    current_action: MutableState<MODAL_TYPE>,
    is_open: MutableState<Boolean>,
    modifier:Modifier = Modifier
){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .padding(horizontal = 13.dp, vertical = 12.dp)
    ) {
        TopBar(
            name = "Billionaire's Pg",
            navActions = navActions
        )
        DashboardBoard()
        ActionBar(
            navAction = navActions,
            is_open = is_open,
            current_action = current_action
        )
        Details(
            onClickPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.PAID}")
            },
            onClickNotPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.NOT_PAID}")
            },
            onClickPartialPaid = {
                navController.navigate("${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}=${TENANT_FILTERS.PARTIAL_PAID}")
            }
        )
    }
}


fun formatIndianRupee(number:Int) :String {
    val rupee = number.toString()
    val length = rupee.length
    if(length <= 3) return rupee

    val currency = StringBuilder()
    var count = 0
    for(i in length - 1 downTo 0){
        currency.append(rupee[i])
        count ++
        if(count == 3 || (count > 3 && (count - 3) % 2 == 0) && i != 0){
            currency.append(",")
        }
    }
    return currency.reverse().toString()
}