package com.finance.lumora.domain.repository


import com.finance.lumora.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for category operations.
 */
interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Long): Category?

    suspend fun addCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun deleteCategoryById(id: Long)
}