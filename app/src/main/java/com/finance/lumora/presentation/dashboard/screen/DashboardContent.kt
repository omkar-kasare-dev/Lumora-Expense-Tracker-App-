package com.finance.lumora.presentation.dashboard.screen



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.dashboard.components.BalanceCard
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar
import com.finance.lumora.presentation.dashboard.components.FinancialSummaryCard
import com.finance.lumora.presentation.dashboard.components.RecentTransactionsSection
import com.finance.lumora.presentation.dashboard.components.StatisticsCard
import com.finance.lumora.presentation.dashboard.components.TopExpenseCategoryCard
import com.finance.lumora.presentation.dashboard.event.DashboardEvent
import com.finance.lumora.presentation.dashboard.state.DashboardState

@Composable
fun DashboardContent(

    state: DashboardState,

    onEvent: (DashboardEvent) -> Unit,

    modifier: Modifier = Modifier

) {

    val listState = rememberLazyListState()

    LazyColumn(

        modifier = modifier.fillMaxSize(),

        state = listState,

        verticalArrangement = Arrangement.spacedBy(16.dp),

        contentPadding = PaddingValues(16.dp)

    ) {

        //--------------------------------------------------
        // Top Bar
        //--------------------------------------------------
/*
        item {

            DashboardTopBar()
            Spacer(modifier= Modifier.height(12.dp))

        }

 */

        //--------------------------------------------------
        // Balance Card
        //--------------------------------------------------

        item {

            BalanceCard(

                totalBalance = state.financial.totalBalance,

                totalIncome = state.financial.totalIncome,

                totalExpense = state.financial.totalExpense

            )
            Spacer(modifier= Modifier.height(12.dp))

        }

        //--------------------------------------------------
        // Financial Summary
        //--------------------------------------------------

        item {

            FinancialSummaryCard(

                monthlyIncome = state.financial.monthlyIncome,

                monthlyExpense = state.financial.monthlyExpense

            )

            Spacer(modifier= Modifier.height(12.dp))
        }

        //--------------------------------------------------
        // Statistics
        //--------------------------------------------------

        item {

            StatisticsCard(

                transactionCount = state.statistics.transactionCount,

                largestIncome = state.statistics.largestIncome,

                largestExpense = state.statistics.largestExpense

            )
            Spacer(modifier= Modifier.height(12.dp))

        }

        //--------------------------------------------------
        // Top Expense Category
        //--------------------------------------------------

        item {

            TopExpenseCategoryCard(

                topExpenseCategory =
                    state.category.topExpenseCategory

            )
            Spacer(modifier= Modifier.height(12.dp))

        }

        //--------------------------------------------------
        // Recent Transactions
        //--------------------------------------------------

        item {

            RecentTransactionsSection(

                recentTransactions =
                    state.recentTransactions,

                onSeeAllClick = {

                    onEvent(
                        DashboardEvent.ViewAllTransactions
                    )

                },

                onTransactionClick = {

                    onEvent(
                        DashboardEvent.TransactionClicked(it)
                    )

                }

            )

            Spacer(modifier= Modifier.height(12.dp))

        }

    }

}