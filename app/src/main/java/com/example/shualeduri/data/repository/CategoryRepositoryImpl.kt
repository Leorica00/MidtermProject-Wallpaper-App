package com.example.shualeduri.data.repository

import com.example.shualeduri.presentation.model.category.Categories
import com.example.shualeduri.presentation.model.category.Category
import com.example.shualeduri.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return Categories.values().map { category ->
            Category(categories = category)
        }
    }
}