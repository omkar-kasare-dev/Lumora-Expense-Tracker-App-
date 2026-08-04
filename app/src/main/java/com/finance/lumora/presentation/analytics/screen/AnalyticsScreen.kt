package com.finance.lumora.presentation.analytics.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.presentation.analytics.components.AnalyticsFilterRow
import com.finance.lumora.presentation.analytics.components.IncomeExpenseChart
import com.finance.lumora.presentation.analytics.components.MonthlySummaryCard
import com.finance.lumora.presentation.analytics.components.PieChartCard
import com.finance.lumora.presentation.analytics.intent.AnalyticsEvent
import com.finance.lumora.presentation.analytics.viewmodel.AnalyticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(

    onBackClick: () -> Unit,
    navController: NavHostController,
    viewModel: AnalyticsViewModel = hiltViewModel()

) {

    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(

                        text = "Analytics",

                        style = MaterialTheme.typography.titleLarge,

                        fontWeight = FontWeight.SemiBold

                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = onBackClick

                    ) {

                        Icon(

                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,

                            contentDescription = "Back"

                        )

                    }

                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

                    containerColor =
                        MaterialTheme.colorScheme.surface,

                    titleContentColor =
                        MaterialTheme.colorScheme.onSurface,

                    navigationIconContentColor =
                        MaterialTheme.colorScheme.onSurface

                )

            )

        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }

    ) { innerPadding ->

        when {

            //------------------------------------
            // Loading
            //------------------------------------

            uiState.isLoading -> {

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),

                    contentAlignment = Alignment.Center

                ) {

                    CircularProgressIndicator()

                }

            }

            //------------------------------------
            // Error
            //------------------------------------

            uiState.error != null -> {

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),

                    contentAlignment = Alignment.Center

                ) {

                    Text(

                        text = uiState.error!!,

                        color = MaterialTheme.colorScheme.error

                    )

                }

            }

            //------------------------------------
            // Empty State
            //------------------------------------

            uiState.monthlySummary?.transactionCount == 0 -> {

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),

                    contentAlignment = Alignment.Center

                ) {

                    Text(

                        text = "No analytics available yet.\nAdd some transactions.",

                        style = MaterialTheme.typography.bodyLarge

                    )

                }

            }

            //------------------------------------
            // Success
            //------------------------------------

            else -> {

                LazyColumn(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),

                    verticalArrangement = Arrangement.spacedBy(20.dp),

                    contentPadding = PaddingValues(16.dp)

                ) {

                    //------------------------------------
                    // Filter Chips
                    //------------------------------------

                    item {

                        AnalyticsFilterRow(

                            selectedFilter =
                                uiState.selectedFilter,

                            onFilterSelected = {

                                viewModel.onEvent(

                                    AnalyticsEvent.ChangeFilter(it)

                                )

                            }

                        )

                    }

                    //------------------------------------
                    // Monthly Summary
                    //------------------------------------

                    item {

                        uiState.monthlySummary?.let {

                            MonthlySummaryCard(

                                summary = it

                            )

                        }

                    }
                    item {

                        HorizontalDivider()

                    }
                    //------------------------------------
                    // Pie Chart
                    //------------------------------------
/*
                    item {

                        Text(

                            text = "Expense Distribution",

                            style = MaterialTheme.typography.titleMedium

                        )

                    }

                    item {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            PieChart(
                                categories = uiState.categorySummary
                            )

                        }


                    }

                    item {

                        PieChartLegend(

                            categories =
                                uiState.categorySummary

                        )

                    }

 */
                    item {

                        PieChartCard(categories =
                            uiState.categorySummary)

                    }
                    //------------------------------------
                    // Divider
                    //------------------------------------

                    item {

                        HorizontalDivider()

                    }

                    //------------------------------------
                    // Income Expense
                    //------------------------------------

                    item {

                        Text(

                            text = "Income vs Expense",

                            style = MaterialTheme.typography.titleMedium

                        )

                    }

                    item {

                        IncomeExpenseChart(

                            monthlyData =
                                uiState.monthlyIncomeExpense

                        )

                    }

                }

            }

        }

    }

}