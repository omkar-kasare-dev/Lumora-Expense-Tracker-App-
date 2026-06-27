package com.finance.lumora.domain.usecase.settings


import com.finance.lumora.core.exception.BudgetValidationException
import com.finance.lumora.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveBudgetUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(amount: Double) {

        if (amount <= 0) {
            throw BudgetValidationException(
                "Budget must be greater than zero."
            )
        }

        repository.setMonthlyBudget(amount)
    }
}