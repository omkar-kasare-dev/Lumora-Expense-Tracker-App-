package com.finance.lumora.domain.usecase.subcategory


import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.domain.repository.SubCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Returns all subcategories belonging to a selected category.
 */
class GetSubCategoriesUseCase @Inject constructor(
    private val repository: SubCategoryRepository
) {

    operator fun invoke(
        categoryId: Long
    ): Flow<List<SubCategory>> {

        return repository.getSubCategoriesByCategoryId(
            categoryId = categoryId
        )
    }
}