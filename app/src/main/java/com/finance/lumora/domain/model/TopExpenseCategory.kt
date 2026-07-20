package com.finance.lumora.domain.model

/**
 * Represents the category
 * with the highest expense.
 */
data class TopExpenseCategory(

    val category: Category,

    val amount: Double

)