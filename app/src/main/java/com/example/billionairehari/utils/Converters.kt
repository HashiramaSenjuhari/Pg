package com.example.billionairehari.utils

import android.icu.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

const val FRIENDLY_DATE_FORMAT = "dd MM YYYY"
const val DATE_FORMAT = "dd-MM-yyyy"
const val SQL_DATE_FORMAT = "yyyy-MM-dd"

/** Long to String Converter **/

fun Long.toDateFormat(format:String = DATE_FORMAT) : String {
    val date = Date(this)
    return dateZoneFormat(date.time, format = format)
}

fun dateZoneFormat(date:Long,format:String = SQL_DATE_FORMAT):String{
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

fun combineDaytoCurrentDate(day:Int):String {
    return "${currentYear()}-${currentMonthInt()}-${day.toString().padStart(2,'0')}"
}

/** String Date Formatter **/
fun String.formatDate(format:String): String {
    val localDate = LocalDate.parse(this)
    return localDate.format(DateTimeFormatter.ofPattern(format))
}

/** String to Date Long **/
fun String.toDateLong(format:String = SQL_DATE_FORMAT): Long {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
    return localDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli()
}