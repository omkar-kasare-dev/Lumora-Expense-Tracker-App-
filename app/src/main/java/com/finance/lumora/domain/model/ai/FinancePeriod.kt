package com.finance.lumora.domain.model.ai

/**
 * Represents which time period a finance question refers to.
 * Extended beyond simple current/previous month so Aurix can
 * answer about today, this week, this year, and any specific
 * named month - matching the range of periods the Analytics
 * screen already supports.
 */
sealed interface FinancePeriod {

    data object TODAY : FinancePeriod

    data object THIS_WEEK : FinancePeriod

    data object CURRENT_MONTH : FinancePeriod

    data object PREVIOUS_MONTH : FinancePeriod

    data object CURRENT_YEAR : FinancePeriod

    /**
     * A specific calendar month named directly in the question
     * (e.g. "August" or "August 2025"). [month] uses the same
     * 0-based indexing as [java.util.Calendar] (JANUARY = 0).
     */
    data class SpecificMonth(
        val year: Int,
        val month: Int
    ) : FinancePeriod
}