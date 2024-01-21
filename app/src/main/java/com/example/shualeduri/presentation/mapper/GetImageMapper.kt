package com.example.shualeduri.presentation.mapper

import com.example.shualeduri.domain.model.GetImage
import com.example.shualeduri.presentation.model.Image

fun GetImage.toPresenter(): Image = Image(
    id, pageURL, tags, previewURL, webformatURL, largeImageURL, views, downloads, collections, likes, comments
)