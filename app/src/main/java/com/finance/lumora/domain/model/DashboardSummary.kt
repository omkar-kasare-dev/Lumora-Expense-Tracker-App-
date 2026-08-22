package com.finance.lumora.domain.model


data class DashboardSummary(

    val financial: DashboardFinancialSummary,

    val statistics: DashboardStatistics,

    val category: DashboardCategorySummary,

    val recentTransactions: List<TransactionWithCategory>

)