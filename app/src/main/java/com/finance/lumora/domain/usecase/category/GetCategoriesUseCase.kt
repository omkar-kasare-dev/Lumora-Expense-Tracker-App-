package com.finance.lumora.domain.usecase.category


import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}