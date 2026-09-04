package com.finance.lumora.data.repository



import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.finance.lumora.domain.model.ExportTransaction
import com.finance.lumora.data.local.relation.TransactionWithCategory


class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    // Get all transaction for Export:
    override suspend fun getTransactionsForExport(): List<ExportTransaction> {

        return transactionDao
            .getAllTransactionsWithCategoryForExport()
            .map { transactionWithCategory ->

                ExportTransaction(
                    id = transactionWithCategory.transaction.id,
                    amount = transactionWithCategory.transaction.amount,
                    type = transactionWithCategory.transaction.type,
                    categoryName = transactionWithCategory.category.name,
                    note = transactionWithCategory.transaction.note,
                    transactionDate = transactionWithCategory.transaction.transactionDate,
                    createdAt = transactionWithCategory.transaction.createdAt,
                    updatedAt = transactionWithCategory.transaction.updatedAt
                )
            }
    }

    //----------------------------------------

    override suspend fun addTransaction(
        transaction: Transaction
    ) {
        transactionDao.insertTransaction(
            transaction.toEntity()
        )
    }

    override suspend fun updateTransaction(
        transaction: Transaction
    ) {
        transactionDao.updateTransaction(
            transaction.toEntity()
        )
    }

    override suspend fun deleteTransaction(
        transaction: Transaction
    ) {
        transactionDao.deleteTransaction(
            transaction.toEntity()
        )
    }

    override suspend fun deleteTransactionById(
        id: Long
    ) {
        transactionDao.deleteTransactionById(id)
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {

        return transactionDao
            .getAllTransactions()
            .map {
                it.toDomainList()
            }
    }

    override suspend fun getTransactionById(
        id: Long
    ): Transaction? {

        return transactionDao
            .getTransactionById(id)
            ?.toDomain()
    }

    override fun getTransactionsByType(
        type: TransactionType
    ): Flow<List<Transaction>> {

        return transactionDao
            .getTransactionsByType(type)
            .map {
                it.toDomainList()
            }
    }

    override fun getTransactionsByCategory(
        categoryId: Long
    ): Flow<List<Transaction>> {

        return transactionDao
            .getTransactionsByCategory(categoryId)
            .map {
                it.toDomainList()
            }
    }

    override fun getTransactionsBetweenDates(
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> {

        return transactionDao
            .getTransactionsBetweenDates(
                startDate,
                endDate
            )
            .map {
                it.toDomainList()
            }
    }

    override suspend fun getTransactionCount(): Int {

        return transactionDao.getTransactionCount()
    }

    override fun getTotalIncome(): Flow<Double> {

        return transactionDao.getTotalIncome()
    }

    override fun getTotalExpense(): Flow<Double> {

        return transactionDao.getTotalExpense()
    }
}