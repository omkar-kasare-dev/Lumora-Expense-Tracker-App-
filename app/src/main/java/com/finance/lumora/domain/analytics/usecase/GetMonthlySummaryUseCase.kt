package com.finance.lumora.domain.analytics.usecase

import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.MonthlySummary
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthlySummaryUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {

    operator fun invoke(
        dateRange: DateRange
    ): Flow<MonthlySummary> {


        return repository.getMonthlySummary(
            dateRange
        )

    }
}