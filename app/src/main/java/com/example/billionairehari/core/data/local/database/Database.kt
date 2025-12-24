package com.example.billionairehari.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.billionairehari.core.data.local.dao.ActivityDao
import com.example.billionairehari.core.data.local.dao.AdditionalInfoDao
import com.example.billionairehari.core.data.local.dao.OwnerDao
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.dao.RecentSearchDao
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.entity.Owner
import com.example.billionairehari.core.data.local.entity.Payment
import com.example.billionairehari.core.data.local.entity.Tenant
import com.example.billionairehari.core.data.local.entity.Room
import com.example.billionairehari.core.data.local.entity.Activity
import com.example.billionairehari.core.data.local.entity.AdditionalInfo
import com.example.billionairehari.core.data.local.entity.RecentSearch
import com.example.billionairehari.core.data.local.typeconverter.PaymentTypeConverter

/**
 * Abstract class the must have no body and must provide by subclass
 *
 * here the AppDatabase is extended by RoomDatabase() and added some abstract function with no body that taken care in hilt
 **/
@Database(
    entities = [Owner::class, Room::class, Tenant::class, AdditionalInfo::class, Payment::class, Activity::class, RecentSearch::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun ownerDao(): OwnerDao
    abstract fun roomDao(): RoomDao
    abstract fun tenantDao(): TenantDao
    abstract fun additionalInfoDao(): AdditionalInfoDao
    abstract fun paymentDao(): PaymentDao
    abstract fun activityDao(): ActivityDao
    abstract fun recentSearchDao(): RecentSearchDao
}