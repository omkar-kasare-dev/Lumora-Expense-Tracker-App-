package com.finance.lumora.presentation.transaction.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.transaction.TransactionUseCases
import com.finance.lumora.presentation.transaction.effect.TransactionUiEffect
import com.finance.lumora.presentation.transaction.event.TransactionEvent
import com.finance.lumora.presentation.transaction.state.TransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.SubCategory
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.usecase.category.CategoryUseCases
import com.finance.lumora.domain.usecase.subcategory.SubCategoryUseCases
import com.finance.lumora.domain.validation.ValidationResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

/**
 * ViewModel responsible for handling
 * Transaction UI state and user interactions.
 */
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val subCategoryUseCases: SubCategoryUseCases
) : ViewModel() {

    // UI State
    private val _state = MutableStateFlow(
        TransactionState()
    )

    val state: StateFlow<TransactionState> =
        _state.asStateFlow()

    // ----------------------------------------------------
    // One-Time UI Effects
    // ----------------------------------------------------

    private val _uiEffect =
        MutableSharedFlow<TransactionUiEffect>()

    val uiEffect: SharedFlow<TransactionUiEffect> =
        _uiEffect.asSharedFlow()

    private var loadSubCategoriesJob: Job? = null

    // ----------------------------------------------------
    // Initialization
    // ----------------------------------------------------

    init {
        initialize()
    }

    // Performs initial setup for the screen.
    // ----------------------------------------------------
    private fun initialize() {

        loadCategories()

        loadTransactions()

        loadDashboard()
    }

    // Event Dispatcher
    // ----------------------------------------------------
    /**
     * Receives all user actions from the UI.
     */
    fun onEvent(
        event: TransactionEvent
    ) {

        when (event) {

            // ----------------------------------------------------
            // Input
            // ----------------------------------------------------

            is TransactionEvent.AmountChanged -> {
                updateAmount(event.amount)
            }

            is TransactionEvent.NoteChanged -> {
                updateNote(event.note)
            }

            is TransactionEvent.CategoryChanged -> {
                updateCategory(event.category)
                // Subcategory Loaders calling.
                loadSubCategories(
                    event.category.id
                )
            }
//==
            is TransactionEvent.TypeChanged -> {
                updateTransactionType(event.type)
            }

            is TransactionEvent.DateChanged -> {
                updateDate(event.date)
            }

            // ----------------------------------------------------
            // CRUD
            // ----------------------------------------------------

            TransactionEvent.SaveTransaction -> {
                saveTransaction()
            }
/*
            is TransactionEvent.EditTransaction -> {
                editTransaction(event.transactionId)
            }

            is TransactionEvent.DeleteTransaction -> {
                deleteTransaction(event.transactionId)
            }

 */
            // Transaction crud:
            is TransactionEvent.EditTransaction -> {

                editTransaction(
                    event.transaction
                )

            }
/*
            is TransactionEvent.DeleteTransaction -> {

                deleteTransaction(
                    event.transaction
                )

            }

 */
            is TransactionEvent.ShowDeleteDialog -> {

                showDeleteDialog(
                    event.transaction
                )

            }

            TransactionEvent.DismissDeleteDialog -> {

                dismissDeleteDialog()

            }

            TransactionEvent.ConfirmDeleteTransaction -> {

                confirmDeleteTransaction()

            }

            // ----------------------------------------------------
            // Search
            // ----------------------------------------------------

            is TransactionEvent.SearchQueryChanged -> {
                updateSearchQuery(event.query)
            }

            // ----------------------------------------------------
            // Filters
            // ----------------------------------------------------

            is TransactionEvent.CategoryFilterChanged -> {
                updateCategoryFilter(event.category)
            }

            is TransactionEvent.TypeFilterChanged -> {
                updateTypeFilter(event.type)
            }

            TransactionEvent.ClearFilters -> {
                clearFilters()
            }

            // ----------------------------------------------------
            // Refresh
            // ----------------------------------------------------

            TransactionEvent.Refresh -> {
                refresh()
            }

            // ----------------------------------------------------
            // Initial Loading
            // ----------------------------------------------------

            TransactionEvent.LoadCategories -> {
                loadCategories()
            }

            TransactionEvent.LoadTransactions -> {
                loadTransactions()
            }
            // Custom category Events:

            is TransactionEvent.ShowAddCategoryDialog -> {
                showAddCategoryDialog()
            }

            is TransactionEvent.DismissAddCategoryDialog -> {
                dismissAddCategoryDialog()
            }

            is TransactionEvent.SaveCustomCategory -> {
                saveCustomCategory(event.category)

            }
            // Custom category Events: END

            // Subcategory event section :Start:
            is TransactionEvent.SubCategoryChanged -> {

                _state.update {

                    it.copy(
                        selectedSubCategory = event.subCategory
                    )

                }

            }

            TransactionEvent.ShowAddSubCategoryDialog -> {

                _state.update {

                    it.copy(
                        showAddSubCategoryDialog = true
                    )

                }

            }

            TransactionEvent.DismissAddSubCategoryDialog -> {

                _state.update {

                    it.copy(
                        showAddSubCategoryDialog = false
                    )

                }

            }

            is TransactionEvent.SaveCustomSubCategory -> {

                saveCustomSubCategory(
                    event.subCategory
                )

            }

            // Subcategory event section : END

            else -> {}
        }
    }


// Input Handlers


// Input Handlers
// ----------------------------------------------------

    /**
     * Updates the entered amount.
     */
    private fun updateAmount(
        amount: String
    ) {
        _state.update { currentState ->
            currentState.copy(
                amount = amount
            )
        }
    }

    /**
     * Updates the transaction note.
     */
    private fun updateNote(
        note: String
    ) {
        _state.update { currentState ->
            currentState.copy(
                note = note
            )
        }
    }

    /**
     * Updates the selected category.
     */
    private fun updateCategory(
        category: Category
    ) {
        _state.update { currentState ->
            currentState.copy(
                selectedCategory = category
            )
        }
    }

    /**
     * Updates the transaction type.
     */
    private fun updateTransactionType(
        type: TransactionType
    ) {
        _state.update { currentState ->
            currentState.copy(
                transactionType = type
            )
        }
    }

    /**
     * Updates the selected transaction date.
     */
    private fun updateDate(
        date: Long
    ) {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = date
            )
        }
    }

    // input Handler End:
// CRUD Operations


     //Saves a new transaction.
    /**
     * Saves a new transaction or updates an existing one.
     */
    private fun saveTransaction() {

        viewModelScope.launch {

            val currentState = state.value

            val category = currentState.selectedCategory

            if (category == null) {

                _uiEffect.emit(
                    TransactionUiEffect.ShowSnackbar(
                        "Please select a category."
                    )
                )

                return@launch
            }

            val amount = currentState.amount.toDoubleOrNull()

            if (amount == null) {

                _uiEffect.emit(
                    TransactionUiEffect.ShowSnackbar(
                        "Please enter a valid amount."
                    )
                )

                return@launch
            }

            val transaction = Transaction(
                id = currentState.editingTransactionId ?: 0L,
                amount = amount,
                type = currentState.transactionType,
                categoryId = category.id,
                note = currentState.note.ifBlank { null },
                transactionDate = currentState.selectedDate,
                updatedAt = System.currentTimeMillis()
            )

            val result = if (currentState.isEditMode) {

                transactionUseCases.updateTransaction(transaction)

            } else {

                transactionUseCases.addTransaction(transaction)

            }

            when (result) {

                ValidationResult.Success -> {

                    _uiEffect.emit(
                        TransactionUiEffect.ShowSnackbar(
                            if (currentState.isEditMode)
                                "Transaction updated successfully."
                            else
                                "Transaction saved successfully."
                        )
                    )

                    resetForm()

                    _uiEffect.emit(
                        TransactionUiEffect.NavigateBack
                    )
                }

                is ValidationResult.Error -> {

                    _uiEffect.emit(
                        TransactionUiEffect.ShowSnackbar(
                            result.message
                        )
                    )
                }
            }
        }
    }

    // save Transaction End:

    // Edit Transaction
    /*
    private fun editTransaction(
        transactionId: Long
    ) {

        viewModelScope.launch {

            val transaction = transactionUseCases
                .getTransactionById(transactionId)
                ?: return@launch

            val category = state.value.categories
                .firstOrNull {
                    it.id == transaction.categoryId
                }

            _state.update { currentState ->

                currentState.copy(

                    amount = transaction.amount.toString(),

                    note = transaction.note.orEmpty(),

                    selectedCategory = category,

                    transactionType = transaction.type,

                    selectedDate = transaction.transactionDate,

                    editingTransactionId = transaction.id,

                    isEditMode = true

                )
            }
        }
    }

    // Edit Transaction End:

    /**
     * Deletes a transaction.
     */
    private fun deleteTransaction(
        transactionId: Long
    )

    {

        viewModelScope.launch {

            val transaction = transactionUseCases
                .getTransactionById(transactionId)
                ?: return@launch

            transactionUseCases.deleteTransaction(transaction)

            _uiEffect.emit(
                TransactionUiEffect.ShowSnackbar(
                    "Transaction deleted successfully."
                )
            )

            if (state.value.editingTransactionId == transactionId) {
                resetForm()
            }
        }
    }

    //--------------------------

     */

    /**
     * Loads the selected transaction into the form
     * so the user can edit it.
     */
    /**
     * Loads the selected transaction into the form
     * so the user can edit it.
     */
    private fun editTransaction(
        transaction: Transaction
    ) {

        val category = state.value.categories.firstOrNull {

            it.id == transaction.categoryId

        }

        _state.update { currentState ->

            currentState.copy(

                amount = transaction.amount.toString(),

                note = transaction.note.orEmpty(),

                selectedCategory = category,

                transactionType = transaction.type,

                selectedDate = transaction.transactionDate,

                editingTransactionId = transaction.id,

                isEditMode = true

            )

        }

    }

    /**
     * Deletes the selected transaction.
     */
    /**
     * Deletes the selected transaction.
     */
    private fun deleteTransaction(
        transaction: Transaction
    ) {

        viewModelScope.launch {

            transactionUseCases.deleteTransaction(
                transaction
            )

            _uiEffect.emit(
                TransactionUiEffect.ShowSnackbar(
                    "Transaction deleted successfully."
                )
            )

            if (
                state.value.editingTransactionId == transaction.id
            ) {

                resetForm()

            }

        }

    }

    /**
     * Resets the transaction form to its default state.
     */
    private fun resetForm() {

        _state.update { currentState ->

            currentState.copy(

                amount = "",

                note = "",

                selectedCategory = null,

                transactionType = TransactionType.EXPENSE,

                selectedDate = System.currentTimeMillis(),

                editingTransactionId = null,

                isEditMode = false
            )
        }
    }

// Loading
// load categories start
    private fun loadCategories() {

        viewModelScope.launch {

            categoryUseCases
                .getCategories()
                .collectLatest { categories ->

                    Log.d(
                        "LUMORA_CATEGORY",
                        "Loaded categories = ${categories.size}"
                    )

                    _state.update { currentState ->
                        currentState.copy(
                            categories = categories
                        )
                    }

                }
        }
    }
    // load categories End

    // load Transaction start
    private fun loadTransactions() {

        viewModelScope.launch {

            transactionUseCases
                .getAllTransactions()
                .collectLatest { transactions ->

                    _state.update { currentState ->
                        currentState.copy(
                            transactions = transactions
                        )
                    }

                }
        }
    }
    // load Transaction End:

    // load Dashboard start

    private fun loadDashboard() {

        viewModelScope.launch {

            transactionUseCases
                .getTotalIncome()
                .collectLatest { income ->

                    _state.update { currentState ->
                        currentState.copy(
                            totalIncome = income
                        )
                    }

                }
        }

        viewModelScope.launch {

            transactionUseCases
                .getTotalExpense()
                .collectLatest { expense ->

                    _state.update { currentState ->
                        currentState.copy(
                            totalExpense = expense
                        )
                    }

                }
        }
    }

    // load Dashboard End:

//-------------------------------------------------------------

    // refresh Start:
    private fun refresh() {

        _state.update {
            it.copy(
                isRefreshing = true
            )
        }

        loadCategories()

        loadTransactions()

        loadDashboard()

        _state.update {
            it.copy(
                isRefreshing = false
            )
        }
    }
    // refresh End: :

    //---------------------------------------------

// Search & Filters

    private fun updateSearchQuery(
        query: String
    ) {

        // TODO: Implement in Phase 4.3.4.8

    }

    private fun updateCategoryFilter(
        category: Category?
    ) {

        // TODO: Implement in Phase 4.3.4.8

    }

    private fun updateTypeFilter(
        type: TransactionType?
    ) {

        // TODO: Implement in Phase 4.3.4.8

    }


    private fun clearFilters() {

        // TODO: Implement in Phase 4.3.4.8

    }

    // Delete Dialog helper Methods:

    private fun showDeleteDialog(
        transaction: Transaction
    ) {

        _state.update {

            it.copy(

                showDeleteDialog = true,

                transactionToDelete = transaction

            )

        }

    }

    //---------------------------------------
    private fun dismissDeleteDialog() {

        _state.update {

            it.copy(

                showDeleteDialog = false,

                transactionToDelete = null

            )

        }

    }

    //___________________________________________

    private fun confirmDeleteTransaction() {

        val transaction = state.value.transactionToDelete
            ?: return

        viewModelScope.launch {

            transactionUseCases.deleteTransaction(
                transaction
            )

            _uiEffect.emit(

                TransactionUiEffect.ShowSnackbar(
                    "Transaction deleted successfully."
                )

            )

            if (
                state.value.editingTransactionId == transaction.id
            ) {

                resetForm()

            }

            _state.update {

                it.copy(

                    showDeleteDialog = false,

                    transactionToDelete = null

                )

            }

        }

    }
    /**
     * Add custom category helper function:START
     */
    private fun showAddCategoryDialog() {

        _state.update {
            it.copy(
                showAddCategoryDialog = true
            )
        }
        Log.d("CATEGORY_DIALOG", "Dialog State = true")
    }

    private fun dismissAddCategoryDialog() {

        _state.update {
            it.copy(
                showAddCategoryDialog = false
            )
        }
    }

    private fun saveCustomCategory(category: Category) {

        Log.d("CATEGORY_SAVE", "Saving ${category.name}")

        viewModelScope.launch {

            try {

                categoryUseCases.addCategory(
                    category.copy(
                        isDefault = false
                    )
                )

                _state.update {
                    it.copy(
                        showAddCategoryDialog = false,
                        selectedCategory = category
                    )
                }

                _uiEffect.emit(
                    TransactionUiEffect.ShowSnackbar(
                        "Category added successfully."
                    )
                )

                Log.d("CATEGORY_SAVE", "Category Saved Successfully")

            } catch (e: Exception) {

                Log.e(
                    "CATEGORY_SAVE",
                    "Save Failed",
                    e
                )

                _uiEffect.emit(
                    TransactionUiEffect.ShowSnackbar(
                        e.message ?: "Unable to save category."
                    )
                )
            }
        }
    }

    //* Add custom category helper function: END ------------------------------------------------------

    /*
    ** LoadSubCategories Helper Function:
     */
    private fun loadSubCategories(
        categoryId: Long
    ) {

        loadSubCategoriesJob?.cancel()

        loadSubCategoriesJob = viewModelScope.launch {

            subCategoryUseCases
                .getSubCategories(categoryId)
                .collect { subCategories ->

                    _state.update {

                        it.copy(
                            subCategories = subCategories,
                            selectedSubCategory = null
                        )

                    }

                }

        }

    }
/*
* Save Custom Sub Category:
 */
    private fun saveCustomSubCategory(
        subCategory: SubCategory
    ) {

        viewModelScope.launch {

            try {

                val selectedCategory =
                    _state.value.selectedCategory

                require(selectedCategory != null) {
                    "Please select a category first."
                }

                val newSubCategory = subCategory.copy(
                    categoryId = selectedCategory.id
                )


                subCategoryUseCases
                    .addSubCategory(newSubCategory)

                _state.update {

                    it.copy(
                        selectedSubCategory = newSubCategory,
                        showAddSubCategoryDialog = false
                    )

                }

            } catch (e: Exception) {

                Log.e(
                    "SUBCATEGORY_SAVE",
                    "Failed to save subcategory",
                    e
                )

            }

        }

    }

    // Dialog Functions:
}