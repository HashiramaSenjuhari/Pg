package com.example.billionairehari.model

data class Tenant(
    val name:String = "",
    val phone_number:String = "",
    val room:String = "",
    val joining_date:Long = 0L,
    val rent_paid_first:Boolean = false,
    val deposit:Boolean = false,
    val image: ByteArray?= null,
    val automatic_remainder:Boolean = false
)

data class TenantRentRecord(
    val id:String = "",
    val name:String = "",
    val image:String = "",
    val room:String = "",
    val due_date:Long = 0L,
    val rent:String = "",
    val isPaid:Boolean = false
)

data class TenantUi(
    val id:String = "",
    val name:String = "",
    val phone_number:String = "",
    val room:String = "",
    val joining_date:Long = 0L,
    val rent_paid_first:Boolean = false,
    val deposit:Boolean = false,
    val image: ByteArray?= null,
    val automatic_remainder:Boolean = false,
    val under_notice:Boolean = false,
    val current_rent_paid:Boolean = false
)