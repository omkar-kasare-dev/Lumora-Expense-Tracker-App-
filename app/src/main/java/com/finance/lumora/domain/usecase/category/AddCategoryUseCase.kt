package com.finance.lumora.domain.usecase.category

import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) {

        val normalizedName = category.name.trim()

        require(normalizedName.isNotBlank()) {
            "Category name cannot be empty."
        }

        require(category.icon.isNotBlank()) {
            "Category icon cannot be empty."
        }

        val existingCategory =
            repository.getCategoryByName(normalizedName)

        require(existingCategory == null) {
            "Category already exists."
        }

        repository.addCategory(
            category.copy(
                name = normalizedName
            )
        )
    }
}