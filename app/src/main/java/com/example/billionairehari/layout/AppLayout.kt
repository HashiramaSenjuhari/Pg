package com.example.billionairehari.layout

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.billionairehari.Arguments
import com.example.billionairehari.Destinations
import com.example.billionairehari.NavigationAction
import com.example.billionairehari.Screens
import com.example.billionairehari.components.contacts.ContactScreen
import com.example.billionairehari.components.sheets.SelectedType
import com.example.billionairehari.screens.AuthScreen
import com.example.billionairehari.screens.DashboardScreen
import com.example.billionairehari.screens.RoomScreen
import com.example.billionairehari.screens.search.RoomSearchComponentScreen
import com.example.billionairehari.screens.RoomsScreen
import com.example.billionairehari.screens.TenantScreen
import com.example.billionairehari.screens.search.TenantSearchComponentScreen
import com.example.billionairehari.screens.TenantsScreen
import com.example.billionairehari.viewmodels.RoomsViewModel
import okhttp3.internal.wait

@Composable
fun AppLayout(
    padding: PaddingValues,
    navController: NavHostController,
    startDestination:String,
    is_open: MutableState<Boolean>,
    is_dialog_open: MutableState<Boolean>,
    current_action: MutableState<MODAL_TYPE>,
    current_dialog_action: MutableState<DIALOG_TYPE>,
    navActions: NavigationAction = remember(navController) {
        NavigationAction(navController = navController)
    }
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition =  {
            ExitTransition.None
        }
    ){
        composable(
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            route = Destinations.DASHBOARD_ROUTE
        ) {
            DashboardScreen(
                navController = navActions,
                coroutine = coroutine,
                modifier = Modifier.padding(padding),
                is_open = is_open,
                current_action = current_action
            )
        }
        composable(
            route = Destinations.ROOMS_ROUTE,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            RoomsScreen(
                viewmodel = hiltViewModel<RoomsViewModel>(),
                navController = navController,
                current_action = current_action,
                current_dialog_action = current_dialog_action
            )
        }
        composable(
            route = Destinations.TENANTS_ROUTE,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }
        ){
            TenantsScreen(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
        composable(
            route = Destinations.TENANT_ROUTE,
            arguments = listOf(
                navArgument(name = Arguments.TENANT_ID_ARGS) {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }
                ) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }
                ) + fadeOut(animationSpec = tween(durationMillis = 300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth }
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }
                ) + fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ){
            entry ->
            val tenantId = entry.arguments?.getString(Arguments.TENANT_ID_ARGS)!!
            Log.d("Billionaire",tenantId)
            TenantScreen(
                id = tenantId,
                modifier = Modifier.padding(padding).fillMaxSize(),
                context = context,
                onNavigateToHistory = {
                    navController.navigate("${Screens.TENANT_RENT_SCREEN}/1")
                }
            )
        }
        composable(
            route = Destinations.ROOM_ROUTE,
            arguments = listOf(
                navArgument(name = Arguments.ROOM_ID_ARGS){
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }
                ) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }
                )  + fadeOut()
            }
        ) {
            entry ->
            val roomId = entry.arguments?.getString(Arguments.ROOM_ID_ARGS) ?: "Unknown"
            RoomScreen(
                id = roomId,
                modifier = Modifier.padding(padding),
                onBackNavigation = {
                    val route = navController.popBackStack()
                    if(route) {
                        navController.popBackStack()
                    }else {
                        navController.navigate(Destinations.ROOMS_ROUTE)
                    }
                },
                onTenantNavigate = {},
                onRoomEdit = {
                    is_open.value = true
                },
                onRoomMessage = {
                    is_open.value = true
                    current_action.value = MODAL_TYPE.ANNOUNCE(reveivers = listOf(SelectedType.Tenant(name = it, phone = it)))
                },
                onRoomShare = {
                    val photos = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        setPackage("com.whatsapp")
                    }
                    context.startActivity(photos)
                },
                onRoomDelete = {
                    is_dialog_open.value = true
                    current_dialog_action.value = DIALOG_TYPE.DELETE_ROOM(id = it)
                },

                onTenant = {
                    navController.navigate("${Screens.TENANTS_SCREEN}/${it}")
                },
                onTenantNotice = {
                },
                onTenantMessage = {
                    is_open.value = true
                    current_action.value = MODAL_TYPE.ANNOUNCE(reveivers = listOf(SelectedType.Tenant(name = it, phone = it)))
                },
                onTenantRentUpdate = {
                    is_open.value = true
                    current_action.value = MODAL_TYPE.UPDATE_TENANT_RENT()
                },
                onTenantShare = {},
                current_action = current_action
            )
        }
        composable(
            route = Destinations.TENANT_HISTORY,
            arguments = listOf(
                navArgument(name = Arguments.TENANT_ID_ARGS){
                    type = NavType.StringType
                }
            )
        ) {
            id ->
            val tenant_id = id?.arguments?.getString(Arguments.TENANT_ID_ARGS)
            Column {
                Text(tenant_id!!)
            }
        }
        composable(
            route = Destinations.ROOM_SEARCH_ROUTE,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight }
                )
            }
        ) {
            RoomSearchComponentScreen(
                modifier = Modifier.padding(padding),
                navController = navController,
                current_action = current_action
            )
        }
        composable(
            route = Destinations.TENANT_SEARCH_ROUTE,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight }
                )
            }
        ){
            TenantSearchComponentScreen(
                modifier = Modifier.padding(padding),
                navController = navController,
            )
        }
        composable(
            route = Destinations.AUTH_ROUTE
        ){
            AuthScreen(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}