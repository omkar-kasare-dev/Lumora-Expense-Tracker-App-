package com.finance.lumora.domain.analytics.repository

import com.finance.lumora.domain.analytics.model.CategorySummary
import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import com.finance.lumora.domain.analytics.model.MonthlySummary
import kotlinx.coroutines.flow.Flow
interface AnalyticsRepository {


    fun getMonthlySummary(
        dateRange: DateRange
    ): Flow<MonthlySummary>


    fun getCategorySummary(
        dateRange: DateRange
    ): Flow<List<CategorySummary>>


    fun getIncomeExpenseSummary(
        dateRange: DateRange
    ): Flow<List<IncomeExpenseSummary>>


}