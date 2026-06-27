package com.finance.lumora.domain.usecase.category


import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) {
        repository.deleteCategory(category)
    }

    suspend fun deleteById(id: Long) {
        repository.deleteCategoryById(id)
    }
}