package com.finance.lumora.presentation.transaction.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.presentation.category.components.AddCategoryDialog
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
    onBackClick: () -> Unit,
    navController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isAddTransactionDialogOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Transactions",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            letterSpacing = (-0.2).sp
                        ),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isAddTransactionDialogOpen = true },
                shape = RoundedCornerShape(30.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    ) { innerPadding ->

        // ---------------------------------------------------------
        // Overlay Dialogs Hierarchy
        // ---------------------------------------------------------
        if (state.showDeleteDialog) {
            DeleteTransactionDialog(
                onDismiss = { viewModel.onEvent(TransactionEvent.DismissDeleteDialog) },
                onConfirm = { viewModel.onEvent(TransactionEvent.ConfirmDeleteTransaction) }
            )
        }

        AddTransactionDialog(
            showDialog = isAddTransactionDialogOpen,
            state = state,
            onAmountChanged = { viewModel.onEvent(TransactionEvent.AmountChanged(it)) },
            onTypeChanged = { viewModel.onEvent(TransactionEvent.TypeChanged(it)) },
            onCategoryChanged = { viewModel.onEvent(TransactionEvent.CategoryChanged(it)) },
            onDateChanged = { viewModel.onEvent(TransactionEvent.DateChanged(it)) },
            onSubCategoryChanged = {
                viewModel.onEvent(TransactionEvent.SubCategoryChanged(it))
            },
            onAddSubCategoryClick = {
                viewModel.onEvent(TransactionEvent.ShowAddSubCategoryDialog)
            },
            onNoteChanged = { viewModel.onEvent(TransactionEvent.NoteChanged(it)) },
            onSaveClicked = { viewModel.onEvent(TransactionEvent.SaveTransaction) },
            onAddCategoryClick = {
                Log.d("CATEGORY_DIALOG", "Add Category Clicked")
                viewModel.onEvent(TransactionEvent.ShowAddCategoryDialog)
            },
            onDismissRequest = { isAddTransactionDialogOpen = false }
        )

        if (state.showAddCategoryDialog) {
            AddCategoryDialog(
                onDismiss = {
                    viewModel.onEvent(TransactionEvent.DismissAddCategoryDialog)
                },
                onSave = { category ->
                    Log.d("CATEGORY_SAVE", "Sending Event: ${category.name}")
                    viewModel.onEvent(TransactionEvent.SaveCustomCategory(category))
                }
            )
        }

        if (state.showAddSubCategoryDialog) {
            AddSubCategoryDialog(
                onDismiss = {
                    viewModel.onEvent(TransactionEvent.DismissAddSubCategoryDialog)
                },
                onSave = { subCategory ->
                    viewModel.onEvent(TransactionEvent.SaveCustomSubCategory(subCategory))
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
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            )
        ) {
            item {
                DashboardSection(
                    totalIncome = state.totalIncome,
                    totalExpense = state.totalExpense
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 15.sp,
                            letterSpacing = (-0.1).sp
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (state.transactions.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            border = BorderStroke(
                                width = 0.6.dp,
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "${state.transactions.size} items",
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
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