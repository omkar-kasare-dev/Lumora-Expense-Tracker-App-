package com.finance.lumora.data.repository


import com.finance.lumora.data.local.dao.ExpenseDao
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

/**
 * Repository implementation for Expense operations.
 *
 * Converts Room entities to Domain models and vice versa.
 */
class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {

        return expenseDao
            .getAllExpenses()
            .map { it.toDomainList() }
    }

    override fun getExpensesByCategory(
        categoryId: Long
    ): Flow<List<Expense>> {

        return expenseDao
            .getExpensesByCategory(categoryId)
            .map { it.toDomainList() }
    }

    override fun searchExpenses(
        query: String
    ): Flow<List<Expense>> {

        return expenseDao
            .searchExpenses(query)
            .map { it.toDomainList() }
    }

    override suspend fun getExpenseById(
        id: Long
    ): Expense? {

        return expenseDao
            .getExpenseById(id)
            ?.toDomain()
    }

    override suspend fun addExpense(
        expense: Expense
    ) {

        expenseDao.insertExpense(
            expense.toEntity()
        )
    }

    override suspend fun updateExpense(
        expense: Expense
    ) {

        expenseDao.updateExpense(
            expense.toEntity()
        )
    }

    override suspend fun deleteExpense(
        expense: Expense
    ) {

        expenseDao.deleteExpense(
            expense.toEntity()
        )
    }

    override suspend fun deleteExpenseById(
        id: Long
    ) {

        expenseDao.deleteExpenseById(id)
    }

    override suspend fun getTotalExpense(): Double {

        return expenseDao.getTotalExpense()
    }

    override suspend fun getMonthlyExpense(
        month: Int,
        year: Int
    ): Double {

        val startDate = LocalDate
            .of(year, month, 1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val endDate = LocalDate
            .of(year, month, 1)
            .plusMonths(1)
            .minusDays(1)
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return expenseDao.getExpenseBetweenDates(
            startDate,
            endDate
        )
    }
}