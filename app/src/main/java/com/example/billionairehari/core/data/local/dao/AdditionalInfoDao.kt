package com.example.billionairehari.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.billionairehari.core.data.local.entity.AdditionalInfo

@Dao
interface AdditionalInfoDao {
    @Insert
    suspend fun insertAdditionalInfo(info: AdditionalInfo)
}