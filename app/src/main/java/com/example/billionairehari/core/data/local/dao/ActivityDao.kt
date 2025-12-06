package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.entity.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert
    suspend fun insertAcivity(acivity: Activity)

    @Query("""
        SELECT * FROM activities WHERE owner_id = :ownerId
    """)
    fun getActivities(ownerId:String): Flow<List<Activity>>
}