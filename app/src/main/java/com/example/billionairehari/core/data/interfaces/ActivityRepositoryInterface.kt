package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.entity.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepositoryInterface {
    suspend fun insertActvity(activity: Activity)
    fun getActivities(ownerId:String): Flow<ApiResult<List<Activity>>>
}