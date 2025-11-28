package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.billionairehari.core.data.local.entity.Property


@TypeConverters(ListConverter::class)
@Entity(
    tableName = "rooms",
    foreignKeys = [
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class Room(
    @PrimaryKey val id:String,
    @ColumnInfo(index = true) val name:String,
    @ColumnInfo(name = "bed_count") var bedCount:Int,
    @ColumnInfo(name = "rent_price") var rentPrice: Int,
    var deposit:Int,
    var features: List<String>,
    val property_id:String,
    @ColumnInfo(name = "owner_id") val ownerId:String
)