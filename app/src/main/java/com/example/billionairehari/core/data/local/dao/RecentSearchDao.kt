package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.RecentSearch

@Dao
interface RecentSearchDao {
    @Insert
    suspend fun insertSearch(search: RecentSearch)

    data class RecentSearchData(val text:String)

    @Query("""
        SELECT
        text
        FROM recent_searches
        WHERE owner_id = :ownerId AND search_type = 'room'
        ORDER BY id
    """)
    suspend fun getRoomRecentSearch(ownerId:String): RecentSearchData

    @Query("""
        SELECT
        text
        FROM recent_searches
        WHERE owner_id = :ownerId AND search_type = 'tenant'
        ORDER BY id
    """)
    suspend fun getTenantRecentSearch(ownerId:String) : RecentSearchData

    @Query("""
        DELETE FROM recent_searches WHERE search_type = 'room' AND owner_id = :ownerId
    """)
    suspend fun clearRoomRecentSearch(ownerId:String)

    @Query("""
        DELETE FROM recent_searches WHERE search_type = 'tenant' AND owner_id = :ownerId
    """)
    suspend fun clearTenantRecentSearch(ownerId:String)
}