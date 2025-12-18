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
        ),
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class Tenant(
    @PrimaryKey val id:String,
    @ColumnInfo(index = true) val name:String,
    val image:String,
    @ColumnInfo(name = "phone_number") val phoneNumber:String,
    @ColumnInfo(name = "joining_date") val joiningDate:String, // YYYY-MM-DD
    @ColumnInfo(name = "automatic_rent_remainder") val automaticRentRemainder:Boolean,
    @ColumnInfo(name = "is_active") val isActive: Boolean,

    @ColumnInfo(name = "updated_at") val updatedAt:String, // YYYY-MM-DD HH:mm
    @ColumnInfo(name = "created_at") val createdAt:String, // YYYY-MM-DD HH:mm

    @ColumnInfo(name = "owner_id", index = true) val ownerId:String,
    @ColumnInfo(name = "room_id", index = true) val roomId:String
)