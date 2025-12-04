package com.example.billionairehari.core.data.repository

import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.model.RoomCardDetails
import kotlinx.coroutines.flow.Flow

class RoomRepository constructor(
    private val roomDao: RoomDao
): RoomRepositoryInterface() {
    override suspend fun createRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun updateRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun getRoom(id: String): Room {
        TODO("Not yet implemented")
    }
    override suspend fun getRoomCard(ownerId: String): Flow<List<RoomCardDetails>> {
        TODO("Not yet implemented")
    }
}