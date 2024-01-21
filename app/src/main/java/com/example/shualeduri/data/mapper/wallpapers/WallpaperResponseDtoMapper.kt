package com.example.shualeduri.data.mapper.wallpapers

import com.example.shualeduri.data.mapper.wallpaper_details.toDomain
import com.example.shualeduri.data.model.WallpaperResponseDto
import com.example.shualeduri.domain.model.GetWallpaperResponse

fun WallpaperResponseDto.toDomain(): GetWallpaperResponse {
    return GetWallpaperResponse(
        hits = hits.map { it.toDomain() }
    )
}