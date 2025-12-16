package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.billionairehari.core.data.local.entity.Room

@Entity(
    tableName = "tenants",
    foreignKeys = [
        ForeignKey(
            entity = Room::class,
            parentColumns = ["id"],
            childColumns = ["room_id"]
        )
    ]
)
data class Tenant(
    @PrimaryKey val id:String,
    @ColumnInfo(index = true) val name:String,
    val image:String,
    @ColumnInfo(name = "phone_number") val phoneNumber:String,
    @ColumnInfo(name = "joining_date") val joiningDate:String,
    @ColumnInfo(name = "automatic_rent_remainder") val automaticRentRemainder:Boolean,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "room_id", index = true) val roomId:String,
    @ColumnInfo(name = "created_at") val createdAt:String
)