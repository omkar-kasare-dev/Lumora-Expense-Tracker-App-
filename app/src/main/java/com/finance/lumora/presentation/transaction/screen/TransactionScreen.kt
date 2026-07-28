package com.finance.lumora.presentation.transaction.screen
/*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.finance.lumora.presentation.transaction.dialog.DeleteTransactionDialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar
import com.finance.lumora.presentation.transaction.components.AddTransactionDialog
import com.finance.lumora.presentation.transaction.components.DashboardSection
import com.finance.lumora.presentation.transaction.components.EmptyTransactionState
import com.finance.lumora.presentation.transaction.components.TransactionForm
import com.finance.lumora.presentation.transaction.components.transactionList
import com.finance.lumora.presentation.transaction.effect.TransactionUiEffect
import com.finance.lumora.presentation.transaction.event.TransactionEvent
import com.finance.lumora.presentation.transaction.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    // 1. Manages whether the input overlay dialog shows up on screen
    var isAddTransactionDialogOpen by remember { mutableStateOf(false) }


    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {

        viewModel.onEvent(
            TransactionEvent.LoadCategories
        )

        viewModel.onEvent(
            TransactionEvent.LoadTransactions
        )
    }

    LaunchedEffect(Unit) {

        viewModel.uiEffect.collect { effect ->

            when (effect) {

                is TransactionUiEffect.ShowSnackbar -> {

                    snackbarHostState.showSnackbar(
                        effect.message
                    )

                }

                TransactionUiEffect.NavigateBack -> {

                    navController.popBackStack()

                }

                else -> Unit
            }
        }
    }

    Scaffold(

        topBar = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding() // 1. Safe boundary: Prevents overlapping the system status bar icons
                    .padding(top = 10.dp)  // 2. Visual gap: Generates the explicit separation space you want
            ){
                DashboardTopBar(
                    userName = "Transactions Section",
                    onNotificationClick = { /* Handle notification tap */ },
                    onProfileClick = { /* Handle profile tap */ }
                )
            }

        },

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )

        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }

        ,
        floatingActionButton = {
            // 2. FAB Button to trigger the dialog window open
            FloatingActionButton(onClick = {isAddTransactionDialogOpen = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
                AddTransactionDialog(
                    showDialog = isAddTransactionDialogOpen,
                    state = state,
                    onAmountChanged = { viewModel.onEvent(TransactionEvent.AmountChanged(it)) },
                    onTypeChanged = { viewModel.onEvent(TransactionEvent.TypeChanged(it)) },
                    onCategoryChanged = { viewModel.onEvent(TransactionEvent.CategoryChanged(it)) },
                    onDateChanged = { viewModel.onEvent(TransactionEvent.DateChanged(it)) },
                    onNoteChanged = { viewModel.onEvent(TransactionEvent.NoteChanged(it)) },
                    onSaveClicked = { viewModel.onEvent(TransactionEvent.SaveTransaction) },
                    onDismissRequest = { isAddTransactionDialogOpen = false }
                )
            }
        }

    ) { innerPadding ->

        if (state.showDeleteDialog) {

            DeleteTransactionDialog(

                onDismiss = {

                    viewModel.onEvent(
                        TransactionEvent.DismissDeleteDialog
                    )

                },

                onConfirm = {

                    viewModel.onEvent(
                        TransactionEvent.ConfirmDeleteTransaction
                    )

                }

            )

        }

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            )

        ) {

            item {

                DashboardSection(

                    totalIncome = state.totalIncome,

                    totalExpense = state.totalExpense

                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

            }
            /*
            item {

                TransactionForm(

                    state = state,

                    onAmountChanged = {

                        viewModel.onEvent(
                            TransactionEvent.AmountChanged(it)
                        )

                    },

                    onTypeChanged = {

                        viewModel.onEvent(
                            TransactionEvent.TypeChanged(it)
                        )

                    },

                    onCategoryChanged = {

                        viewModel.onEvent(
                            TransactionEvent.CategoryChanged(it)
                        )

                    },

                    onDateChanged = {

                        viewModel.onEvent(
                            TransactionEvent.DateChanged(it)
                        )

                    },

                    onNoteChanged = {

                        viewModel.onEvent(
                            TransactionEvent.NoteChanged(it)
                        )

                    },

                    onSaveClicked = {

                        viewModel.onEvent(
                            TransactionEvent.SaveTransaction
                        )

                    }

                )

            }

             */

            item {

                Text(

                    text = "Recent Transactions",

                    style = MaterialTheme.typography.titleLarge,

                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)

                )

            }

            if (state.transactions.isEmpty()) {

                item {

                    EmptyTransactionState()

                }

            } else {

                transactionList(

                    transactions = state.transactions,

                    categories = state.categories,

                    onEditClick = {

                        viewModel.onEvent(
                            TransactionEvent.EditTransaction(it)
                        )

                    },

                    onDeleteClick = {

                        viewModel.onEvent(
                            TransactionEvent.ShowDeleteDialog(it)
                        )

                    }

                )

            }


        }

    }

}

 */


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.presentation.category.components.AddCategoryDialog
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar
import com.finance.lumora.presentation.subcategory.components.AddSubCategoryDialog
import com.finance.lumora.presentation.transaction.components.AddTransactionDialog
import com.finance.lumora.presentation.transaction.components.DashboardSection
import com.finance.lumora.presentation.transaction.components.EmptyTransactionState
import com.finance.lumora.presentation.transaction.components.transactionList
import com.finance.lumora.presentation.transaction.dialog.DeleteTransactionDialog
import com.finance.lumora.presentation.transaction.effect.TransactionUiEffect
import com.finance.lumora.presentation.transaction.event.TransactionEvent
import com.finance.lumora.presentation.transaction.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isAddTransactionDialogOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: TransactionViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.onEvent(TransactionEvent.LoadCategories)
        viewModel.onEvent(TransactionEvent.LoadTransactions)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is TransactionUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                TransactionUiEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            // FIX 1: Wrap in a solid Surface window layer.
            // This blocks scrolled list elements from being visible underneath your padding zones.
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(top = 10.dp, bottom = 10.dp) // Added bottom padding to cleanly frame the bar
                ) {
                    DashboardTopBar(
                        userName = "Transactions Section",
                        onNotificationClick = { /* Handle notification tap */ },
                        onProfileClick = { /* Handle profile tap */ }
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddTransactionDialogOpen = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { innerPadding ->

        // ---------------------------------------------------------
        // Overlay Dialogs Hierarchy (Placed cleanly outside layout boundaries)
        // ---------------------------------------------------------
        if (state.showDeleteDialog) {
            DeleteTransactionDialog(
                onDismiss = { viewModel.onEvent(TransactionEvent.DismissDeleteDialog) },
                onConfirm = { viewModel.onEvent(TransactionEvent.ConfirmDeleteTransaction) }
            )
        }

        // FIX 2: Extracted AddTransactionDialog cleanly out of the FAB content builder scope
        AddTransactionDialog(
            showDialog = isAddTransactionDialogOpen,
            state = state,
            onAmountChanged = { viewModel.onEvent(TransactionEvent.AmountChanged(it)) },
            onTypeChanged = { viewModel.onEvent(TransactionEvent.TypeChanged(it)) },
            onCategoryChanged = { viewModel.onEvent(TransactionEvent.CategoryChanged(it)) },
            onDateChanged = { viewModel.onEvent(TransactionEvent.DateChanged(it)) },
            onSubCategoryChanged = {

                viewModel.onEvent(

                    TransactionEvent.SubCategoryChanged(it)

                )

            },

            onAddSubCategoryClick = {

                viewModel.onEvent(

                    TransactionEvent.ShowAddSubCategoryDialog

                )

            },
            onNoteChanged = { viewModel.onEvent(TransactionEvent.NoteChanged(it)) },
            onSaveClicked = { viewModel.onEvent(TransactionEvent.SaveTransaction) },
            onAddCategoryClick = {
                Log.d("CATEGORY_DIALOG", "Add Category Clicked")

                viewModel.onEvent(
                    TransactionEvent.ShowAddCategoryDialog
                )


            },
            onDismissRequest = { isAddTransactionDialogOpen = false }
        )


        if (state.showAddCategoryDialog) {

            AddCategoryDialog(

                onDismiss = {
                    viewModel.onEvent(
                        TransactionEvent.DismissAddCategoryDialog
                    )
                },

                onSave = { category ->
                    Log.d(
                        "CATEGORY_SAVE",
                        "Sending Event: ${category.name}"
                    )

                    viewModel.onEvent(
                        TransactionEvent.SaveCustomCategory(category)
                    )

                }

            )
        }

        if (state.showAddSubCategoryDialog) {

            AddSubCategoryDialog(

                onDismiss = {

                    viewModel.onEvent(

                        TransactionEvent.DismissAddSubCategoryDialog

                    )

                },

                onSave = { subCategory ->

                    viewModel.onEvent(

                        TransactionEvent.SaveCustomSubCategory(

                            subCategory

                        )

                    )

                }

            )

        }

        // ---------------------------------------------------------
        // Scroll Content Base
        // ---------------------------------------------------------
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                // Explicitly uses Scaffold's calculated top offset + item spacing buffers
                top = innerPadding.calculateTopPadding() + 6.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            )
        ) {
            item {
                DashboardSection(
                    totalIncome = state.totalIncome,
                    totalExpense = state.totalExpense
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                )
            }

            if (state.transactions.isEmpty()) {
                item {
                    EmptyTransactionState()
                }
            } else {
                transactionList(
                    transactions = state.transactions,
                    categories = state.categories,
                    onEditClick = {
                        viewModel.onEvent(TransactionEvent.EditTransaction(it))
                        isAddTransactionDialogOpen = true
                    },
                    onDeleteClick = {
                        viewModel.onEvent(TransactionEvent.ShowDeleteDialog(it))
                    }
                )
            }
        }
    }
}