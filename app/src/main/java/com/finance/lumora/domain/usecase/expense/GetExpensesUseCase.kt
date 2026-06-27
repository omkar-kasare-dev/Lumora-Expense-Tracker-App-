package com.finance.lumora.domain.usecase.expense


import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpenses()
    }

    fun byCategory(categoryId: Long): Flow<List<Expense>> {
        return repository.getExpensesByCategory(categoryId)
    }
}