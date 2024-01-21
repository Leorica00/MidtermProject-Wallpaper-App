package com.example.shualeduri.domain.repository

import com.example.shualeduri.presentation.model.category.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}