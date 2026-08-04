package com.finance.lumora.domain.analytics.usecase


import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIncomeExpenseSummaryUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {

    operator fun invoke(
        dateRange: DateRange
    ): Flow<List<IncomeExpenseSummary>> {


        return repository.getIncomeExpenseSummary(
            dateRange
        )

    }
}