package com.example.billionairehari.viewmodels

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.components.sheets.Brodcast
import com.example.billionairehari.components.sheets.SelectedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject



data class AnnounceModel(
    val title:String = "",
    val message:String = "",
    val image:Uri? = null,
    val tenants:List<SelectedType> = emptyList<SelectedType>(),
    val brodcast_type: Brodcast = Brodcast.EVERYONE,
    val title_error:String? = null,
    val message_error:String? = null,
    val image_error:String? = null,
    val submission_error:String? = null,
    val isComplete:Boolean = false
)

data class AnnounceMessage(
    val title:String,
    val message:String,
    val image:Uri? = null
)

fun AnnounceModel.toAnnounce() : AnnounceMessage = AnnounceMessage(
    title = title,
    message = message,
    image = image
)

@HiltViewModel
class AnnounceViewModel @Inject constructor(): ViewModel(){
    private val _announce = MutableStateFlow(AnnounceModel())
    val announce = _announce.asStateFlow()

    private val _announce_trigger = MutableSharedFlow<Intent>()
    val announce_trigger = _announce_trigger.asSharedFlow()


    suspend fun observe_announce(intent: Intent){
        _announce_trigger.emit(intent)
    }

    fun update_title(title:String){
        _announce.update {
            it.copy(
                title = title,
                title_error = null
            )
        }
    }

    fun update_message(body:String){
        _announce.update {
            it.copy(
                message = body,
                message_error = null
            )
        }
    }

    fun update_images(uri:Uri){
        _announce.update {
            it.copy(
                image = uri
            )
        }
    }

    fun update_image_limit(error:String){
        _announce.update {
            it.copy(
                image_error = error
            )
        }
    }

    fun remove_image(){
        _announce.update {
            it.copy(
                image = null
            )
        }
    }

    fun update_select_value(tenant: SelectedType){
        when (tenant) {
           is SelectedType.Tenant -> {
                val updated_tenant = _announce.value.tenants.toMutableList().apply {
                    add(0,tenant)
                }
                _announce.update {
                    it.copy(
                        tenants = updated_tenant
                    )
                }
            }
            is SelectedType.Group -> {
                Log.d("billioniare",tenant.name)
            }
            else -> {}
        }
    }

    fun remove_select_value(index:Int){
        val updated_value = _announce.value.tenants.toMutableList().apply {
            removeAt(index)
        }
        _announce.update {
            it.copy(
                tenants = updated_value
            )
        }
    }

    fun update_brodcast_type(type: Brodcast){
        _announce.update {
            it.copy(
                brodcast_type = type
            )
        }
    }

    fun reset(){
        _announce.value = AnnounceModel()
    }
    fun submit() : Boolean {
        val current = _announce.value
        if(current.title.trim().length <= 0 && current.message.trim().length <= 0 && current.image == null) {
            _announce.update {
                it.copy(
                    submission_error = "Provide Any Field to Announce"
                )
            }
            return false
        }
        if(current.title.trim().length >= 1 && current.message.trim().length <= 0){
            _announce.update {
                it.copy(
                    message_error = "Provide message"
                )
            }
            return false
        }
        return true
    }
}