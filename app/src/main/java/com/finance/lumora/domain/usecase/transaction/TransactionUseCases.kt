package com.finance.lumora.domain.usecase.transaction


data class TransactionUseCases(

    val addTransaction: AddTransactionUseCase,

    val updateTransaction: UpdateTransactionUseCase,

    val deleteTransaction: DeleteTransactionUseCase,

    val deleteTransactionById: DeleteTransactionByIdUseCase,

    val getAllTransactions: GetAllTransactionsUseCase,

    val getTransactionById: GetTransactionByIdUseCase,

    val getTransactionsByType: GetTransactionsByTypeUseCase,

    val getTransactionsByCategory: GetTransactionsByCategoryUseCase,

    val getTransactionsBetweenDates: GetTransactionsBetweenDatesUseCase,

    val getTotalIncome: GetTotalIncomeUseCase,

    val getTotalExpense: GetTotalExpenseUseCase,

    val getTransactionCount: GetTransactionCountUseCase,
    val getMonthlyExpense: GetMonthlyExpenseUseCase
)