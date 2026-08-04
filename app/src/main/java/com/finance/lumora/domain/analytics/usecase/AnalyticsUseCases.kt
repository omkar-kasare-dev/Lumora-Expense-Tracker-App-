package com.finance.lumora.domain.analytics.usecase

data class AnalyticsUseCases(

    val getMonthlySummary: GetMonthlySummaryUseCase,

    val getCategorySummary: GetCategorySummaryUseCase,

    val getIncomeExpenseSummary: GetIncomeExpenseSummaryUseCase
)