package com.finance.lumora.domain.model

/*

/**
 * Aggregated dashboard data.
 * This model contains all information required
 * to render the Dashboard screen.
 */
data class DashboardSummary(

    // Financial Summary
    // ----------------------------------------------------

    /**
     * Total balance.
     *
     * Formula:
     * Income - Expense
     */
    val totalBalance: Double = 0.0,

    /**
     * Total income.
     */
    val totalIncome: Double = 0.0,

    /**
     * Total expense.
     */
    val totalExpense: Double = 0.0,

    // ----------------------------------------------------
    // Monthly Summary
    // ----------------------------------------------------

    /**
     * Current month income.
     */
    val monthlyIncome: Double = 0.0,

    /**
     * Current month expense.
     */
    val monthlyExpense: Double = 0.0,

    /**
     * Current month savings.
     */
    //val monthlySavings: Double = 0.0,

    // ----------------------------------------------------
    // Statistics
    // ----------------------------------------------------

    /**
     * Total transactions.
     */
    val transactionCount: Int = 0,

    /**
     * Largest expense.
     */
    val largestExpense: Double = 0.0,

    /**
     * Largest income.
     */
    val largestIncome: Double = 0.0,

    // ----------------------------------------------------
    // Category Analytics
    // ----------------------------------------------------

    /**
     * Top spending category.
     */
    /*
    val topExpenseCategory: Category? = null,

    /**
     * Top category amount.
     */
    val topExpenseAmount: Double = 0.0,

     */

    val topExpenseCategory: TopExpenseCategory? = null,

    // ----------------------------------------------------
    // Recent Transactions
    // ----------------------------------------------------

    /**
     * Recent transactions shown on dashboard.
     */
    //val recentTransactions: List<Transaction> = emptyList()
    val recentTransactions: List<TransactionWithCategory> = emptyList()
)



data class DashboardSummary(

    val totalBalance: Double,

    val totalIncome: Double,

    val totalExpense: Double,

    val monthlyIncome: Double,

    val monthlyExpense: Double,

    val transactionCount: Int,

    val largestIncome: Double,

    val largestExpense: Double,

    val topExpenseCategory: TopExpenseCategory?,

    val recentTransactions:
    List<TransactionWithCategory>

)

 */

data class DashboardSummary(

    val financial: DashboardFinancialSummary,

    val statistics: DashboardStatistics,

    val category: DashboardCategorySummary,

    val recentTransactions: List<TransactionWithCategory>

)