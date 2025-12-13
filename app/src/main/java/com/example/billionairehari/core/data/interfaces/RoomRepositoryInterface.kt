package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.entity.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepositoryInterface {
    suspend fun insertRoom(room: Room)

    fun getRoomNames(ownerId:String): Flow<List<String>>
    suspend fun getRooms(): List<Room>
    suspend fun getRoom(roomId:String,ownerId:String): Room
    fun getRoomCardsFlow(ownerId:String): Flow<List<RoomDao.RoomCard>>

    suspend fun getRoomCards(ownerId:String): List<RoomDao.RoomCard>

    fun searchRooms(ownerId:String,query:String): Flow<List<RoomDao.RoomCard>>
    suspend fun getTables():List<String>
    suspend fun deleteRooms()
    suspend fun deleteRoom(ownerId:String,roomId:String)

    fun getRoomNameAndAvailability(ownerId:String): Flow<List<String>>
}
