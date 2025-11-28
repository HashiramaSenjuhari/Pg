package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.billionairehari.core.data.local.entity.Owner

@Entity(
    tableName = "properties",
    foreignKeys = [ForeignKey(
        entity = Owner::class,
        parentColumns = ["id"],
        childColumns = ["owner_id"]
    )]
)
data class Property(
    @PrimaryKey @ColumnInfo(name = "property_id") val id:String,
    val name:String,
    @ColumnInfo(name = "owner_id") val ownerId:String
)