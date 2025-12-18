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


enum class PaymentStatus { NOT_PAID,PARTIAL,PAID }

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
@TypeConverters(PaymentTypeConverter::class)
data class Payment(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "tenant_id", index = true) val tenantId:String,
    @ColumnInfo(name = "room_id", index = true) val roomId:String,
    @ColumnInfo(name = "owner_id", index = true) val ownerId:String,

    @ColumnInfo(name = "amount") val amount:Int,
    @ColumnInfo(name = "payment_status") val paymentStatus: PaymentStatus, // { NOT_PAID, PARTIAL,PAID }
    @ColumnInfo(name = "payment_type") val paymentType: PaymentType, // { UPI,CASH }

    @ColumnInfo(name = "payment_date") val paymentDate:String, // YYYY-MM-DD
    @ColumnInfo(name = "due_date") val dueDate:String, // YYYY-MM-DD


    @ColumnInfo(name = "updated_at") val updatedAt:String, // YYYY-MM-DD HH:mm
    @ColumnInfo(name = "created_at") val createdAt:String  // YYYY-MM-DD HH:mm
)