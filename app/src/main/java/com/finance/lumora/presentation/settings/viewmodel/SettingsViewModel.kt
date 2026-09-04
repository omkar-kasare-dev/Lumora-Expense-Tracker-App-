package com.finance.lumora.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.UserSettings
import com.finance.lumora.domain.usecase.budget.BudgetAlertUseCases
import com.finance.lumora.domain.usecase.settings.SettingsUseCases
import com.finance.lumora.notifications.BudgetAlertWorkScheduler
import com.finance.lumora.presentation.settings.intent.SettingsEvent
import com.finance.lumora.presentation.settings.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.finance.lumora.data.export.CsvExporter
import com.finance.lumora.domain.usecase.export.ExportDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
    private val budgetAlertUseCases: BudgetAlertUseCases,
    private val budgetAlertWorkScheduler: BudgetAlertWorkScheduler,
    private val exportDataUseCase: ExportDataUseCase
) : ViewModel() {

    val currentMonthlyExpense: StateFlow<Double> = budgetAlertUseCases
        .getMonthlyExpenseUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
    // Export State:
    private val _exportCsv = MutableStateFlow<String?>(null)

    val exportCsv: StateFlow<String?> = _exportCsv.asStateFlow()
    private val _exportMessage = MutableStateFlow<String?>(null)

    val exportMessage: StateFlow<String?> = _exportMessage.asStateFlow()
    //----

    /**
     * ----------------------------------------------------
     * User Settings
     * ----------------------------------------------------
     *
     * Combines all persistent user preference values
     * into the UserSettings domain model.
     */
    private val userSettingsFlow = combine(

        settingsUseCases.getCurrency(),

        settingsUseCases.getTheme(),

        settingsUseCases.getNotifications(),

        settingsUseCases.getBudgetAlerts(),

        settingsUseCases.getBiometric()

    ) { currency,
        theme,
        isNotificationsEnabled,
        isBudgetAlertsEnabled,
        isBiometricEnabled ->

        UserSettings(

            currency = currency,

            theme = theme,

            isBiometricEnabled = isBiometricEnabled,

            isNotificationsEnabled = isNotificationsEnabled,

            isBudgetAlertsEnabled = isBudgetAlertsEnabled

        )
    }

    /**
     * ----------------------------------------------------
     * Settings UI State
     * ----------------------------------------------------
     *
     * Combines UserSettings with the monthly budget.
     */
    val uiState: StateFlow<SettingsUiState> = combine(

        userSettingsFlow,

        settingsUseCases.getBudget()

    ) { settings, monthlyBudget ->

        SettingsUiState(

            settings = settings,

            monthlyBudget = monthlyBudget,

            isLoading = false,

            error = null

        )

    }
        .onStart {

            emit(
                SettingsUiState(
                    isLoading = true
                )
            )

        }
        .catch { throwable ->

            emit(
                SettingsUiState(
                    isLoading = false,
                    error = throwable.message
                        ?: "Something went wrong"
                )
            )

        }
        .stateIn(

            scope = viewModelScope,

            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5_000
            ),

            initialValue = SettingsUiState(
                isLoading = true
            )

        )

    /**
     * ----------------------------------------------------
     * Event Handler
     * ----------------------------------------------------
     */
    fun onEvent(event: SettingsEvent) {

        when (event) {

            is SettingsEvent.ChangeCurrency -> {

                saveCurrency(event.currency)

            }

            is SettingsEvent.ChangeTheme -> {

                saveTheme(event.theme)

            }


            is SettingsEvent.ToggleNotifications -> {

                saveNotifications(
                    event.enabled
                )

            }

            is SettingsEvent.ToggleBudgetAlerts -> {

                saveBudgetAlerts(
                    event.enabled
                )

            }

            is SettingsEvent.ToggleBiometric -> {

                saveBiometric(
                    event.enabled
                )

            }

            is SettingsEvent.ChangeBudget -> {

                saveBudget(
                    event.amount
                )

            }
        }
    }

    /**
     * ----------------------------------------------------
     * Save Currency
     * ----------------------------------------------------
     */
    private fun saveCurrency(
        currency: String
    ) {

        viewModelScope.launch {

            settingsUseCases.saveCurrency(
                currency
            )

        }
    }

    /**
     * ----------------------------------------------------
     * Save Theme
     * ----------------------------------------------------
     */
    private fun saveTheme(
        theme: com.finance.lumora.domain.model.AppTheme
    ) {

        viewModelScope.launch {

            settingsUseCases.saveTheme(
                theme
            )

        }
    }

    /**
     * ----------------------------------------------------
     * Save Notifications
     * ----------------------------------------------------
     */
    private fun saveNotifications(
        enabled: Boolean
    ) {

        viewModelScope.launch {

            settingsUseCases.saveNotifications(
                enabled
            )

        }
    }

    /**
     * ----------------------------------------------------
     * Save Budget Alerts
     * ----------------------------------------------------
     */
    // SettingsViewModel.kt
    private fun saveBudgetAlerts(enabled: Boolean) {
        viewModelScope.launch {
            settingsUseCases.saveBudgetAlerts(enabled)
            if (enabled) {
                budgetAlertWorkScheduler.scheduleBudgetAlertChecks()
            } else {
                budgetAlertWorkScheduler.cancelBudgetAlertChecks()
            }
        }
    }

    /**
     * ----------------------------------------------------
     * Save Biometric
     * ----------------------------------------------------
     */
    private fun saveBiometric(
        enabled: Boolean
    ) {

        viewModelScope.launch {

            settingsUseCases.saveBiometric(
                enabled
            )

        }
    }

    /**
     * ----------------------------------------------------
     * Save Monthly Budget
     * ----------------------------------------------------
     */
    private fun saveBudget(
        amount: Double
    ) {

        viewModelScope.launch {

            settingsUseCases.saveBudget(
                amount
            )

        }
    }
    fun onTestBudgetAlertClicked() {
        budgetAlertWorkScheduler.triggerImmediateBudgetCheck()
    }

    //Export Data Function:
    fun exportData() {
        viewModelScope.launch {
            try {
                _exportMessage.value = null
                _exportCsv.value = null

                val transactions = exportDataUseCase()

                if (transactions.isEmpty()) {
                    _exportMessage.value =
                        "No transactions available to export."
                    return@launch
                }

                _exportCsv.value = CsvExporter.generate(
                    transactions
                )

            } catch (e: Exception) {
                _exportCsv.value = null
                _exportMessage.value =
                    "Unable to export transactions. Please try again."
            }
        }
    }

    fun clearExportCsv() {
        _exportCsv.value = null
    }
    fun clearExportMessage() {
        _exportMessage.value = null
    }
    //--------------------

}