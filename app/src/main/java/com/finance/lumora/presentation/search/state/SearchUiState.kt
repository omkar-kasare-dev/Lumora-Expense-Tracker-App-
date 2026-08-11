package com.finance.lumora.presentation.search.state

import com.finance.lumora.data.local.relation.TransactionWithCategory
import com.finance.lumora.domain.search.model.SearchFilters


data class SearchUiState(

    val searchQuery: String = "",
    val filters: SearchFilters = SearchFilters(),

    val recentSearches: List<String> = emptyList(),


    val searchResults: List<TransactionWithCategory> = emptyList(),

    val isLoading: Boolean = false,

    val showEmptyState: Boolean = false
)


