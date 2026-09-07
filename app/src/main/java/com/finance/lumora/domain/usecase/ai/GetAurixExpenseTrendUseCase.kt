package com.finance.lumora.domain.usecase.ai



import com.finance.lumora.domain.model.ai.ExpenseTrendInsight
import com.finance.lumora.domain.repository.AurixFinanceRepository
import javax.inject.Inject

class GetAurixExpenseTrendUseCase @Inject constructor(
    private val aurixFinanceRepository: AurixFinanceRepository
) {

    suspend operator fun invoke(): ExpenseTrendInsight {
        return aurixFinanceRepository.getExpenseTrendInsight()
    }
}