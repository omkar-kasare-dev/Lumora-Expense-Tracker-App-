package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.usecase.AnalyticsUseCases
import com.finance.lumora.domain.model.ai.FinanceCategorySummary
import com.finance.lumora.domain.model.ai.FinanceContext
import com.finance.lumora.domain.model.ai.FinancePeriod
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

    suspend operator fun invoke( period: FinancePeriod):
            FinanceContext {

        val dateRange = getDateRange(period)

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
            if (period == FinancePeriod.CURRENT_MONTH) {
                getBudgetUseCase()
                    .first()
            } else {
                null
            }

        val budgetRemaining =
            monthlyBudget?.let {
                it - monthlySummary.totalExpense
            }

        val budgetUsagePercentage =
            monthlyBudget?.let {
                if (it > 0.0) {
                    (monthlySummary.totalExpense / it) * 100.0
                } else {
                    null
                }
            }

        return FinanceContext(
            period = getPeriodLabel(period),
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
/*
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

 */

    private fun getDateRange(
        period: FinancePeriod
    ): DateRange {

        val calendar = Calendar.getInstance()

        when (period) {

            FinancePeriod.CURRENT_MONTH -> {

                calendar.set(
                    Calendar.DAY_OF_MONTH,
                    1
                )
            }

            FinancePeriod.PREVIOUS_MONTH -> {

                calendar.add(
                    Calendar.MONTH,
                    -1
                )

                calendar.set(
                    Calendar.DAY_OF_MONTH,
                    1
                )
            }

            FinancePeriod.CURRENT_YEAR -> {

                calendar.set(
                    Calendar.MONTH,
                    Calendar.JANUARY
                )

                calendar.set(
                    Calendar.DAY_OF_MONTH,
                    1
                )
            }
        }

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

        when (period) {

            FinancePeriod.CURRENT_MONTH,
            FinancePeriod.PREVIOUS_MONTH -> {

                calendar.add(
                    Calendar.MONTH,
                    1
                )
            }

            FinancePeriod.CURRENT_YEAR -> {

                calendar.add(
                    Calendar.YEAR,
                    1
                )
            }
        }

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
/*
    private fun getCurrentMonthLabel(): String {

        return SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
        ).format(
            Calendar.getInstance().time
        )
    }

 */

    private fun getPeriodLabel(
        period: FinancePeriod
    ): String {

        val calendar = Calendar.getInstance()

        return when (period) {

            FinancePeriod.CURRENT_MONTH -> {
                SimpleDateFormat(
                    "MMMM yyyy",
                    Locale.getDefault()
                ).format(
                    calendar.time
                )
            }

            FinancePeriod.PREVIOUS_MONTH -> {
                calendar.add(
                    Calendar.MONTH,
                    -1
                )

                SimpleDateFormat(
                    "MMMM yyyy",
                    Locale.getDefault()
                ).format(
                    calendar.time
                )
            }

            FinancePeriod.CURRENT_YEAR -> {
                SimpleDateFormat(
                    "yyyy",
                    Locale.getDefault()
                ).format(
                    calendar.time
                )
            }
        }
    }
}