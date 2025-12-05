package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.billionairehari.core.data.local.entity.Owner

@Entity(
    tableName = "recent_searches",
    foreignKeys = [
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class RecentSearch(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "search_type") val searchType:String,
    @ColumnInfo(index = true) val text:String,
    @ColumnInfo(name = "owner_id") val ownerId:String
)