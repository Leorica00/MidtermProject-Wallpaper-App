package com.example.shualeduri.domain.model

data class GetImage(
    val id: Long,
    val pageURL: String,
    val tags: String,
    val previewURL: String,
    val webformatURL: String,
    val largeImageURL: String,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
)