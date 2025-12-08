package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.RecentSearchRepositoryInterface
import com.example.billionairehari.core.data.local.dao.RecentSearchDao
import com.example.billionairehari.core.data.local.entity.RecentSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

enum class RecentSearchType { ROOMS,TENANTS }

class RecentSearchRepository @Inject constructor (
    private val recentSearchDao: RecentSearchDao
): RecentSearchRepositoryInterface {
    override suspend fun insertRecentSearch(recent_search: RecentSearch) {
        recentSearchDao.insertSearch(recent_search)
    }

    override fun getRecentSearches(
        ownerId: String,
        type: RecentSearchType
    ): Flow<List<RecentSearchDao.RecentSearchData>> = recentSearchDao
        .getRecentSearch(ownerId = ownerId, type = type.name.lowercase())
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(code = 500,message = it.message ?: "") }

    override suspend fun clearRecentSearches(ownerId: String, type: RecentSearchType) {
        recentSearchDao.clearRecentSearch(ownerId = ownerId, type = type.name.lowercase())
    }
}