package com.example.billionairehari.utils

import android.icu.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone


/** Long to String Converter **/

fun Long.toDateTimeString() : String {
    val date = Date(this)
    return dateZoneFormat(date.time)
}

fun Long.toDateString(): String {
    val datetime = Date(this)
    return dateZoneFormat(datetime.time)
}

fun Long.toDateFormat(format:String = "dd-MM-yyyy") : String {
    val date = Date(this)
    return dateZoneFormat(date = date.time, format = format)
}

fun Long.toFriendlyDate(): String {
    val date = Date(this)
    return dateZoneFormat(date.time, format = "dd MMM yyyy")
}

fun dateZoneFormat(date:Long,format:String = "yyyy-MM-dd"):String{
    val format = DateTimeFormatter.ofPattern(format)
        .withZone(ZoneId.of("Asia/Kolkata"))
    return format.format(ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(date),
        ZoneId.of("Asia/Kolkata")
    ))
}


/** String to Long Converter **/

fun String.fromDateLong() : Long {
    return LocalDate.parse(this).toEpochDay()
}

fun combineDaytoCurrentDate(day:String):String {
    return "${currentYear()}-${currentMonthInt()}-${day.padStart(2,'0')}"
}

