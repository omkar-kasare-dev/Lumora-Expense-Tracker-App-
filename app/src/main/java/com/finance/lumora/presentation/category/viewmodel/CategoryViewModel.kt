package com.finance.lumora.presentation.category.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.usecase.category.CategoryUseCases
import com.finance.lumora.presentation.category.state.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing Category UI state
 * and coordinating Category-related business logic.
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())

   // val uiState: StateFlow<CategoryUiState> =
      //  _uiState.asStateFlow()

    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    /**
     * Loads all categories from Room.
     */
    private fun loadCategories() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            categoryUseCases
                .getCategories()
                .catch { exception ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                    }
                }
                .collect { categories ->

                    _uiState.update {
                        it.copy(
                            categories = categories,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    /**
     * Adds a new category.
     */
    fun addCategory(category: Category) {

        viewModelScope.launch {

            try {

                categoryUseCases.addCategory(category)

                hideAddDialog()

            } catch (exception: Exception) {

                _uiState.update {

                    it.copy(
                        errorMessage = exception.message
                    )

                }

            }

        }

    }

    /**
     * Updates an existing category.
     */
    fun updateCategory(category: Category) {

        viewModelScope.launch {

            try {

                categoryUseCases.updateCategory(category)

            } catch (exception: Exception) {

                _uiState.update {
                    it.copy(
                        errorMessage = exception.message
                    )
                }
            }
        }
    }

    /**
     * Deletes a category.
     */
    /**
     * Deletes a category.
     */
    fun deleteCategory(category: Category) {

        viewModelScope.launch {

            try {

                categoryUseCases.deleteCategory(category)

                // Close the dialog after successful deletion
                hideDeleteDialog()

            } catch (exception: Exception) {

                _uiState.update {

                    it.copy(
                        errorMessage = exception.message
                    )

                }

            }

        }

    }

    /**
     * Clears the currently displayed error.
     */
    fun clearError() {

        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    // Category Dialouge
    fun showAddDialog() {

        _uiState.update {

            it.copy(
                showAddDialog = true
            )

        }

    }

    fun hideAddDialog() {

        _uiState.update {

            it.copy(
                showAddDialog = false
            )

        }

    }

    /**
     * Closes the Edit Category dialog and clears
     * the selected category.
     */
    fun hideEditDialog() {

        _uiState.update {

            it.copy(
                showEditDialog = false,
                selectedCategory = null
            )

        }

    }

    /**
     * Opens the Edit Category dialog and stores
     * the selected category.
     */
    fun showEditDialog(category: Category) {

        _uiState.update {

            it.copy(
                selectedCategory = category,
                showEditDialog = true
            )

        }

    }

    /**
     * Opens Delete Confirmation Dialog.
     */
    fun showDeleteDialog(category: Category) {

        _uiState.update {

            it.copy(
                showDeleteDialog = true,
                categoryToDelete = category
            )

        }

    }

    /**
     * Hides Delete Confirmation Dialog.
     */
    fun hideDeleteDialog() {

        _uiState.update {

            it.copy(
                showDeleteDialog = false,
                categoryToDelete = null
            )

        }

    }
}