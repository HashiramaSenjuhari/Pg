package com.example.billionairehari.utils

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun currentYear():String {
    return LocalDate.now(ZoneId.of("Asia/Kolkata"))
        .format(DateTimeFormatter.ofPattern("yyyy"))
}

fun currentMonthInt():Int {
    return LocalDate.now().monthValue
}

fun currentMonth():String {
    return LocalDate.now(ZoneId.of("Asia/Kolkata"))
        .format(DateTimeFormatter.ofPattern("MMM"))
}

fun currentDateTime():String {
    return LocalDateTime.now(ZoneId.of("Asia/Kolkata"))
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

fun currentDate():String {
    return LocalDate.now(ZoneId.of("Asia/Kolkata"))
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

fun currentMonthStartAndEndInMilli(): Pair<Long,Long>{
    val zoneId = ZoneId.of("Asia/Kolkata")
    val time = ZonedDateTime.now(zoneId)
    val currentMonth = YearMonth.from(time)

    val monthStart = currentMonth.atDay(1)
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
    val monthLast = currentMonth.atEndOfMonth()
        .atTime(23,59,59,999_000_000)
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli()
    return monthStart to monthLast
}