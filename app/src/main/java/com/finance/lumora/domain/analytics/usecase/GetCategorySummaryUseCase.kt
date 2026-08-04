package com.finance.lumora.domain.analytics.usecase


import com.finance.lumora.domain.analytics.model.CategorySummary
import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.MonthlySummary
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategorySummaryUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {

    operator fun invoke(
        dateRange: DateRange
    ): Flow<List<CategorySummary>> {


        return repository.getCategorySummary(
            dateRange
        )

    }

}