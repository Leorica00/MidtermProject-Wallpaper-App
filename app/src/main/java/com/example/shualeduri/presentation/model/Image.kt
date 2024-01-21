package com.example.shualeduri.presentation.model

data class Image(
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