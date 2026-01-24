package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.entity.Owner
import kotlinx.coroutines.flow.Flow

interface OwnerRepositoryInterface {
    suspend fun createOwner(owner: Owner)
    suspend fun getOwner(phone:String): Owner
    suspend fun updateOwner(ownerId:String): Int
    fun getFirstDate(ownerId:String): Flow<String>
}