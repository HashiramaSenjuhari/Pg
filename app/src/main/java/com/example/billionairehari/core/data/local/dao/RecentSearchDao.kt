package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.RecentSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Insert
    suspend fun insertSearch(search: RecentSearch)

    data class RecentSearchData(val text:String)

    @Query("""
        SELECT
        text
        FROM recent_searches
        WHERE owner_id = :ownerId AND search_type = :type
        ORDER BY id
    """)
    fun getRecentSearch(ownerId:String,type:String): Flow<List<RecentSearchData>>

    @Query("""
        DELETE FROM recent_searches WHERE search_type = :type AND owner_id = :ownerId
    """)
    suspend fun clearRecentSearch(ownerId:String,type: String)
}