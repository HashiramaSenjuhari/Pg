package com.example.billionairehari.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel(assistedFactory = GetRoomTenantCountViewModel.GetRoomTenantCountFactory::class)
class GetRoomTenantCountViewModel @AssistedInject constructor(
    @Assisted val roomId: String
): ViewModel() {

    @AssistedFactory
    interface GetRoomTenantCountFactory {
        fun create(roomId:String): GetRoomTenantCountViewModel
    }
}