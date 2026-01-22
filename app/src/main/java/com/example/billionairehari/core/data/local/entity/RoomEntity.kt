package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.billionairehari.core.data.local.typeconverter.ListConverter

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
    @ColumnInfo(index = true) var name:String,
    var images:List<String>,
    @ColumnInfo(name = "bed_count") var bedCount:Int,
    @ColumnInfo(name = "rent_price") var rentPrice: Int,
    var deposit:Int,
    var features: List<String>,
    @ColumnInfo(name = "due_day") var dueDate:String, // DD
    var location:String,
    @ColumnInfo(name = "owner_id", index = true) val ownerId:String,

    @ColumnInfo(name = "updated_at") val updatedAt:String, // YYYY-MM-DD HH:mm
    @ColumnInfo(name = "created_at") val createdAt:String // YYYY-MM-DD HH:mm
)