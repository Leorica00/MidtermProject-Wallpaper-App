package com.example.shualeduri.di

import com.example.shualeduri.data.common.response_handler.HandleResponse
import com.example.shualeduri.data.repository.SingleImageRepositoryImpl
import com.example.shualeduri.data.repository.WallpaperRepositoryImpl
import com.example.shualeduri.data.service.WallpaperApiService
import com.example.shualeduri.domain.repository.SingleImageRepository
import com.example.shualeduri.domain.repository.WallpaperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWallpaperRepository(wallpaperApiService: WallpaperApiService): WallpaperRepository =
        WallpaperRepositoryImpl(apiService = wallpaperApiService)

    @Singleton
    @Provides
    fun provideSingleImageRepository(
        wallpaperApiService: WallpaperApiService,
        handleResponse: HandleResponse
    ): SingleImageRepository =
        SingleImageRepositoryImpl(
            wallpaperApiService = wallpaperApiService,
            handleResponse = handleResponse
        )

}