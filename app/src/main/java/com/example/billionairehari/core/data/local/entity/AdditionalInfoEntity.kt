package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "additional_info",
    foreignKeys = [
        ForeignKey(
            entity = Tenant::class,
            parentColumns = ["id"],
            childColumns = ["tenant_id"]
        )
    ]
)
data class AdditionalInfo(
    @PrimaryKey val id:String,
    val dob:String,
    val state:String,
    @ColumnInfo(name = "alternate_phone") val alternatePhone:String,
    @ColumnInfo(name = "identity_document") val identityDocument:String,
    @ColumnInfo(index = true) val tenant_id:String
)