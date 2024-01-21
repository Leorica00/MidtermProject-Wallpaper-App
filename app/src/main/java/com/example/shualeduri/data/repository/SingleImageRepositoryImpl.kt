package com.example.shualeduri.data.repository

import com.example.shualeduri.data.common.response_handler.HandleResponse
import com.example.shualeduri.data.common.Resource
import com.example.shualeduri.data.mapper.base.asResource
import com.example.shualeduri.data.mapper.wallpaper_details.toDomain
import com.example.shualeduri.data.service.WallpaperApiService
import com.example.shualeduri.domain.model.GetImage
import com.example.shualeduri.domain.repository.SingleImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SingleImageRepositoryImpl @Inject constructor(private val wallpaperApiService: WallpaperApiService, private val handleResponse: HandleResponse): SingleImageRepository {
    override suspend fun getImageById(apiKey: String, id: Long): Flow<Resource<GetImage>> {
        return handleResponse.safeApiCall {
            wallpaperApiService.getImageById(apiKey, id)
        }.asResource {
            it.hits.first().toDomain()
        }
    }
}