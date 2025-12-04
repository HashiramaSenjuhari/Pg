package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.data.local.entity.Owner

interface OwnerRepositoryInterface {
    suspend fun createOwner(owner: Owner):Int
    suspend fun getOwner(phone:String): Owner
    suspend fun updateOwner(ownerId:String): Int
}