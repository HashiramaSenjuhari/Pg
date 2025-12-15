package com.example.billionairehari.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.example.billionairehari.core.data.local.dao.ActivityDao
import com.example.billionairehari.core.data.local.dao.OwnerDao
import com.example.billionairehari.core.data.local.dao.PaymentDao
import com.example.billionairehari.core.data.local.dao.RecentSearchDao
import com.example.billionairehari.core.data.local.dao.RoomDao
import com.example.billionairehari.core.data.local.dao.TenantDao
import com.example.billionairehari.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    /** App Database Init bind **/
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase = Room
        .databaseBuilder(
            context = context,
            name = "test_billionaire6",
            klass = AppDatabase::class.java
        )
        .addCallback(object: RoomDatabase.Callback() {
            override fun onOpen(connection: SQLiteConnection) {
                super.onOpen(connection)
                Log.d("BillionaireHariGreat","Room Opened")
            }
        })
        .build()

    /** DAO bind **/

    @Provides
    @Singleton
    fun provideOwnerDao(dao: AppDatabase) : OwnerDao = dao.ownerDao()

    @Provides
    @Singleton
    fun provideRoomDao(dao: AppDatabase): RoomDao = dao.roomDao()

    @Provides
    @Singleton
    fun provideTenantDao(dao: AppDatabase): TenantDao = dao.tenantDao()

    @Provides
    @Singleton
    fun providePaymentDao(dao: AppDatabase): PaymentDao = dao.paymentDao()

    @Provides
    @Singleton
    fun provideRecentSearchDao(dao: AppDatabase): RecentSearchDao = dao.recentSearchDao()

    @Provides
    @Singleton
    fun provideActivityDao(dao: AppDatabase): ActivityDao = dao.activityDao()
}