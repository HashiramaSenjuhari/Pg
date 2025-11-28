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
    val phont:String,
    @ColumnInfo(name = "is_verified") val isVerified:Boolean
)