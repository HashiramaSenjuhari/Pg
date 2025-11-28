package com.example.billionairehari.core.data.local.entity

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ListConverter{
    private val moshi = Moshi.Builder()
        .build()
    private val type = Types.newParameterizedType(
        List::class.java,
        String::class.java
    )
    private val adapter : JsonAdapter<List<String>> = moshi.adapter(type)

    @TypeConverter
    fun fromJson(json:String?):List<String> = json?.let { adapter.fromJson(it) }

    @TypeConverter
    fun toJson(list:List<String>?) : String? = list?.let { adapter.toJson(it) }
}