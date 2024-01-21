package com.example.shualeduri.data.mapper.wallpaper_details

import com.example.shualeduri.data.model.ImageDto
import com.example.shualeduri.domain.model.GetImage

fun ImageDto.toDomain(): GetImage {
    return GetImage(
        id, pageURL, tags, previewURL, webformatURL, largeImageURL, views, downloads, collections, likes, comments
    )
}