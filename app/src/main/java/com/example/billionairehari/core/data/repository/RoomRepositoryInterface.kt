package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.Flow


interface RoomRepositoryInterface {
    suspend fun createRoom(room: Room):Boolean
    suspend fun updateRoom(room: Room): Boolean
    suspend fun getRoomCard(ownerId:String): Flow<List<RoomCardDetails>>
    suspend fun getRoom(id:String): Room
}
