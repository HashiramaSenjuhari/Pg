package com.example.billionairehari.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TenantWithRoomName(
    @Embedded val tenant: Tenant,
    val roomName:String
)
