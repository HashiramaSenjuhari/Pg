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

    override fun getTenantsCardFlow(ownerId: String): Flow<ApiResult<List<TenantDao.TenantCardDetails>>> = tenantDao
        .getTenantsFlow(ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(code = 300, message = it.message ?: "") }

    override suspend fun getTenantsCard(ownerId: String): ApiResult<List<TenantDao.TenantCardDetails>> =
        ApiResult.Success(tenantDao.getTenantsCard(ownerId = ownerId))

    override fun getTenant(
        tenantId: String,
        ownerId: String
    ): Flow<ApiResult<TenantDao.TenantDetails>> = tenantDao
        .getTenant(tenantId = tenantId, ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(code = 300, message = it.message ?: "") }

    override fun getPaidCount(ownerId: String): Flow<ApiResult<TenantDao.RentPaid>> = tenantDao
        .getRentPaidFlow(ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(500, message = it.message ?: "") }

    override fun getNotPaidCount(ownerId: String): Flow<ApiResult<TenantDao.RentNotPaid>> = tenantDao
        .getRentNotPaidFlow(ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(500, message = it.message ?: "") }
}