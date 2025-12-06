package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.billionairehari.core.data.local.entity.Owner


@Dao
interface OwnerDao {
  @Insert
  suspend fun insertOwner(owner: Owner)

  @Query("SELECT * FROM owners WHERE phone = :phone")
  suspend fun getOwner(phone:String): Owner

  @Update
  suspend fun updateOwner(owner: Owner)
}