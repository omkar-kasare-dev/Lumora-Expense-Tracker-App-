package com.finance.lumora.domain.usecase.subcategory


import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.domain.repository.SubCategoryRepository
import javax.inject.Inject

/**
 * Adds a new SubCategory after validating business rules.
 */
class AddSubCategoryUseCase @Inject constructor(
    private val repository: SubCategoryRepository
) {

    suspend operator fun invoke(
        subCategory: SubCategory
    ) {

        val normalizedName = subCategory.name.trim()

        require(normalizedName.isNotBlank()) {
            "SubCategory name cannot be empty."
        }

        val existingSubCategory =
            repository.getSubCategoryByName(
                categoryId = subCategory.categoryId,
                name = normalizedName
            )

        require(existingSubCategory == null) {
            "SubCategory already exists."
        }

        repository.addSubCategory(
            subCategory.copy(
                name = normalizedName
            )
        )
    }
}