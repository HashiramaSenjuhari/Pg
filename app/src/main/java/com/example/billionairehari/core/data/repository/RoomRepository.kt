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
    override suspend fun getRoomCards(ownerId: String): List<RoomDao.RoomCard> = roomDao.getRoomsCard(ownerId = ownerId)

    override fun getRoomCardsFlow(ownerId: String): Flow<List<RoomDao.RoomCard>> = roomDao.getRoomsCardFlow(ownerId = ownerId)
    override fun searchRooms(ownerId: String, query: String): Flow<List<RoomDao.RoomCard>> = roomDao
        .searchRooms(ownerId = ownerId, query = query)
}