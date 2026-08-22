package com.finance.lumora.presentation.dashboard.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.TransactionWithCategory
import com.finance.lumora.domain.usecase.dashboard.DashboardUseCases
import com.finance.lumora.presentation.dashboard.effect.DashboardUiEffect
import com.finance.lumora.presentation.dashboard.state.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.finance.lumora.presentation.dashboard.event.DashboardEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import com.finance.lumora.presentation.dashboard.event.DashboardNavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * ViewModel responsible for managing
 * Dashboard UI state.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases

) : ViewModel() {

// Navigation Events
//--------------------------------------------------

    /**
     * Emits one-time navigation events.
     */
    private val _navigationEvent =
        Channel<DashboardNavigationEvent>()

    val navigationEvent =
        _navigationEvent.receiveAsFlow()

    // UI State
    // ----------------------------------------------------
    private var dashboardJob: Job? = null
    private val _state = MutableStateFlow(
        DashboardState()
    )

    val state: StateFlow<DashboardState> =
        _state.asStateFlow()

    // UI Effects
    // ----------------------------------------------------

    private val _uiEffect =
        MutableSharedFlow<DashboardUiEffect>()

    val uiEffect: SharedFlow<DashboardUiEffect> =
        _uiEffect.asSharedFlow()

    // Initialization
    // ----------------------------------------------------
    init {

        loadDashboard()

    }
//---------------------------------------------------------------------------------
    // Helper Functions: Starts:
    //-----------------------------
    /**
     * Loads dashboard information.
     *
     * Since Room returns Flow,
     * the dashboard automatically updates
     * whenever the database changes.
     */
    private fun loadDashboard() {

        dashboardJob?.cancel()

        dashboardJob = viewModelScope.launch {

            _state.value = _state.value.copy(

                isLoading = true,

                error = null

            )

            dashboardUseCases
                .getDashboardSummary()
                .catch { throwable ->

                    _state.value = _state.value.copy(

                        isLoading = false,

                        error = throwable.message
                            ?: "Failed to load dashboard."

                    )

                }
                .collect { summary ->

                    _state.value = _state.value.copy(

                        isLoading = false,

                        financial = summary.financial,

                        statistics = summary.statistics,

                        category = summary.category,

                        recentTransactions = summary.recentTransactions,

                        error = null

                    )

                }

        }

    }

    //_______________________________________________________
    /**
     * Refreshes the dashboard.
     */
    private fun refreshDashboard() {

        loadDashboard()

    }

    /**
     * Retries loading dashboard data.
     */
    private fun retry() {

        loadDashboard()

    }
//---------------------------------------------------------------------------
    /**
     * Navigates to Add Transaction screen.
     */
    /**
     * Emits navigation event to Add Transaction screen.
     */
    private fun navigateToAddTransaction() {

        viewModelScope.launch {

            _uiEffect.emit(

                DashboardUiEffect.NavigateToAddTransaction

            )

        }

    }
//---------------------------------------------------------------------------
    /**
     * Navigates to Transactions screen.
     */
    /**
     * Emits navigation event to Transactions screen.
     */
    private fun navigateToTransactions() {

        viewModelScope.launch {

            _uiEffect.emit(

                DashboardUiEffect.NavigateToTransactions

            )

        }

    }
//---------------------------------------------------------------------------
    /**
     * Navigates to Transaction Details.
     */
    /**
     * Emits navigation event to Transaction Details screen.
     */
    private fun navigateToTransactionDetails(

        transaction: TransactionWithCategory

    ) {

        viewModelScope.launch {

            _uiEffect.emit(

                DashboardUiEffect.NavigateToTransactionDetails(

                    transaction = transaction

                )

            )

        }

    }
//---------------------------------------------------------------------------
    /**
     * Emits a Snackbar message.
     */
    private fun showSnackbar(

        message: String

    ) {

        viewModelScope.launch {

            _uiEffect.emit(

                DashboardUiEffect.ShowSnackbar(

                    message = message

                )

            )

        }

    }
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------
    // helper Functions ending
    // ----------------------------------------------------------------

    //
    /**
     * Handles all Dashboard UI events.
     */
    fun onEvent(

        event: DashboardEvent

    ) {

        when (event) {

            DashboardEvent.LoadDashboard -> {

                loadDashboard()

            }

            DashboardEvent.RefreshDashboard -> {

                refreshDashboard()

            }

            DashboardEvent.Retry -> {

                retry()

            }

            DashboardEvent.AddTransaction -> {

                navigateToAddTransaction()

            }

            DashboardEvent.ViewAllTransactions -> {

                navigateToTransactions()

            }

            is DashboardEvent.TransactionClicked -> {

                navigateToTransactionDetails(

                    event.transaction

                )

            }

            else -> {}
        }

    }



}