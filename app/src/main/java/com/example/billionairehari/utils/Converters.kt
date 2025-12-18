package com.example.billionairehari.utils

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date


/** Long to String Converter **/

fun Long.toDateTimeString() : String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return format.format(date)
}

fun Long.toDateString(): String {
    val datetime = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(datetime)
}


/** String to Long Converter **/

fun String.fromDateLong() : Long {
    return LocalDate.parse(this).toEpochDay()
}

fun combineDaytoCurrentDate(day:String):String {
    return "${currentYear()}-${currentMonthInt()}-${day.padStart(2,'0')}"
}