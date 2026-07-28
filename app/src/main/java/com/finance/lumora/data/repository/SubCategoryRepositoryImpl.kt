package com.finance.lumora.data.repository


import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.domain.repository.SubCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository implementation for SubCategory operations.
 *
 * Converts Room entities to Domain models and vice versa.
 */
class SubCategoryRepositoryImpl @Inject constructor(
    private val subCategoryDao: SubCategoryDao
) : SubCategoryRepository {

    /**
     * Returns all subcategories belonging to a category.
     */
    override fun getSubCategoriesByCategoryId(
        categoryId: Long
    ): Flow<List<SubCategory>> {

        return subCategoryDao
            .getSubCategoriesByCategoryId(categoryId)
            .map { it.toDomainList() }
    }

    /**
     * Adds a new subcategory.
     */
    override suspend fun addSubCategory(
        subCategory: SubCategory
    ) {

        subCategoryDao.insertSubCategory(
            subCategory.toEntity()
        )
    }

    /**
     * Returns a subcategory by its name within a category.
     */
    override suspend fun getSubCategoryByName(
        categoryId: Long,
        name: String
    ): SubCategory? {

        return subCategoryDao
            .getSubCategoryByName(
                categoryId = categoryId,
                name = name
            )
            ?.toDomain()
    }
}