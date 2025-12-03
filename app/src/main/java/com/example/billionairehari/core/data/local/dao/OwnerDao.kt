package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billionairehari.core.data.local.entity.Owner


@Dao
interface OwnerDao {
  @Insert
  fun insertOwner(owner: Owner)

  @Query("SELECT * FROM owners WHERE phone = :phone")
  fun getOwner(phone:String): Owner
}