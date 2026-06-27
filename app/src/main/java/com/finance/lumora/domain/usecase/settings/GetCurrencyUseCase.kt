package com.finance.lumora.domain.usecase.settings


import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<String> {
        return repository.selectedCurrency
    }
}