package com.example.shualeduri.presentation.state.wallpaper_details

import com.example.shualeduri.presentation.model.Image

data class WallpaperDetailsState(
    val data: Image? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
