package com.example.billionairehari.model

data class Room(
    val name:String = "",
    val images: List<ByteArray> = emptyList(),
    val total_beds:String = "",
    val count:Int = 0,
    val rent_per_tenant:String = "",
    val deposit_per_tenant:String = "",
    val features:List<String> = emptyList()
)

data class RoomCardDetails(
    val id:String = "",
    val name:String = "",
    val images: List<ByteArray> = emptyList(),
    val total_beds:String = "",
    val count:Int = 0,
    val rent_per_tenant:String = "",
    val deposit_per_tenant:String = "",
    val features:List<String> = emptyList(),
    val is_available:Boolean = false,
    val under_notice:Int = 0,
    val rent_dues:Int = 0
)