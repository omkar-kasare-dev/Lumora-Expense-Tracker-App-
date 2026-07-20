package com.finance.lumora.domain.model



/**
 * Financial information displayed
 * on Dashboard.
 */
data class DashboardFinancialSummary(

    val totalIncome: Double,

    val totalExpense: Double,

    val totalBalance: Double,

    val monthlyIncome: Double,

    val monthlyExpense: Double

)