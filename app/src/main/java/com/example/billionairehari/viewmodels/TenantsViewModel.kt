package com.example.billionairehari.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.model.Tenant
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class AddTenantViewModel @Inject constructor(
//    private val db: Repository,
): ViewModel() {
    val tenants =  "" // get from repo
    init {
//        refresh from remote
    }
    fun create(tenant: Tenant){
        viewModelScope.launch {

        }
    }
    fun update(tenant: Tenant){
        viewModelScope.launch {

        }
    }
    fun delete(id:String){
        viewModelScope.launch {

        }
    }
}