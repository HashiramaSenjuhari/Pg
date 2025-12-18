package com.example.billionairehari.core.data.local.typeconverter

import androidx.room.TypeConverter
import com.example.billionairehari.core.data.local.entity.PaymentStatus
import com.example.billionairehari.core.data.local.entity.PaymentType


class PaymentTypeConverter {
    @TypeConverter
    fun fromTypeString(string:String?) : PaymentType? {
        return string?.let { PaymentType.valueOf(it) }
    }

    @TypeConverter
    fun toTypeString(type: PaymentType?): String? {
        return type?.let { it.name }
    }

    @TypeConverter
    fun fromStatusString(string:String?) : PaymentStatus? {
        return string?.let { PaymentStatus.valueOf(it) }
    }

    @TypeConverter
    fun toStatusString(status: PaymentStatus?): String? {
        return status?.let { it.name }
    }
}

class ListConverter{
    @TypeConverter
    fun fromList(list:List<String>?) : String? {
        return list?.let { it.joinToString(",") }
    }

    @TypeConverter
    fun toList(string:String?) : List<String>? {
        return string?.let { it.split(",").toList() }
    }
}