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
    const val TENANT_RENT_SCREEN = "rents"
    const val TICKETS_SCREEN = "tickets"
    const val CONTACTS_SCREEN = "contacts"
    const val PROFILE_SCREEN = "profile"
    const val SETTING_SCREEN = "setting"
    const val ROOM_SEARCH_SEARCH = "room_search"
    const val TENANT_SEARCH_SEARCH = "tenant_search"
}

object Arguments {
    const val ROOM_ID_ARGS = "roomId"
    const val TENANT_ID_ARGS = "tenantId"
    const val SEARCH_ID_ARGS = "searchId"
}

object Destinations {
    const val DASHBOARD_ROUTE = Screens.DASHBOARD_SCREEN
    const val ROOMS_ROUTE = Screens.ROOMS_SCREEN
    const val ROOM_ROUTE = "${Screens.ROOMS_SCREEN}/{${Arguments.ROOM_ID_ARGS}}"

    const val TENANTS_ROUTE  = Screens.TENANTS_SCREEN
    const val TENANT_ROUTE = "${Screens.TENANTS_SCREEN}/{${Arguments.TENANT_ID_ARGS}}"
    const val TENANT_HISTORY = "${Screens.TENANT_RENT_SCREEN}/{${Arguments.TENANT_ID_ARGS}}"
    const val CONTACTS_ROUTE = "${Screens.CONTACTS_SCREEN}"
    const val PROFILE_ROUTE = "${Screens.PROFILE_SCREEN}"
    const val SETTING_ROUTE = "${Screens.SETTING_SCREEN}"

    const val ROOM_SEARCH_ROUTE = "${Screens.ROOM_SEARCH_SEARCH}"
    const val TENANT_SEARCH_ROUTE = "${Screens.TENANT_SEARCH_SEARCH}"
}

class NavigationAction (private val navController: NavHostController) {
    fun navigateToDashboard(){
        navController.navigate(Destinations.DASHBOARD_ROUTE) {
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
    fun navigateToContacts(){
        navController.navigate(Destinations.CONTACTS_ROUTE){
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
}
