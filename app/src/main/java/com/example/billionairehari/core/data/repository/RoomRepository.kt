package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.ApiResult
import com.example.billionairehari.core.data.interfaces.RoomRepositoryInterface
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomDao: RoomDao
): RoomRepositoryInterface {
    override suspend fun insertRoom(room: Room){
        try {
            roomDao.insertRoom(room = room)
        }catch(error: Exception){

        }
    }
    override suspend fun getRoomCards(ownerId: String): Flow<ApiResult<List<RoomDao.RoomCard>>> = roomDao
        .getRoomsFlow(ownerId = ownerId)
        .map { ApiResult.Success(it) }
        .catch { ApiResult.Error(code = 300, message = it.message ?: "") }
}