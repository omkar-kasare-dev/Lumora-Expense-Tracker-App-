package com.finance.lumora.presentation.analytics.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.analytics.model.AnalyticsFilter
import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.usecase.AnalyticsUseCases
import com.finance.lumora.domain.analytics.utils.DateRangeFactory
import com.finance.lumora.presentation.analytics.intent.AnalyticsEvent
import com.finance.lumora.presentation.analytics.state.AnalyticsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AnalyticsViewModel @Inject constructor(

    private val analyticsUseCases: AnalyticsUseCases

) : ViewModel() {

    /**
     * ----------------------------------------------------
     * Selected Analytics Filter
     * ----------------------------------------------------
     * Single source of truth for the currently
     * selected filter.
     */
    private val selectedFilter = MutableStateFlow(
        AnalyticsFilter.THIS_MONTH
    )

    /**
     * ----------------------------------------------------
     * Custom Date Range
     * ----------------------------------------------------
     * Used only when the selected filter is CUSTOM.
     */
    private val customDateRange =
        MutableStateFlow<DateRange?>(null)

    /**
     * ----------------------------------------------------
     * Analytics UI State
     * ----------------------------------------------------
     * This StateFlow is observed directly by
     * AnalyticsScreen.
     */
    val uiState: StateFlow<AnalyticsUiState> =

        combine(

            selectedFilter,

            customDateRange

        ) { filter, customRange ->

            val dateRange =

                if (filter == AnalyticsFilter.CUSTOM) {

                    customRange
                        ?: DateRangeFactory.create(
                            AnalyticsFilter.THIS_MONTH
                        )

                } else {

                    DateRangeFactory.create(filter)

                }

            Pair(
                filter,
                dateRange
            )

        }

// Part 2 continues from here...
            .flatMapLatest { (filter, dateRange) ->

                combine(

                    analyticsUseCases.getMonthlySummary(
                        dateRange
                    ),

                    analyticsUseCases.getCategorySummary(
                        dateRange
                    ),

                    analyticsUseCases.getIncomeExpenseSummary(
                        dateRange
                    )

                ) { monthlySummary,
                    categorySummary,
                    monthlyIncomeExpense ->

                    AnalyticsUiState(

                        selectedFilter = filter,

                        dateRange = dateRange,

                        monthlySummary = monthlySummary,

                        categorySummary = categorySummary,

                        monthlyIncomeExpense = monthlyIncomeExpense,

                        isLoading = false,

                        error = null

                    )

                }

                    .onStart {

                        emit(

                            AnalyticsUiState(

                                selectedFilter = filter,

                                dateRange = dateRange,

                                isLoading = true

                            )

                        )

                    }

            }

            .catch { throwable ->

                emit(

                    AnalyticsUiState(

                        isLoading = false,

                        error =
                            throwable.message
                                ?: "Something went wrong"

                    )

                )

            }

            .stateIn(

                scope = viewModelScope,

                started = SharingStarted.WhileSubscribed(

                    stopTimeoutMillis = 5_000

                ),

                initialValue =

                    AnalyticsUiState(

                        isLoading = true

                    )

            )

    /**
     * ----------------------------------------------------
     * Event Handler
     * ----------------------------------------------------
     */

    // Part 3 continues from here...
    fun onEvent(
        event: AnalyticsEvent
    ) {

        when (event) {

            //----------------------------------
            // Initial Load
            //----------------------------------
            AnalyticsEvent.LoadAnalytics -> {

                // No action required.
                // StateFlow automatically loads
                // based on the selected filter.

            }

            //----------------------------------
            // Refresh
            //----------------------------------
            AnalyticsEvent.Refresh -> {

                // Re-emitting the same value forces
                // the Flow pipeline to restart.

                selectedFilter.value =
                    selectedFilter.value

            }

            //----------------------------------
            // Filter Changed
            //----------------------------------
            is AnalyticsEvent.ChangeFilter -> {

                selectedFilter.value =
                    event.filter

            }

            //----------------------------------
            // Custom Date Range
            //----------------------------------
            is AnalyticsEvent.ChangeDateRange -> {

                customDateRange.value =
                    DateRange(
                        startDate = event.startDate,
                        endDate = event.endDate
                    )

                selectedFilter.value =
                    AnalyticsFilter.CUSTOM

            }

        }

    }

}


