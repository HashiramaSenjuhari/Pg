package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.entity.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepositoryInterface {
    suspend fun insertRoom(room: Room)

    fun getRoomCardsFlow(ownerId:String): Flow<List<RoomDao.RoomCard>>

    suspend fun getRoomCards(ownerId:String): List<RoomDao.RoomCard>

    fun searchRooms(ownerId:String,query:String): Flow<List<RoomDao.RoomCard>>
//    suspend fun getRoom(ownerId:String,roomId:String) : Room
}
