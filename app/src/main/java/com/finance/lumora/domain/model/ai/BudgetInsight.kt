package com.finance.lumora.domain.model.ai


data class BudgetInsight(
    val monthlyBudget: Double,
    val currentExpense: Double,
    val remainingBudget: Double,
    val budgetUsagePercentage: Double,
    val isBudgetExceeded: Boolean
)