package com.finance.lumora.presentation.search.intent

import com.finance.lumora.domain.search.model.SearchFilters
import com.finance.lumora.presentation.search.state.FilterChipType


sealed interface SearchIntent {

    data class OnQueryChange(
        val query: String
    ) : SearchIntent

    data class OnRecentSearchClick(
        val query: String
    ) : SearchIntent

    data class UpdateFilters(

        val filters: SearchFilters

    ) : SearchIntent

    object ClearSearch : SearchIntent
    object ClearFilters : SearchIntent

    data class RemoveFilter(
        val chip: FilterChipType
    ) : SearchIntent
}