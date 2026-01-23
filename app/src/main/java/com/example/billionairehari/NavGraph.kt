package com.example.billionairehari

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.navigation.NavHostController

object Screens {
    const val DASHBOARD_SCREEN = "dashboard"
    const val ROOMS_SCREEN = "rooms"
    const val TENANTS_SCREEN = "tenants"
    const val PAYMENTS_SCREEN = "payments_history"
    const val TENANT_PAYMENTS_SCREEN = "tenant_payments"
    const val TENANT_RENT_SCREEN = "rents"
    const val PROFILE_SCREEN = "profile"
    const val SETTING_SCREEN = "setting"
    const val ROOM_SEARCH_SCREEN = "room_search"
    const val TENANT_SEARCH_SCREEN = "tenant_search"
    const val PAYMENT_SEARCH_SCREEN = "payment_search"
    const val AUTH_SCREEN = "auth"
    const val PAID_SCREEN = "paids"
    const val NOT_PAID_SCREEN = "not_paids"
    const val PARTIAL_PAID_SCREEN = "partial_paids"
}

object Arguments {
    const val ROOM_ID_ARGS = "room_id"
    const val TENANT_ID_ARGS = "tenant_id"
    const val SEARCH_ID_ARGS = "search_id"
    const val PAYMENT_ID_ARGS = "payment_id"
    const val TENANT_FILTER_TYPE = "type"
}

object Query {
    const val FILTER_QUERY = "filter"
}

object Destinations {
    const val DASHBOARD_ROUTE = Screens.DASHBOARD_SCREEN
    const val ROOMS_ROUTE = Screens.ROOMS_SCREEN
    const val ROOM_ROUTE = "${Screens.ROOMS_SCREEN}/{${Arguments.ROOM_ID_ARGS}}"
    const val PAYMENTS_ROUTE = "${Screens.PAYMENTS_SCREEN}"
    const val PAYMENT_ROUTE = "${Screens.PAYMENTS_SCREEN}/{${Arguments.PAYMENT_ID_ARGS}}"

    const val TENANTS_ROUTE  = "${Screens.TENANTS_SCREEN}?${Query.FILTER_QUERY}={${Arguments.TENANT_FILTER_TYPE}}"
    const val TENANT_ROUTE = "${Screens.TENANTS_SCREEN}/{${Arguments.TENANT_ID_ARGS}}"
    const val TENANT_RENT_HISTORY = "${Screens.TENANT_RENT_SCREEN}/{${Arguments.TENANT_ID_ARGS}}"
    const val PROFILE_ROUTE = "${Screens.PROFILE_SCREEN}"
    const val SETTING_ROUTE = "${Screens.SETTING_SCREEN}"
    const val PAID_ROUTE = "${Screens.PAID_SCREEN}"
    const val NOT_PAID_ROUTE = "${Screens.NOT_PAID_SCREEN}"
    const val PARTIAL_PAID_ROUTE = "${Screens.PARTIAL_PAID_SCREEN}"

    const val ROOM_SEARCH_ROUTE = "${Screens.ROOM_SEARCH_SCREEN}"
    const val TENANT_SEARCH_ROUTE = "${Screens.TENANT_SEARCH_SCREEN}"
    const val PAYMENT_SEARCH_ROUTE = "${Screens.PAYMENT_SEARCH_SCREEN}"
    const val AUTH_ROUTE = "${Screens.AUTH_SCREEN}"
    const val TENANT_PAYMENT_HISTORY_ROUTE = "${Screens.TENANT_PAYMENTS_SCREEN}/{${Arguments.TENANT_ID_ARGS}}"
}

class NavigationAction (private val navController: NavHostController) {
    fun navigateToDashboard(){
        navController.navigate(Destinations.DASHBOARD_ROUTE)
        {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToRooms(){
        navController.navigate(Destinations.ROOMS_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToTenants(){
        navController.navigate(Destinations.TENANTS_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToPaymentHistory(){
        navController.navigate(Destinations.PAYMENTS_ROUTE){
            popUpTo(navController.graph.startDestinationId){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun naviagateToProfile(){
        navController.navigate(Destinations.PROFILE_ROUTE){
            popUpTo(navController.graph.startDestinationId){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToSetting(){
        navController.navigate(Destinations.SETTING_ROUTE){
            popUpTo(navController.graph.startDestinationId){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToProfile(){
        navController.navigate(Destinations.PROFILE_ROUTE){
            popUpTo(navController.graph.startDestinationId){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
//    fun navigateToPaidPage(){
//        navController.navigate(Destinations.PAID_ROUTE){
//            popUpTo(navController.graph.startDestinationId){
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
//    fun navigateToNotPaidPage(){
//        navController.navigate(Destinations.NOT_PAID_ROUTE){
//            popUpTo(navController.graph.startDestinationId){
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
//    fun navigateToPartialPaidPage(){
//        navController.navigate(Destinations.PARTIAL_PAID_ROUTE){
//            popUpTo(navController.graph.startDestinationId){
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
}
