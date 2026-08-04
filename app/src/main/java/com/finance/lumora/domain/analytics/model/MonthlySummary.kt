package com.finance.lumora.domain.analytics.model


data class MonthlySummary(

    val totalIncome: Double = 0.0,

    val totalExpense: Double = 0.0,

    val balance: Double = 0.0,

    val transactionCount: Int = 0
)