package com.example.billionairehari.core.data.repository

import android.util.Log
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
        roomDao.insertRoom(room = room)
    }

    override suspend fun getRoom(roomId: String, ownerId: String): Room {
        TODO("Not yet implemented")
    }
    override suspend fun getRoomCards(ownerId: String): List<RoomDao.RoomCard> = roomDao.getRoomsCard(ownerId = ownerId)

    override suspend fun getRooms() : List<Room> = roomDao.getRooms()
    override fun getRoomCardsFlow(ownerId: String): Flow<List<RoomDao.RoomCard>> = roomDao.getRoomsCardFlow(ownerId = ownerId)
    override fun searchRooms(ownerId: String, query: String): Flow<List<RoomDao.RoomCard>> = roomDao
        .searchRooms(ownerId = ownerId, query = query)

    override suspend fun getTables(): List<String> = roomDao.getAllTables()
    override suspend fun deleteRoom(ownerId: String, roomId: String) = roomDao.deleteRoom(ownerId = ownerId,roomId = roomId)
    override suspend fun deleteRooms() = roomDao.deleteRooms()
    override fun getRoomNames(ownerId: String): Flow<List<String>> = roomDao.getRoomNames(ownerId = ownerId)
    override fun getRoomNameAndAvailability(ownerId: String): Flow<List<RoomDao.RoomNameAndTenantCount>> = roomDao
        .getRoomNameAndTenantCount(ownerId)

    override fun getRoomTenants(
        ownerId: String,
        roomId: String
    ): Flow<List<RoomDao.RoomTenantDetails>> = roomDao.getRoomTenantDetails(roomId = roomId, ownerId = ownerId)

    override fun getRoomDetail(
        ownerId: String,
        roomId: String
    ): Flow<RoomDao.RoomWithTenantAndDueCount> = roomDao.getRoomFlow(ownerId = ownerId, roomId = roomId)
}