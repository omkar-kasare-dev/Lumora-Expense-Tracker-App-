package com.finance.lumora.presentation.ai.viewmodel
/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.usecase.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionEditViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories =
        MutableStateFlow<List<Category>>(emptyList())

    val categories: StateFlow<List<Category>> =
        _categories.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {

        viewModelScope.launch {

            getCategoriesUseCase()
                .collect { categories ->

                    _categories.value =
                        categories
                }
        }
    }
}

 */



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.usecase.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the Transaction Editing flow.
 */
data class TransactionEditUiState(
    val categories: List<Category> = emptyList(),
    val isLoadingCategories: Boolean = false,
    val categoryError: String? = null
)

@HiltViewModel
class TransactionEditViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionEditUiState())
    val uiState: StateFlow<TransactionEditUiState> = _uiState.asStateFlow()

    // Backward compatibility property if your composable directly observes `viewModel.categories`
    val categories: StateFlow<List<Category>> = MutableStateFlow<List<Category>>(emptyList()).apply {
        viewModelScope.launch {
            uiState.collect { value = it.categories }
        }
    }

    init {
        loadCategories()
    }

    /**
     * Observes available categories from the database repository via [GetCategoriesUseCase].
     */
    fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingCategories = true,
                            categoryError = null
                        )
                    }
                }
                .catch { throwable ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingCategories = false,
                            categoryError = throwable.message ?: "Failed to load categories."
                        )
                    }
                }
                .collect { categoryList ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            categories = categoryList,
                            isLoadingCategories = false,
                            categoryError = null
                        )
                    }
                }
        }
    }
}