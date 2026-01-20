package com.example.billionairehari.utils

import com.example.billionairehari.components.sheets.SelectedType
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.model.Tenant

sealed class MODAL_TYPE {
    object ADD_ROOM: MODAL_TYPE()
    object NONE: MODAL_TYPE()
    data class ADD_TENANT(val room: Pair<String,String>? = null): MODAL_TYPE()
    data class UPDATE_TENANT(val tenant: Tenant): MODAL_TYPE()
    data class UPDATE_ROOM(val id: String): MODAL_TYPE()
    data class UPDATE_TENANT_RENT(val tenantRentDetails: TenantDao.TenantWithRoomRentCard? = null): MODAL_TYPE()
    data class ANNOUNCE(val reveivers:List<SelectedType>? = null): MODAL_TYPE()
}

sealed class DIALOG_TYPE {
    data class DELETE_ROOM(val id:String): DIALOG_TYPE()
    data class DELETE_TENANT(val id:String): DIALOG_TYPE()
    object NONE: DIALOG_TYPE()
}