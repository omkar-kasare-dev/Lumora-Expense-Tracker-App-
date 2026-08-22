package com.finance.lumora.presentation.category.state

import com.finance.lumora.domain.model.Category

data class CategoryUiState(

    val categories: List<Category> = emptyList(),

    val isLoading: Boolean = false,

    /**
     * Error message to display.
     * Null means no error.
     */
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,

    val selectedCategory: Category? = null,

    val showDeleteDialog: Boolean = false,

    val categoryToDelete: Category? = null

)