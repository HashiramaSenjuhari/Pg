package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Tenant
import kotlinx.coroutines.flow.Flow

interface TenantRepositoryInterface {
    suspend fun insertTenant(tenant: Tenant)
    fun getTenantsCard(ownerId:String): Flow<ApiResult<List<TenantDao.TenantCardDetails>>>
    fun getTenant(tenantId:String,ownerId:String): Flow<ApiResult<TenantDao.TenantDetails>>
    fun getPaidCount(ownerId:String): Flow<ApiResult<TenantDao.RentPaid>>
    fun getNotPaidCount(ownerId:String) : Flow<ApiResult<TenantDao.RentNotPaid>>
}