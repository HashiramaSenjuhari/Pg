package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.entity.Owner

interface OwnerRepositoryInterface {
    suspend fun createOwner(owner: Owner)
    suspend fun getOwner(phone:String): ApiResult<Owner>
    suspend fun updateOwner(ownerId:String): Int
}