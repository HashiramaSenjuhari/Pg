package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.billionairehari.core.data.local.entity.Owner

@Entity(
    tableName = "recent_searches",
    foreignKeys = [
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ],
    indices = [
        Index(value = ["text"], unique = true)
    ]
)
data class RecentSearch(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "search_type") val searchType:String,
    val text:String,
    @ColumnInfo(name = "owner_id") val ownerId:String,
    @ColumnInfo(name = "created_at") val createdAt:String
)