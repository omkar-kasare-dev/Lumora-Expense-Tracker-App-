package com.finance.lumora.domain.usecase.expense



import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {
        repository.deleteExpense(expense)
    }

    suspend fun deleteById(id: Long) {
        repository.deleteExpenseById(id)
    }
}