package com.finance.lumora.domain.repository

import com.finance.lumora.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepository {
    fun getSubCategoriesByCategoryId(
        categoryId: Long
    ): Flow<List<SubCategory>>

    suspend fun addSubCategory(
        subCategory: SubCategory
    )

    suspend fun getSubCategoryByName(
        categoryId: Long,
        name: String
    ): SubCategory?
}