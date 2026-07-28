package com.finance.lumora.domain.usecase.subcategory



/**
 * Container class for all SubCategory use cases.
 *
 * Inject this class into ViewModels instead of injecting
 * each use case individually.
 */
data class SubCategoryUseCases(

    val addSubCategory: AddSubCategoryUseCase,

    val getSubCategories: GetSubCategoriesUseCase

)