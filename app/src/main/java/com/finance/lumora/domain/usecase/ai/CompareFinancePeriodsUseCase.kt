package com.finance.lumora.domain.usecase.ai


import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.model.ai.FinanceComparison

import com.finance.lumora.domain.analytics.usecase.GetMonthlySummaryUseCase
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class CompareFinancePeriodsUseCase @Inject constructor(
    private val getMonthlySummaryUseCase: GetMonthlySummaryUseCase
) {

    suspend operator fun invoke(): FinanceComparison {

        val currentMonthRange =
            getCurrentMonthDateRange()

        val previousMonthRange =
            getPreviousMonthDateRange()

        val currentSummary =
            getMonthlySummaryUseCase(
                currentMonthRange
            ).first()

        val previousSummary =
            getMonthlySummaryUseCase(
                previousMonthRange
            ).first()

        val currentExpense =
            currentSummary.totalExpense

        val previousExpense =
            previousSummary.totalExpense

        val expenseDifference =
            currentExpense - previousExpense

        val expenseChangePercentage =
            if (previousExpense > 0.0) {
                (expenseDifference / previousExpense) * 100.0
            } else {
                0.0
            }

        return FinanceComparison(
            currentPeriod = "Current Month",
            previousPeriod = "Previous Month",
            currentExpense = currentExpense,
            previousExpense = previousExpense,
            expenseDifference = expenseDifference,
            expenseChangePercentage = expenseChangePercentage
        )
    }

    private fun getCurrentMonthDateRange(): DateRange {

        val today =
            LocalDate.now()

        val startOfMonth =
            today
                .withDayOfMonth(1)
                .atStartOfDay()

        val endOfMonth =
            today
                .withDayOfMonth(
                    today.lengthOfMonth()
                )
                .atTime(
                    LocalTime.MAX
                )

        return DateRange(
            startDate = startOfMonth.toEpochMillis(),
            endDate = endOfMonth.toEpochMillis()
        )
    }

    private fun getPreviousMonthDateRange(): DateRange {

        val previousMonth =
            LocalDate
                .now()
                .minusMonths(1)

        val startOfMonth =
            previousMonth
                .withDayOfMonth(1)
                .atStartOfDay()

        val endOfMonth =
            previousMonth
                .withDayOfMonth(
                    previousMonth.lengthOfMonth()
                )
                .atTime(
                    LocalTime.MAX
                )

        return DateRange(
            startDate = startOfMonth.toEpochMillis(),
            endDate = endOfMonth.toEpochMillis()
        )
    }

    private fun LocalDateTime.toEpochMillis(): Long {
        return atZone(
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()
    }
}