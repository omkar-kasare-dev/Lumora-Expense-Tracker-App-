package com.finance.lumora.domain.usecase.settings


import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(theme: AppTheme) {
        repository.setTheme(theme)
    }
}