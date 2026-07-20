package com.finance.lumora.data.local.projection


/**
 * Projection representing the category
 * with the highest total expense.
 *
 * Used only for Dashboard analytics.
 */
data class TopExpenseCategoryProjection(

    /**
     * Category Id.
     */
    val categoryId: Long,

    /**
     * Total amount spent in the category.
     */
    val totalExpense: Double

)