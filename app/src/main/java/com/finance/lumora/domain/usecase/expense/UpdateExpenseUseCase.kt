package com.finance.lumora.domain.usecase.expense


import com.finance.lumora.core.exception.ExpenseValidationException
import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {

        if (expense.id <= 0) {
            throw ExpenseValidationException(
                "Invalid expense id."
            )
        }

        if (expense.title.isBlank()) {
            throw ExpenseValidationException(
                "Expense title cannot be empty."
            )
        }

        if (expense.amount <= 0) {
            throw ExpenseValidationException(
                "Amount must be greater than zero."
            )
        }

        repository.updateExpense(expense)
    }
}