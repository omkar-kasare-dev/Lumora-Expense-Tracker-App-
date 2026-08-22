package com.finance.lumora.domain.repository


import com.finance.lumora.domain.model.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for expense operations.
 */
interface ExpenseRepository {

    fun getAllExpenses(): Flow<List<Expense>>

    fun getExpensesByCategory(categoryId: Long): Flow<List<Expense>>

    fun searchExpenses(query: String): Flow<List<Expense>>

    suspend fun getExpenseById(id: Long): Expense?

    suspend fun addExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun deleteExpenseById(id: Long)

    suspend fun getTotalExpense(): Double

    suspend fun getMonthlyExpense(
        month: Int,
        year: Int
    ): Double
}