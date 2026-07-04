package com.finance.lumora.presentation.category.state

import com.finance.lumora.domain.model.Category

/**
 * Represents the complete UI state of the Category screen.
 */
data class CategoryUiState(

    /**
     * Categories displayed on the screen.
     */
    val categories: List<Category> = emptyList(),

    /**
     * Indicates whether data is currently loading.
     */
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