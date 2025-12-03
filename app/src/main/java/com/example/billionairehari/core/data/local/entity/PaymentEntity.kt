package com.example.billionairehari.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.billionairehari.core.data.local.typeconverter.PaymentTypeConverter

enum class PaymentType {
    CASH,
    UPI
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
        ),
        ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class Payment(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "tenant_id") val tenantId:String,
    @ColumnInfo(name = "room_id") val roomId:String,
    @ColumnInfo(name = "amount") val amount:Int,
    @ColumnInfo(name = "payment_date") val paymentDate:Long,
    @ColumnInfo(name = "due_date") val dueDate:Long,
    @ColumnInfo(name = "is_paid") val isPaid:Boolean,
    @ColumnInfo(name = "payment_type") val paymentType: PaymentType,
    @ColumnInfo(name = "owner_id") val ownerId:String
)