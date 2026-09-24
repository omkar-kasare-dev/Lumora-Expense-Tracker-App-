package com.finance.lumora.presentation.search.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
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
    val categoryUiState by categoryViewModel.uiState.collectAsState()

    // CONVERT DOMAIN CATEGORY → CATEGORY ENTITY
    // ================================================================
    val categories: List<CategoryEntity> = categoryUiState.categories.map { category ->
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
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }

    // FILTER BOTTOM SHEET
    // ================================================================
    if (showFilterSheet) {
        FilterBottomSheet(
            filters = uiState.filters,
            categories = categories,
            onDismiss = { showFilterSheet = false },
            onApply = { filters ->
                viewModel.onIntent(SearchIntent.UpdateFilters(filters))
                showFilterSheet = false
            },
            onClear = {
                viewModel.onIntent(SearchIntent.ClearFilters)
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // SEARCH BAR
                // ========================================================
                SearchBarSection(
                    query = uiState.searchQuery,
                    onQueryChange = { query ->
                        viewModel.onIntent(SearchIntent.OnQueryChange(query))
                    },
                    onClear = {
                        viewModel.onIntent(SearchIntent.ClearSearch)
                    },
                    onFilterClick = {
                        showFilterSheet = true
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // ACTIVE FILTER CHIPS
                // ========================================================
                ActiveFilterChips(
                    filters = uiState.filters,
                    categoryName = categories.firstOrNull {
                        it.id == uiState.filters.categoryId
                    }?.name,
                    onRemoveFilter = { chip ->
                        viewModel.onIntent(SearchIntent.RemoveFilter(chip))
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // RECENT SEARCHES
                // ========================================================
                RecentSearchSection(
                    recentSearches = uiState.recentSearches,
                    onSearchClick = { query ->
                        viewModel.onIntent(SearchIntent.OnRecentSearchClick(query))
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // CONTENT
            // ========================================================
            when {
                // LOADING
                // ----------------------------------------------------
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                                .height(3.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }

                // EMPTY RESULT
                // ----------------------------------------------------
                uiState.showEmptyState -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.SearchOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Transactions Found",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Try another keyword or clear your active filters.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // SEARCH RESULTS
                // ----------------------------------------------------
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 4.dp,
                            bottom = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (uiState.searchResults.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Search Results",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 14.sp,
                                            letterSpacing = (-0.1).sp
                                        ),
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                        border = BorderStroke(
                                            width = 0.6.dp,
                                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                                        )
                                    ) {
                                        Text(
                                            text = "${uiState.searchResults.size} found",
                                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }

                        items(
                            items = uiState.searchResults,
                            key = { transaction -> transaction.transaction.id }
                        ) { transaction ->
                            SearchResultCard(
                                category = transaction.category.name,
                                title = transaction.transaction.note ?: "Transaction",
                                amount = transaction.transaction.amount.toString(),
                                date = formatTransactionDate(transaction.transaction.transactionDate),
                                modifier = Modifier.animateItem()
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
private fun formatTransactionDate(timestamp: Long): String {
    return SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    ).format(Date(timestamp))
}