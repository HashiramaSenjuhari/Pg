package com.example.billionairehari.core.data.local.typeconverter

import androidx.room.TypeConverter
import com.example.billionairehari.core.data.local.entity.PaymentType


class PaymentTypeConverter{
    @TypeConverter
    fun fromString(json:String?) : PaymentType? {
        return json?.let { PaymentType.valueOf(it) }
    }

    @TypeConverter
    fun toString(type: PaymentType?): String? {
        return type?.let { it.name }
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
