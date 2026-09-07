package com.finance.lumora.domain.model.ai

// Carries Result:
data class AurixInsightResult(
    val expenseTrendInsight: ExpenseTrendInsight? = null,
    val budgetInsight: BudgetInsight? = null
)