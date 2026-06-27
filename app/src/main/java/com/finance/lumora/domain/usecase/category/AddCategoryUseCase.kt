package com.finance.lumora.domain.usecase.category

import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) {

        require(category.name.isNotBlank()) {
            "Category name cannot be empty."
        }

        require(category.icon.isNotBlank()) {
            "Category icon cannot be empty."
        }

        repository.addCategory(category)
    }
}