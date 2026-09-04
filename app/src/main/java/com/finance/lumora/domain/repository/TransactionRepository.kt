package com.finance.lumora.domain.repository


import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import com.finance.lumora.domain.model.ExportTransaction

/**
 * Repository contract for Transaction operations.
 */
interface TransactionRepository {

    // -------------------------
    // CRUD
    // -------------------------

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(id: Long)

    // -------------------------
    // READ
    // -------------------------

    fun getAllTransactions(): Flow<List<Transaction>>

    suspend fun getTransactionById(id: Long): Transaction?

    fun getTransactionsByType(
        type: TransactionType
    ): Flow<List<Transaction>>

    fun getTransactionsByCategory(
        categoryId: Long
    ): Flow<List<Transaction>>

    fun getTransactionsBetweenDates(
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>>

    suspend fun getTransactionCount(): Int

    suspend fun getTransactionsForExport(): List<ExportTransaction>

    // -------------------------
    // DASHBOARD
    // -------------------------

    fun getTotalIncome(): Flow<Double>

    fun getTotalExpense(): Flow<Double>
}