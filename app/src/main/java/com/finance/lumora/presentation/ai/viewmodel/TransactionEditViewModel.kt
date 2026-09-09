package com.finance.lumora.presentation.ai.viewmodel

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