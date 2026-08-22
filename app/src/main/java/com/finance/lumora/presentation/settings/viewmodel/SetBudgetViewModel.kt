package com.finance.lumora.presentation.settings.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.SettingsRepository
import com.finance.lumora.domain.usecase.budget.SaveMonthlyBudgetUseCase
import com.finance.lumora.presentation.settings.state.SetBudgetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SetBudgetViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val saveMonthlyBudgetUseCase: SaveMonthlyBudgetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SetBudgetUiState())
    val uiState: StateFlow<SetBudgetUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.monthlyBudget.collect { currentBudget ->
                if (currentBudget > 0.0) {
                    _uiState.update { it.copy(budgetInput = currentBudget.toString()) }
                }
            }
        }
    }

    fun onBudgetChanged(input: String) {
        _uiState.update { it.copy(budgetInput = input, errorMessage = null) }
    }

    fun saveBudget() {
        viewModelScope.launch {
            val result = saveMonthlyBudgetUseCase(_uiState.value.budgetInput)
            result.onSuccess {
                _uiState.update { it.copy(isSaved = true, errorMessage = null) }
            }.onFailure { error ->
                _uiState.update { it.copy(errorMessage = error.message) }
            }
        }
    }
}