package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.TenantRepositoryInterface
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Tenant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *  Flow works on conversing the flow
 *  using try catch in flow make it one time function
 *  so using flow directly makes flow
 **/

class TenantRepository @Inject constructor(
    private val tenantDao: TenantDao
): TenantRepositoryInterface {
    override suspend fun insertTenant(tenant: Tenant) {
        tenantDao.insertTenant(tenant = tenant)
    }

    override fun getTenantsCardFlow(ownerId: String): Flow<List<TenantDao.TenantCardDetails>> = tenantDao
        .getTenantsCardFlow(ownerId = ownerId)

    override suspend fun getTenantsCard(ownerId: String): List<TenantDao.TenantCardDetails> =
        tenantDao.getTenantsCard(ownerId = ownerId)

    override fun getTenant(
        tenantId: String,
        ownerId: String
    ): Flow<TenantDao.TenantDetails> = tenantDao
        .getTenant(tenantId = tenantId, ownerId = ownerId)

    override fun getPaidCount(ownerId: String): Flow<TenantDao.RentPaid> = tenantDao
        .getRentPaidFlow(ownerId = ownerId)

    override fun getNotPaidCount(ownerId: String): Flow<TenantDao.RentNotPaid> = tenantDao
        .getRentNotPaidFlow(ownerId = ownerId)
}