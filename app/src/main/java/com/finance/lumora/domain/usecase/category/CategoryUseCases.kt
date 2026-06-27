package com.finance.lumora.domain.usecase.category


data class CategoryUseCases(
    val addCategory: AddCategoryUseCase,
    val updateCategory: UpdateCategoryUseCase,
    val deleteCategory: DeleteCategoryUseCase,
    val getCategories: GetCategoriesUseCase
)