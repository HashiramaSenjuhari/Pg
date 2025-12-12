package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.billionairehari.core.data.local.entity.Owner

@Entity(
    tableName = "activities",
    foreignKeys = [
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class Activity(
    @PrimaryKey val id:String,
    val name:String,
    val message:String,
    val type:String,
    @ColumnInfo(name = "owner_id") val ownerId:String,
    @ColumnInfo(name = "created_at") val createdAt:String
)