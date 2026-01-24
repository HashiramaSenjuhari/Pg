package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.billionairehari.core.data.local.entity.Owner
import kotlinx.coroutines.flow.Flow


@Dao
interface OwnerDao {
  @Insert
  suspend fun insertOwner(owner: Owner)

  @Query("SELECT * FROM owners WHERE phone = :phone")
  suspend fun getOwner(phone:String): Owner


  @Query("""
        SELECT
            strftime('%Y-%m-%d',o.created_at)
        FROM owners o
        WHERE o.id = :ownerId
    """)
  fun getFirstDate(ownerId:String): Flow<String>

  @Update
  suspend fun updateOwner(owner: Owner)
}