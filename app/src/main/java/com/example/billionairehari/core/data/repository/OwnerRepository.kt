package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.data.local.dao.OwnerDao
import com.example.billionairehari.core.data.local.entity.Owner

class OwnerRepository constructor(
    private val ownerDao: OwnerDao
): OwnerRepositoryInterface() {
    override suspend fun createOwner(owner: Owner): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getOwner(phone: String): Owner {
        TODO("Not yet implemented")
    }

    override suspend fun updateOwner(ownerId: String): Int {
        TODO("Not yet implemented")
    }
}