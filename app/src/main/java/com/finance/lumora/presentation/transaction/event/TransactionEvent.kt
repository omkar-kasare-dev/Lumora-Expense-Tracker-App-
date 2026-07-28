package com.finance.lumora.presentation.transaction.event



import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.domain.model.Transaction

/**
 * Represents all user actions that can occur
 * on Transaction screens.
 */
sealed interface TransactionEvent {

    // --------------------------------------------------
    // Input Fields
    // --------------------------------------------------

    data class AmountChanged(
        val amount: String
    ) : TransactionEvent

    data class NoteChanged(
        val note: String
    ) : TransactionEvent

    data class CategoryChanged(
        val category: Category
    ) : TransactionEvent

    data class TransactionTypeChanged(
        val type: TransactionType
    ) : TransactionEvent

    data class DateChanged(
        val date: Long
    ) : TransactionEvent

    data class TypeChanged(
        val type: TransactionType
    ) : TransactionEvent

    // --------------------------------------------------
    // CRUD
    // --------------------------------------------------

    data object SaveTransaction : TransactionEvent
/*
    data class EditTransaction(
        val transactionId: Long
    ) : TransactionEvent

    data class DeleteTransaction(
        val transactionId: Long
    ) : TransactionEvent


 */

    data class EditTransaction(
        val transaction: Transaction
    ) : TransactionEvent
/*
    data class DeleteTransaction(
        val transaction: Transaction
    ) : TransactionEvent


 */

    /**
     * Opens the delete confirmation dialog.
     */
    data class ShowDeleteDialog(
        val transaction: Transaction
    ) : TransactionEvent

    /**
     * Hides the delete confirmation dialog.
     */
    data object DismissDeleteDialog : TransactionEvent

    /**
     * User confirmed deletion.
     */
    data object ConfirmDeleteTransaction : TransactionEvent

    // --------------------------------------------------
    // Search
    // --------------------------------------------------

    data class SearchQueryChanged(
        val query: String
    ) : TransactionEvent

    // --------------------------------------------------
    // Filters
    // --------------------------------------------------

    data class CategoryFilterChanged(
        val category: Category?
    ) : TransactionEvent

    data class TypeFilterChanged(
        val type: TransactionType?
    ) : TransactionEvent

    data object ClearFilters : TransactionEvent

    // --------------------------------------------------
    // Refresh
    // --------------------------------------------------

    data object Refresh : TransactionEvent

    // --------------------------------------------------
    // Screen Lifecycle
    // --------------------------------------------------

    data object LoadTransactions : TransactionEvent

    data object LoadCategories : TransactionEvent

    // Category Events:
    // ------------------------------------------------------
// Category Dialog
// ------------------------------------------------------

    object ShowAddCategoryDialog : TransactionEvent

    object DismissAddCategoryDialog : TransactionEvent

    data class SaveCustomCategory(
        val category: Category
    ) : TransactionEvent

    //----------------------------------

    /**
     * User selected a subcategory.
     */
    data class SubCategoryChanged(
        val subCategory: SubCategory
    ) : TransactionEvent

    /**
     * Opens the Add SubCategory dialog.
     */
    data object ShowAddSubCategoryDialog : TransactionEvent

    /**
     * Closes the Add SubCategory dialog.
     */
    data object DismissAddSubCategoryDialog : TransactionEvent

    /**
     * Saves a custom subcategory.
     */
    data class SaveCustomSubCategory(
        val subCategory: SubCategory
    ) : TransactionEvent
}