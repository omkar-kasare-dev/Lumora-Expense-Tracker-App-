package com.finance.lumora.domain.usecase.settings

import com.finance.lumora.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveNotificationsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
    }
}