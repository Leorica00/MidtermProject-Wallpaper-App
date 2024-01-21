package com.example.shualeduri.domain.repository

import com.example.shualeduri.data.common.Resource
import com.example.shualeduri.domain.model.GetImage
import kotlinx.coroutines.flow.Flow

interface SingleImageRepository {
    suspend fun getImageById(apiKey: String, id: Long): Flow<Resource<GetImage>>
}