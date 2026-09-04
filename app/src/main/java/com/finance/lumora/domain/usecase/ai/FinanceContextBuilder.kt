package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.usecase.AnalyticsUseCases
import com.finance.lumora.domain.model.ai.FinanceCategorySummary
import com.finance.lumora.domain.model.ai.FinanceContext
import com.finance.lumora.domain.usecase.settings.GetBudgetUseCase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.first

import com.finance.lumora.domain.repository.SettingsRepository

class FinanceContextBuilder @Inject constructor(
    private val analyticsUseCases: AnalyticsUseCases,
    private val getBudgetUseCase: GetBudgetUseCase,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(): FinanceContext {

        val dateRange = getCurrentMonthDateRange()

        val monthlySummary =
            analyticsUseCases
                .getMonthlySummary(dateRange)
                .first()

        val currency =
            settingsRepository.selectedCurrency.first()

        val categorySummaries =
            analyticsUseCases
                .getCategorySummary(dateRange)
                .first()

        val monthlyBudget =
            getBudgetUseCase()
                .first()

        val budgetRemaining =
            monthlyBudget - monthlySummary.totalExpense

        val budgetUsagePercentage =
            if (monthlyBudget > 0.0) {
                (monthlySummary.totalExpense / monthlyBudget) * 100.0
            } else {
                0.0
            }

        return FinanceContext(
            period = getCurrentMonthLabel(),
            currency = currency,
            totalIncome = monthlySummary.totalIncome,
            totalExpense = monthlySummary.totalExpense,
            balance = monthlySummary.balance,
            transactionCount = monthlySummary.transactionCount,
            monthlyBudget = monthlyBudget,
            budgetRemaining = budgetRemaining,
            budgetUsagePercentage = budgetUsagePercentage,
            categorySummaries = categorySummaries.map {
                FinanceCategorySummary(
                    categoryName = it.categoryName,
                    totalAmount = it.totalAmount,
                    percentage = it.percentage.toDouble()
                )
            }
        )
    }

    private fun getCurrentMonthDateRange(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.set(
            Calendar.DAY_OF_MONTH,
            1
        )

        calendar.set(
            Calendar.HOUR_OF_DAY,
            0
        )

        calendar.set(
            Calendar.MINUTE,
            0
        )

        calendar.set(
            Calendar.SECOND,
            0
        )

        calendar.set(
            Calendar.MILLISECOND,
            0
        )

        val startDate =
            calendar.timeInMillis

        calendar.add(
            Calendar.MONTH,
            1
        )

        calendar.add(
            Calendar.MILLISECOND,
            -1
        )

        val endDate =
            calendar.timeInMillis

        return DateRange(
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun getCurrentMonthLabel(): String {

        return SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
        ).format(
            Calendar.getInstance().time
        )
    }
}