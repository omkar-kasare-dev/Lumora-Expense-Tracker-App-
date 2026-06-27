package com.finance.lumora.domain.usecase.settings


import com.finance.lumora.core.exception.SettingsValidationException
import com.finance.lumora.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(currency: String) {

        if (currency.isBlank()) {
            throw SettingsValidationException(
                "Currency cannot be empty."
            )
        }

        repository.setCurrency(currency)
    }
}