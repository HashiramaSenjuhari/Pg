package com.example.billionairehari.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.repository.RecentSearchRepository
import com.example.billionairehari.core.data.repository.RecentSearchType
import com.example.billionairehari.core.data.repository.RoomRepository
import com.example.billionairehari.model.RoomCardDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


val greats = listOf<RoomCardDetails>(
    RoomCardDetails(
        id = "6",
        name = "Room 101",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 102",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 103",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 104",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    ),
    RoomCardDetails(
        id = "6",
        name = "Room 105",
        total_beds = "4",
        is_available = false,
        count = 3,
        images = emptyList(),
        deposit_per_tenant = "3000",
        rent_per_tenant = "2000",
        features = emptyList(),
        rent_dues = 3,
        under_notice = 0
    )
)

sealed class SearchUiState<out T> {
    data class Default(val recent_searches:List<String>): SearchUiState<Nothing>()
    data class Data<T>(val data:List<T>): SearchUiState<T>()
    object Loading: SearchUiState<Nothing>()
}

@HiltViewModel
class RoomSearchViewModel @Inject constructor(
    private val recent_search: RecentSearchRepository,
    private val repository: RoomRepository,
    private val savedState: SavedStateHandle
): ViewModel() {
//    var recent_searches = recent_search.getRecentSearches(type = RecentSearchType.ROOMS, ownerId = "billionairehari")
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )
    var rooms:List<RoomDao.RoomCard> = emptyList()
    init {
        viewModelScope.launch {
            rooms = repository.getRoomCards(ownerId = "1")
        }
    }

    private val _query = MutableStateFlow<TextFieldValue>(TextFieldValue(""))
    val query = _query.asStateFlow()
    val results: StateFlow<SearchUiState<RoomDao.RoomCard>> = query
        .debounce(300L)
        .distinctUntilChanged()
        .map {
            text ->
            Log.d("BillionaireHari",rooms.toString())
            if(text.text.length <= 2) SearchUiState.Default(listOf("BillionaireHari","BillionaireHari"))
            else SearchUiState.Data<RoomDao.RoomCard>(rooms.filter { it.name.contains(text.text) })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Loading
        )

    fun update_query(query:String) {
        _query.value = TextFieldValue(
            text = query,
            selection = TextRange(query.length)
        )
    }

    fun reset_query(){
        _query.value = TextFieldValue(text = "", selection = TextRange(0))
    }
}
