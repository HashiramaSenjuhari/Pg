package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.OwnerRepositoryInterface
import com.example.billionairehari.core.data.local.dao.OwnerDao
import com.example.billionairehari.core.data.local.entity.Owner
import javax.inject.Inject

class OwnerRepository @Inject constructor(
    private val ownerDao: OwnerDao
): OwnerRepositoryInterface {
    override suspend fun createOwner(owner: Owner)  {
        ownerDao.insertOwner(owner = owner)
    }

    override suspend fun getOwner(phone: String): Owner {
        return try {
            val owner = ownerDao.getOwner(phone = phone)
            ApiResult.Success(owner)
        }catch(error: Exception){
            ApiResult.Error(code = 500, message = error.message ?: "")
        }
    }

    override suspend fun updateOwner(ownerId: String): Int {
        TODO("Not yet implemented")
    }
}