package com.finance.lumora.domain.usecase.dashboard





import com.finance.lumora.domain.model.DashboardSummary
import com.finance.lumora.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Returns all dashboard information.
 *
 * This is the single entry point for
 * loading the Dashboard.
 */
class GetDashboardSummaryUseCase @Inject constructor(

    private val repository: DashboardRepository

) {

    operator fun invoke(): Flow<DashboardSummary> {

        return repository.getDashboardSummary()

    }

}