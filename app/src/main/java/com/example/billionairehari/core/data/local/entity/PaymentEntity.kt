package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.billionairehari.core.data.local.entity.Tenant

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Tenant::class,
            parentColumns = ["id"],
            childColumns = ["tenant_id"]
        ),
        ForeignKey(
            entity = Room::class,
            parentColumns = ["id"],
            childColumns = ["room_id"]
        )
    ]
)
data class Payment(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "tenant_id") val tenantId:String,
    @ColumnInfo(name = "room_id") val roomId:String,
    @ColumnInfo(name = "amount") val amount:Int,
    @ColumnInfo(name = "payment_date") val paymentDate:Long,
    @ColumnInfo(name = "payment_type") val paymentType:String
)