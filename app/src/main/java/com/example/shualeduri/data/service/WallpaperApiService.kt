package com.example.shualeduri.data.service

import com.example.shualeduri.data.model.WallpaperResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApiService {
    @GET("/api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("orientation") orientation: String = "vertical",
        @Query("category") category: String = "all",
        @Query("order") order: String = "popular",
        @Query("page") page: Int = 1,
        @Query("safesearch") safeSearch: Boolean = true,
        @Query("per_page") perPage: Int = 40
    ): Response<WallpaperResponseDto>

    @GET("/api/")
    suspend fun getImageById(
        @Query("key") apiKey: String,
        @Query("id") id: Long
    ): Response<WallpaperResponseDto>
}