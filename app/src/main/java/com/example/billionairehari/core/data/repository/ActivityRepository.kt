package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.ActivityRepositoryInterface
import com.example.billionairehari.core.data.local.dao.ActivityDao
import com.example.billionairehari.core.data.local.entity.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ActivityRepository @Inject constructor(
    private val activityDao: ActivityDao
): ActivityRepositoryInterface {
    override suspend fun insertActvity(activity: Activity) = activityDao.insertAcivity(acivity = activity)

    override fun getActivities(ownerId: String): Flow<List<Activity>> = activityDao
        .getActivities(ownerId = ownerId)
}