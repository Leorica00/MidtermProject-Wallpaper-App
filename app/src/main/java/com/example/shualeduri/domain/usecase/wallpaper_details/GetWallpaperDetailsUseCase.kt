package com.example.shualeduri.domain.usecase.wallpaper_details

import com.example.shualeduri.data.common.Resource
import com.example.shualeduri.data.mapper.base.asResource
import com.example.shualeduri.presentation.mapper.toPresenter
import com.example.shualeduri.domain.repository.SingleImageRepository
import com.example.shualeduri.presentation.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWallpaperDetailsUseCase @Inject constructor(private val wallpaperDetailsRepository: SingleImageRepository) {
    suspend operator fun invoke(apiKey: String, id: Long): Flow<Resource<Image>> {
        return wallpaperDetailsRepository.getImageById(apiKey, id).asResource { it.toPresenter() }
    }
}