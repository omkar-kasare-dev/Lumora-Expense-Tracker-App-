package com.finance.lumora.domain.usecase.expense


import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {

        require(expense.title.isNotBlank()) {
            "Expense title cannot be empty."
        }

        require(expense.amount > 0) {
            "Expense amount must be greater than zero."
        }

        repository.addExpense(expense)
    }
}