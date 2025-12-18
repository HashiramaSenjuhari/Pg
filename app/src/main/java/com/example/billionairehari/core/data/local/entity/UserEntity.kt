package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Properties

@Entity(tableName = "owners")
data class Owner(
    @PrimaryKey val id:String,
    val name:String,
    val phone:String,
    @ColumnInfo(name = "pg_name") val pgName:String,
    @ColumnInfo(name = "is_verified") val isVerified:Boolean,

    @ColumnInfo(name = "updated_at") val updatedAt:String, // YYYY-MM-DD HH:mm
    @ColumnInfo(name = "last_visit") val lastVisit:String, // YYYY-MM-DD HH:mm
    @ColumnInfo(name = "created_at") val createdAt:String // YYYY-MM-DD HH:mm
)