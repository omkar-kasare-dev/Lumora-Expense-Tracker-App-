package com.finance.lumora.domain.usecase.expense



data class ExpenseUseCases(
    val addExpense: AddExpenseUseCase,
    val updateExpense: UpdateExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase,
    val getExpenses: GetExpensesUseCase,
    val searchExpense: SearchExpenseUseCase
)