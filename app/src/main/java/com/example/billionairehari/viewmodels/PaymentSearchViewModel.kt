package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.RecentSearch
import com.example.billionairehari.core.data.repository.PaymentRepository
import com.example.billionairehari.core.data.repository.RecentSearchRepository
import com.example.billionairehari.core.data.repository.RecentSearchType
import com.example.billionairehari.utils.SearchTypes
import com.example.billionairehari.utils.currentDateTime
import com.example.billionairehari.utils.generateUUID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentSearchViewModel @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    private val payments = paymentRepository.getPaymentsHistory(ownerId = "1", paymentDates = emptyList(), paymentTypes = emptyList())
    val recent_searches = recentSearchRepository.getRecentSearches(ownerId = "1", type = SearchTypes.PAYMENTS)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val result: StateFlow<SearchUiState<PaymentDao.PaymentCard>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .combine(payments)
        { query,payments ->
            if(query.text.trim().length <= 2)
                SearchUiState.Default
            else SearchUiState.Data<PaymentDao.PaymentCard>(payments.filter { it.tenantName.contains(query.text, ignoreCase = true) })
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
                searchType = SearchTypes.PAYMENTS
            )
            val count = recentSearchRepository.totalRecentSearches(ownerId = "1", type = SearchTypes.PAYMENTS)
            if(count >= 5){
                  recentSearchRepository.clearOldSearch(ownerId = "1", type = SearchTypes.PAYMENTS)
            }
            recentSearchRepository.insertRecentSearch(recent_search = recents)
        }
    }

    fun clearRecentSearch(){
        viewModelScope.launch {
            recentSearchRepository.clearRecentSearches(ownerId = "1", type = SearchTypes.PAYMENTS)
        }
    }
}