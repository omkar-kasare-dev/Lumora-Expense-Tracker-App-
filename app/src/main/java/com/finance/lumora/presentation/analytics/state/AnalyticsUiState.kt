package com.finance.lumora.presentation.analytics.state

import com.finance.lumora.domain.analytics.model.AnalyticsFilter
import com.finance.lumora.domain.analytics.model.CategorySummary
import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import com.finance.lumora.domain.analytics.model.MonthlySummary
import com.finance.lumora.domain.analytics.utils.DateRangeFactory
data class AnalyticsUiState(

    val selectedFilter: AnalyticsFilter =
        AnalyticsFilter.THIS_MONTH,

    val dateRange: DateRange =
        DateRangeFactory.create(
            AnalyticsFilter.THIS_MONTH
        ),

    /*
     * Summary Card
     */
    val monthlySummary: MonthlySummary? = null,

    /*
     * Pie Chart
     */
    val categorySummary: List<CategorySummary> =
        emptyList(),

    /*
     * Bar Chart
     */
    val monthlyIncomeExpense:
    List<IncomeExpenseSummary> =
        emptyList(),

    /*
     * Loading
     */
    val isLoading: Boolean = false,

    /*
     * Error
     */
    val error: String? = null

)