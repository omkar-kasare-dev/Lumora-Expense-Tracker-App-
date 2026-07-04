package com.finance.lumora.di


import com.finance.lumora.domain.usecase.category.AddCategoryUseCase
import com.finance.lumora.domain.usecase.category.CategoryUseCases
import com.finance.lumora.domain.usecase.category.DeleteCategoryUseCase
import com.finance.lumora.domain.usecase.category.GetCategoriesUseCase
import com.finance.lumora.domain.usecase.category.UpdateCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCategoryUseCases(
        addCategory: AddCategoryUseCase,
        updateCategory: UpdateCategoryUseCase,
        deleteCategory: DeleteCategoryUseCase,
        getCategories: GetCategoriesUseCase
    ): CategoryUseCases {

        return CategoryUseCases(
            addCategory = addCategory,
            updateCategory = updateCategory,
            deleteCategory = deleteCategory,
            getCategories = getCategories
        )
    }
}