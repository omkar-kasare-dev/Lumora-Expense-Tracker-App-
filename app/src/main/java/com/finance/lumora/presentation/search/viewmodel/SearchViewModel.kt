package com.finance.lumora.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.search.model.SearchFilters
import com.finance.lumora.domain.search.repository.SearchRepository
import com.finance.lumora.presentation.search.intent.SearchIntent
import com.finance.lumora.presentation.search.state.FilterChipType
import com.finance.lumora.presentation.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

// UI STATE
// ================================================================

    private val _uiState =
        MutableStateFlow(SearchUiState())

    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

// SEARCH QUERY
// ================================================================

    private val searchQuery =
        MutableStateFlow("")

// INIT
// ================================================================

    init {
        observeSearch()
    }

// INTENTS
// ================================================================

    fun onIntent(intent: SearchIntent) {

        when (intent) {

            // SEARCH QUERY
            // --------------------------------------------------------

            is SearchIntent.OnQueryChange -> {

                val trimmedQuery =
                    intent.query.trim()

                _uiState.update {

                    it.copy(

                        searchQuery = trimmedQuery,

                        isLoading = true

                    )

                }

                searchQuery.value =
                    trimmedQuery
            }

            // RECENT SEARCH
            // --------------------------------------------------------

            is SearchIntent.OnRecentSearchClick -> {

                onIntent(
                    SearchIntent.OnQueryChange(
                        intent.query
                    )
                )
            }

            // UPDATE FILTERS
            // --------------------------------------------------------

            is SearchIntent.UpdateFilters -> {

                _uiState.update {

                    it.copy(

                        filters = intent.filters,

                        isLoading = true

                    )
                }
            }

            // REMOVE FILTER CHIP
            // --------------------------------------------------------

            is SearchIntent.RemoveFilter -> {

                _uiState.update { state ->

                    val currentFilters =
                        state.filters

                    val updatedFilters =
                        when (intent.chip) {

                            FilterChipType.TransactionType ->
                                currentFilters.copy(
                                    transactionType = null
                                )

                            FilterChipType.Category ->
                                currentFilters.copy(
                                    categoryId = null
                                )

                            FilterChipType.MinAmount ->
                                currentFilters.copy(
                                    minAmount = null
                                )

                            FilterChipType.MaxAmount ->
                                currentFilters.copy(
                                    maxAmount = null
                                )

                            FilterChipType.DateRange ->
                                currentFilters.copy(
                                    startDate = null,
                                    endDate = null
                                )
                        }

                    state.copy(

                        filters = updatedFilters,

                        isLoading = true

                    )
                }
            }

            // CLEAR FILTERS
            // --------------------------------------------------------

            SearchIntent.ClearFilters -> {

                _uiState.update {

                    it.copy(

                        filters = SearchFilters(),

                        isLoading = true

                    )
                }
            }

            // CLEAR SEARCH
            // --------------------------------------------------------

            SearchIntent.ClearSearch -> {

                onIntent(
                    SearchIntent.OnQueryChange("")
                )
            }
            // OTHER INTENTS
            // --------------------------------------------------------

            else -> {}

        }
    }
// OBSERVE SEARCH
// ================================================================

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSearch() {
        viewModelScope.launch {
            combine(
                // QUERY
                // ----------------------------------------------------

                searchQuery
                    .debounce(300)
                    .distinctUntilChanged(),
                // FILTERS ONLY
                // ----------------------------------------------------

                _uiState
                    .map { state ->
                        state.filters
                    }
                    .distinctUntilChanged()

            ) { query, filters ->

                SearchRequest(

                    query = query,

                    filters = filters

                )

            }
                // CANCEL PREVIOUS SEARCH
                // ----------------------------------------------------

                .flatMapLatest { request ->

                    // Do not query Room when there is
                    // neither a search query nor filters.

                    if (
                        request.query.isBlank() &&
                        request.filters == SearchFilters()
                    ) {

                        flowOf(emptyList())

                    } else {

                        repository.searchTransactions(

                            query = request.query,

                            filters = request.filters

                        )
                    }
                }

                // ERROR HANDLING
                // ----------------------------------------------------

                .catch {

                    _uiState.update {

                        it.copy(

                            searchResults =
                                emptyList(),

                            isLoading =
                                false,

                            showEmptyState =
                                true

                        )
                    }
                }

                // RESULTS
                // ----------------------------------------------------

                .collect { results ->

                    _uiState.update {

                        it.copy(

                            searchResults =
                                results,

                            isLoading =
                                false,

                            showEmptyState =
                                results.isEmpty()

                        )
                    }
                }
        }
    }
}
// INTERNAL SEARCH REQUEST
// ====================================================================

private data class SearchRequest(
    val query: String,
    val filters: SearchFilters
)

