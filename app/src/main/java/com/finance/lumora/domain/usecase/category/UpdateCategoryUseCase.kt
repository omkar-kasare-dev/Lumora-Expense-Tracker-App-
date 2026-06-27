package com.finance.lumora.domain.usecase.category




import com.finance.lumora.core.exception.CategoryValidationException
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) {

        if (category.name.isBlank()) {
            throw CategoryValidationException(
                "Category name cannot be empty."
            )
        }

        if (category.icon.isBlank()) {
            throw CategoryValidationException(
                "Please select an icon."
            )
        }

        repository.updateCategory(category)
    }
}