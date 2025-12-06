package com.example.billionairehari.core.data.interfaces

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.entity.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepositoryInterface {
    suspend fun insertRoom(room: Room)

    suspend fun getRoomCards(ownerId:String): Flow<ApiResult<List<RoomDao.RoomCard>>>

//    suspend fun getRoom(ownerId:String,roomId:String) : Room
}
