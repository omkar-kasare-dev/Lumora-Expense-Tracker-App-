package com.finance.lumora.data.repository

import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {

        return categoryDao
            .getAllCategories()
            .map { it.toDomainList() }
    }

    override suspend fun getCategoryById(
        id: Long
    ): Category? {

        return categoryDao
            .getCategoryById(id)
            ?.toDomain()
    }

    override suspend fun addCategory(
        category: Category
    ) {

        categoryDao.insertCategory(
            category.toEntity()
        )
    }

    override suspend fun updateCategory(
        category: Category
    ) {

        categoryDao.updateCategory(
            category.toEntity()
        )
    }

    override suspend fun deleteCategory(
        category: Category
    ) {

        categoryDao.deleteCategory(
            category.toEntity()
        )
    }

    override suspend fun deleteCategoryById(
        id: Long
    ) {

        categoryDao.deleteCategoryById(id)
    }
// Get CateGory By Name for when user selects the Default Category it will get The categories by there names:
    override suspend fun getCategoryByName(
        name: String
    ): Category? {

        return categoryDao
            .getCategoryByName(name)
            ?.toDomain()
    }
}