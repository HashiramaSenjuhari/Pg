package com.example.billionairehari.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import retrofit2.Retrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KycApi;

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @KycApi
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder().
            baseUrl("")
            .build();
    }
}