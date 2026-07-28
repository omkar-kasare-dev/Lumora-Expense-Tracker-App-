package com.finance.lumora.presentation.transaction.state
/*
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.Transaction

/**
 * Represents the complete UI state for the Transaction module.
 *
 * This state is used by the ViewModel and observed by the UI.
 */
data class TransactionState(

    // ----------------------------------------------------
    // Input Fields
    // ----------------------------------------------------

    val amount: String = "",

    val note: String = "",

    val selectedCategory: Category? = null,

    val transactionType: TransactionType = TransactionType.EXPENSE,

    val selectedDate: Long = System.currentTimeMillis(),

    // ----------------------------------------------------
    // Data
    // ----------------------------------------------------

    val categories: List<Category> = emptyList(),

    val transactions: List<Transaction> = emptyList(),

    // ----------------------------------------------------
    // Dashboard
    // ----------------------------------------------------

    val totalIncome: Double = 0.0,

    val totalExpense: Double = 0.0,

    // ----------------------------------------------------
    // UI
    // ----------------------------------------------------

    val isLoading: Boolean = false,

    val isRefreshing: Boolean = false,

    // ----------------------------------------------------
    // Search / Filter
    // ----------------------------------------------------

    val searchQuery: String = "",

    val selectedCategoryFilter: Category? = null,

    val selectedTypeFilter: TransactionType? = null,

    val editingTransactionId: Long? = null,
    val isEditMode: Boolean = false
)

 */




import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.domain.model.Transaction


data class TransactionState(

    // Input Fields

    val amount: String = "",

    val note: String = "",

    val selectedCategory: Category? = null,

    val transactionType: TransactionType = TransactionType.EXPENSE,

    val selectedDate: Long = System.currentTimeMillis(),


    // Data

    val categories: List<Category> = emptyList(),

    val transactions: List<Transaction> = emptyList(),


    // Dashboard

    val totalIncome: Double = 0.0,

    val totalExpense: Double = 0.0,


    // UI

    val isLoading: Boolean = false,

    val isRefreshing: Boolean = false,


    // Search / Filter

    val searchQuery: String = "",

    val selectedCategoryFilter: Category? = null,

    val selectedTypeFilter: TransactionType? = null,

    //----------------------------------------
    val subCategories: List<SubCategory> = emptyList(),

    val selectedSubCategory: SubCategory? = null,

    val showAddCategoryDialog: Boolean = false,
    //------------------------------------------
    //Default value
    val showCreateCategoryDialog:Boolean= false,


    val showAddSubCategoryDialog: Boolean = false,

    //---------------------------------------


    // Edit

    val editingTransactionId: Long? = null,

    val isEditMode: Boolean = false,

    // Delete Dialog
    // ----------------------------------------------------

    /**
     * Controls visibility of the delete confirmation dialog.
     */
    val showDeleteDialog: Boolean = false,

    /**
     * Transaction selected for deletion.
     */
    val transactionToDelete: Transaction? = null

    //----------------------------------------



//----------------------------------------

)