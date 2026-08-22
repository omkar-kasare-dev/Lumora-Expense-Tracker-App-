package com.finance.lumora.domain.usecase.budget



import com.finance.lumora.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveMonthlyBudgetUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(amountString: String): Result<Unit> {
        val amount = amountString.toDoubleOrNull()
            ?: return Result.failure(IllegalArgumentException("Please enter a valid amount."))

        if (amount <= 0.0) {
            return Result.failure(IllegalArgumentException("Budget must be greater than zero."))
        }

        settingsRepository.setMonthlyBudget(amount)
        return Result.success(Unit)
    }
}