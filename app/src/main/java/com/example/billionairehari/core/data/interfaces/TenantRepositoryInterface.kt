package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Tenant
import kotlinx.coroutines.flow.Flow

interface TenantRepositoryInterface {
    suspend fun insertTenant(tenant: Tenant)
    fun getTenantsCardFlow(ownerId:String): Flow<List<TenantDao.TenantCardDetails>>
    suspend fun getTenantsCard(ownerId:String): List<TenantDao.TenantCardDetails>
    fun getTenant(tenantId:String,ownerId:String): Flow<TenantDao.TenantDetails>
    fun getPaidCount(ownerId:String): Flow<TenantDao.RentPaid>
    fun getNotPaidCount(ownerId:String) : Flow<TenantDao.RentNotPaid>
    fun getTenantRecentPayments(ownerId:String,tenantId:String): Flow<List<TenantDao.PaymentCard>>
    fun getTenantPayments(ownerId:String,tenantId:String): Flow<List<TenantDao.PaymentCard>>
    fun getTenantSearchCards(ownerId:String): Flow<List<TenantDao.TenantWithRoomRentCard>>
    suspend fun removeTenant(ownerId:String,tenantId:String)
}