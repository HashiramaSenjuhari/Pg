package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.billionairehari.core.data.repository.TenantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    private val repository: TenantRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _id:String? = savedStateHandle["tenantId"]
    val id = _id
}