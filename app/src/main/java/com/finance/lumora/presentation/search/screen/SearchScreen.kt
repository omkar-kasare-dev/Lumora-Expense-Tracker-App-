package com.finance.lumora.presentation.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState

import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.presentation.category.viewmodel.CategoryViewModel
import com.finance.lumora.presentation.search.components.ActiveFilterChips
import com.finance.lumora.presentation.search.components.FilterBottomSheet
import com.finance.lumora.presentation.search.components.RecentSearchSection
import com.finance.lumora.presentation.search.components.SearchBarSection
import com.finance.lumora.presentation.search.components.SearchResultCard
import com.finance.lumora.presentation.search.components.SearchTopBar
import com.finance.lumora.presentation.search.intent.SearchIntent
import com.finance.lumora.presentation.search.viewmodel.SearchViewModel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
// SEARCH UI STATE
// ================================================================

    val uiState by viewModel.uiState.collectAsState()

// CATEGORY UI STATE
// ================================================================

    val categoryUiState by
    categoryViewModel.uiState.collectAsState()

// CONVERT DOMAIN CATEGORY → CATEGORY ENTITY
// ================================================================

    val categories: List<CategoryEntity> =
        categoryUiState.categories.map { category ->

            CategoryEntity(

                id = category.id,

                name = category.name,

                icon = category.icon,

                color = category.color,

                isDefault = category.isDefault

            )

        }

// FILTER SHEET STATE
// ================================================================

    var showFilterSheet by rememberSaveable {

        mutableStateOf(false)

    }

// FILTER BOTTOM SHEET
// ================================================================

    if (showFilterSheet) {

        FilterBottomSheet(

            filters = uiState.filters,

            categories = categories,

            onDismiss = {

                showFilterSheet = false

            },

            onApply = { filters ->

                viewModel.onIntent(

                    SearchIntent.UpdateFilters(filters)

                )

                showFilterSheet = false

            },

            onClear = {

                viewModel.onIntent(

                    SearchIntent.ClearFilters

                )

            }

        )
    }

// SCREEN
// ================================================================

    Scaffold(

        topBar = {

            SearchTopBar {

                navController.popBackStack()

            }

        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)

        ) {

            // SEARCH BAR
            // ========================================================

            SearchBarSection(

                query = uiState.searchQuery,

                onQueryChange = { query ->

                    viewModel.onIntent(

                        SearchIntent.OnQueryChange(query)

                    )

                },

                onClear = {

                    viewModel.onIntent(

                        SearchIntent.ClearSearch

                    )

                },

                onFilterClick = {

                    showFilterSheet = true

                }

            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // ACTIVE FILTER CHIPS
            // ========================================================

            ActiveFilterChips(

                filters = uiState.filters,

                categoryName =
                    categories
                        .firstOrNull {
                            it.id == uiState.filters.categoryId
                        }
                        ?.name,

                onRemoveFilter = { chip ->

                    viewModel.onIntent(

                        SearchIntent.RemoveFilter(chip)

                    )

                }

            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // RECENT SEARCHES
            // ========================================================

            RecentSearchSection(

                recentSearches =
                    uiState.recentSearches,

                onSearchClick = { query ->

                    viewModel.onIntent(

                        SearchIntent.OnRecentSearchClick(
                            query
                        )

                    )

                }

            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // CONTENT
            // ========================================================

            when {

                // LOADING
                // ----------------------------------------------------

                uiState.isLoading -> {

                    Column(

                        modifier =
                            Modifier.fillMaxSize(),

                        horizontalAlignment =
                            Alignment.CenterHorizontally,

                        verticalArrangement =
                            Arrangement.Center

                    ) {

                        LinearProgressIndicator(
                            modifier= Modifier.fillMaxWidth(0.5f)
                                .height(3.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme .surfaceVariant,
                            gapSize = 0.dp
                        )

                    }
                }


                // ----------------------------------------------------
                // EMPTY RESULT
                // ----------------------------------------------------

                uiState.showEmptyState -> {

                    Column(

                        modifier =
                            Modifier.fillMaxSize(),

                        horizontalAlignment =
                            Alignment.CenterHorizontally,

                        verticalArrangement =
                            Arrangement.Center

                    ) {

                        Text(

                            text =
                                "No transactions found",

                            style =
                                MaterialTheme.typography
                                    .titleMedium

                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(

                            text =
                                "Try another search or change your filters.",

                            style =
                                MaterialTheme.typography
                                    .bodyMedium,

                            color =
                                MaterialTheme.colorScheme
                                    .onSurfaceVariant

                        )
                    }
                }


                // ----------------------------------------------------
                // SEARCH RESULTS
                // ----------------------------------------------------

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)

                    ) {

                        items(

                            items =
                                uiState.searchResults,

                            key = { transaction ->

                                transaction.transaction.id

                            }

                        ) { transaction ->

                            SearchResultCard(

                                category =
                                    transaction.category.name,

                                title =
                                    transaction.transaction.note
                                        ?: "Transaction",

                                amount =
                                    transaction.transaction.amount
                                        .toString(),

                                date =
                                    formatTransactionDate(

                                        transaction
                                            .transaction
                                            .transactionDate

                                    )

                            )
                        }
                    }
                }
            }
        }
    }

}

// ====================================================================
// DATE FORMATTER
// ====================================================================
private fun formatTransactionDate(
             timestamp: Long
): String {


    return SimpleDateFormat(

        "dd MMM yyyy",

        Locale.getDefault()

    ).format(

        Date(timestamp)

    )
}

