package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.billionairehari.core.data.local.entity.Tenant
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

enum class PaymentType {
    CASH,
    UPI
}

class PaymentTypeConverter{
    @TypeConverter
    fun fromString(json:String?) : PaymentType {
        json?.let { PaymentType.valueOf(it) }
    }

    @TypeConverter
    fun toString(type: PaymentType?): String {
        type?.let { it.name }
    }
}

@TypeConverters(PaymentTypeConverter::class)
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
    @ColumnInfo(name = "payment_type") val paymentType: PaymentType
)