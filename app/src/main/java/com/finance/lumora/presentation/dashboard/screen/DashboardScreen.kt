package com.finance.lumora.presentation.dashboard.screen

/*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.lumora.presentation.dashboard.effect.DashboardUiEffect
import com.finance.lumora.presentation.dashboard.event.DashboardEvent
import com.finance.lumora.presentation.dashboard.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(

    navController: NavHostController,

    viewModel: DashboardViewModel = hiltViewModel()

) {

    //--------------------------------------------------
    // State
    //--------------------------------------------------

    val state by viewModel.state.collectAsState()

    //--------------------------------------------------
    // Snackbar
    //--------------------------------------------------

    val snackbarHostState = remember {

        SnackbarHostState()

    }

    //--------------------------------------------------
    // UI Effects
    //--------------------------------------------------

    LaunchedEffect(Unit) {

        viewModel.uiEffect.collect { effect ->

            when (effect) {

                is DashboardUiEffect.ShowSnackbar -> {

                    snackbarHostState.showSnackbar(

                        effect.message

                    )

                }

                DashboardUiEffect.NavigateToAddTransaction -> {

                    // Navigation will be added later

                }

                DashboardUiEffect.NavigateToTransactions -> {

                    // Navigation will be added later

                }

                is DashboardUiEffect.NavigateToTransactionDetails -> {

                    // Navigation will be added later

                }

            }

        }

    }

    //--------------------------------------------------
    // Screen
    //--------------------------------------------------

    Scaffold(

        snackbarHost = {

            SnackbarHost(

                hostState = snackbarHostState

            )

        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    viewModel.onEvent(

                        DashboardEvent.AddTransaction

                    )

                }

            ) {

                Text("+")

            }

        }

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize(),

            contentAlignment = Alignment.Center

        ) {

            when {

                //--------------------------------------------------
                // Loading
                //--------------------------------------------------

                state.isLoading -> {

                    CircularProgressIndicator()

                }

                //--------------------------------------------------
                // Error
                //--------------------------------------------------

                state.error != null -> {

                    Text(

                        text = state.error!!

                    )

                }

                //--------------------------------------------------
                // Dashboard Content
                //--------------------------------------------------

                else -> {

                    /*
                     * Dashboard UI Components
                     *
                     * Phase 6.5.2 onwards
                     *
                     * DashboardTopBar()
                     * BalanceCard()
                     * FinancialSummaryCard()
                     * StatisticsCard()
                     * TopExpenseCategoryCard()
                     * RecentTransactionsSection()
                     */

                    Text(

                        text = "Dashboard"

                    )

                }

            }

        }

    }

}

*/



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.lumora.presentation.dashboard.components.DashboardError
import com.finance.lumora.presentation.dashboard.components.DashboardLoading
import com.finance.lumora.presentation.dashboard.components.EmptyDashboard
import com.finance.lumora.presentation.dashboard.screen.DashboardContent

import com.finance.lumora.presentation.dashboard.effect.DashboardUiEffect
import com.finance.lumora.presentation.dashboard.event.DashboardEvent
import com.finance.lumora.presentation.dashboard.event.DashboardNavigationEvent
import com.finance.lumora.presentation.dashboard.viewmodel.DashboardViewModel

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.navigation.Screen
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar
import com.finance.lumora.presentation.transaction.components.AddTransactionDialog
import com.finance.lumora.presentation.transaction.event.TransactionEvent
import com.finance.lumora.presentation.transaction.viewmodel.TransactionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(

    navController: NavHostController,

    viewModel: DashboardViewModel = hiltViewModel(),
    transactionViewModel: TransactionViewModel = hiltViewModel()

)

        /*
        @Composable
        fun DashboardScreen(

            viewModel: DashboardViewModel = hiltViewModel(),

            onNavigate: (DashboardNavigationEvent) -> Unit

        )

         */
{


    //----------------------------------------------------
    // State
    //----------------------------------------------------

    val state by viewModel.state.collectAsState()
    var isAddTransactionDialogOpen by remember { mutableStateOf(false) }
    val transactionState by transactionViewModel.state.collectAsStateWithLifecycle()
    //----------------------------------------------------
    // Snackbar
    //----------------------------------------------------

    val snackbarHostState = remember {

        SnackbarHostState()

    }

    //----------------------------------------------------
    // UI Effects
    //----------------------------------------------------
/*
    LaunchedEffect(Unit) {

        viewModel.navigationEvent.collectLatest { event ->

            onNavigate(event)

        }

    }

 */

    LaunchedEffect(Unit) {

        viewModel.uiEffect.collect { effect ->

            when (effect) {

                is DashboardUiEffect.ShowSnackbar -> {

                    snackbarHostState.showSnackbar(
                        effect.message
                    )

                }

                DashboardUiEffect.NavigateToAddTransaction -> {

                    // TODO Navigation

                }

                DashboardUiEffect.NavigateToTransactions -> {

                    // TODO Navigation

                }

                is DashboardUiEffect.NavigateToTransactionDetails -> {

                    // TODO Navigation

                }

            }

        }

    }

    //----------------------------------------------------
    // Screen
    //----------------------------------------------------

    Scaffold(

        modifier = Modifier.fillMaxSize(),

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )

        }
        ,

        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding() // 1. Safe boundary: Prevents overlapping the system status bar icons
                    .padding(top = 10.dp)  // 2. Visual gap: Generates the explicit separation space you want
            ){
                DashboardTopBar(
                    userName = "Omkar",
                    onSearchClick = {
                        if (navController.currentDestination?.route != Screen.Search.route) {
                            navController.navigate(Screen.Search.route)
                        }
                    },
                    onNotificationClick = {
                        navController.navigate("Notifications")
                    },
                    onProfileClick = {
                        navController.navigate("Profile")
                    }
                )
            }

        }
        ,

        bottomBar = {
            BottomNavigationBar(navController = navController)
        }

    ) { paddingValues ->

        when {

            //------------------------------------------
            // Loading
            //------------------------------------------

            state.isLoading -> {

                DashboardLoading()

            }

            //------------------------------------------
            // Error
            //------------------------------------------

            state.error != null -> {

                DashboardError(

                    message = state.error!!,

                    onRetry = {

                        viewModel.onEvent(

                            DashboardEvent.Retry

                        )

                    }

                )

            }

            //------------------------------------------
            // Empty Dashboard
            //------------------------------------------

            state.statistics.transactionCount == 0 -> {

                EmptyDashboard(
                    onAddTransaction = {
                        // 1. Notify your viewmodel event bus if needed
                        viewModel.onEvent(DashboardEvent.AddTransaction)

                        // 2. Open the dialog overlay window immediately
                        isAddTransactionDialogOpen = true
                    }
                )

            }

            //------------------------------------------
            // Dashboard
            //------------------------------------------

            else -> {

                DashboardContent(

                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues),

                    state = state,

                    onEvent = viewModel::onEvent

                )


            }




        }


    }/*
    AddTransactionDialog(
        showDialog = isAddTransactionDialogOpen,
        state = transactionState, // If your DashboardState matches/contains fields for TransactionState
        onAmountChanged = { transactionViewModel.onEvent(TransactionEvent.AmountChanged(it)) },
        onTypeChanged = { transactionViewModel.onEvent(TransactionEvent.TypeChanged(it)) },
        onCategoryChanged = { transactionViewModel.onEvent(TransactionEvent.CategoryChanged(it)) },
        onDateChanged = { transactionViewModel.onEvent(TransactionEvent.DateChanged(it)) },
        onNoteChanged = { transactionViewModel.onEvent(TransactionEvent.NoteChanged(it)) },
        onSaveClicked = { transactionViewModel.onEvent(TransactionEvent.SaveTransaction) },
        onAddCategoryClick = {

            viewModel.onEvent(
                TransactionEvent.ShowAddCategoryDialog
            )

        },
        onDismissRequest = { isAddTransactionDialogOpen = false }
    )
    */


}