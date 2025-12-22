package com.example.billionairehari.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.RecentSearch
import com.example.billionairehari.core.data.repository.RecentSearchRepository
import com.example.billionairehari.core.data.repository.RecentSearchType
import com.example.billionairehari.core.data.repository.TenantRepository
import com.example.billionairehari.screens.TenantData
import com.example.billionairehari.utils.TENANTS
import com.example.billionairehari.utils.currentDateTime
import com.example.billionairehari.utils.generateUUID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TenantSearchCard(
    val name:String = "",
    val room:String = "",
    val due:Long = 0L,
    val rent_amount:String = ""
)

fun TenantDao.TenantCardDetails.toTenantData() : TenantData = TenantData(
    id = id,
    room_name = roomName,
    paymentStatus = paymentStatus,
    image = "",
    name = name,
    phone = phone_number,
    is_noticed = false
)


@OptIn(FlowPreview::class)
@HiltViewModel
class TenantSearchViewModel @Inject constructor (
    private val repository: RecentSearchRepository,
    private val tenant: TenantRepository,
    private val savedState: SavedStateHandle
): ViewModel() {
    private val tenants = tenant.getTenantsCardFlow(ownerId = "1")
    var recent_searches = repository.getRecentSearches(ownerId = "1", type = TENANTS)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<SearchUiState<TenantDao.TenantCardDetails>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .combine(tenants)
        { query,tenants ->
            if(query.text.trim().length <= 2)
                SearchUiState.Default
            else SearchUiState.Data<TenantDao.TenantCardDetails>(tenants.filter { it.name.contains(query.text, ignoreCase = true) })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Loading
        )

    fun update_query(query:String) {
        _query.value = TextFieldValue(query)
    }

    fun reset_query(){
        _query.value = TextFieldValue("")
    }

    fun save_recent_search(){
        viewModelScope.launch {
            val id = generateUUID()
            val currentDateTime = currentDateTime()
            val recents = RecentSearch(
                ownerId = "1",
                id = id,
                createdAt = currentDateTime,
                text = query.value.text,
                searchType = TENANTS
            )
            val count = repository.totalRecentSearches(ownerId = "1", type = TENANTS)
            if(count >= 5){
                repository.clearOldSearch(ownerId = "1", type = TENANTS)
            }
            repository.insertRecentSearch(recent_search = recents)
        }
    }

    fun clearRecentSearch(){
        viewModelScope.launch {
            repository.clearRecentSearches(ownerId = "1", type = TENANTS)
        }
    }
}