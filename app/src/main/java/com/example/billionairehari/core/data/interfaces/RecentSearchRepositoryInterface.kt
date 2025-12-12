package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.RecentSearchDao
import com.example.billionairehari.core.data.local.entity.RecentSearch
import com.example.billionairehari.core.data.repository.RecentSearchType
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepositoryInterface {
    suspend fun insertRecentSearch(recent_search: RecentSearch)
    fun getRecentSearches(ownerId:String,type: RecentSearchType): Flow<List<RecentSearchDao.RecentSearchData>>
    suspend fun totalRecentSearches(ownerId:String,type: RecentSearchType):Int
    suspend fun clearRecentSearches(ownerId:String,type: RecentSearchType)
    suspend fun clearOldSearch(ownerId:String,type: RecentSearchType)
}