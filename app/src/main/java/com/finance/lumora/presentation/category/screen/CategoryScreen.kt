package com.finance.lumora.presentation.category.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.presentation.category.viewmodel.CategoryViewModel
import com.finance.lumora.presentation.category.components.AddCategoryDialog
import com.finance.lumora.presentation.category.components.CategoryItem
import com.finance.lumora.presentation.category.components.EditCategoryDialog
import com.finance.lumora.presentation.category.components.DeleteCategoryDialog
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    navController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(uiState.errorMessage) {

        uiState.errorMessage?.let {

            snackBarHostState.showSnackbar(it)

            viewModel.clearError()
        }
    }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(

                        text = "Categories",

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

        snackbarHost = {

            SnackbarHost(
                hostState = snackBarHostState
            )

        },

        bottomBar = {
            BottomNavigationBar(navController = navController)
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    viewModel.showAddDialog()

                }

            ) {

                Icon(

                    imageVector = Icons.Default.Add,

                    contentDescription = "Add Category"

                )

            }

        }


    ) { paddingValues ->

        when {

            uiState.isLoading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()

                }

            }

            else -> {

                LazyColumn(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),

                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {

                    items(
                        items = uiState.categories,
                        key = { it.id }
                    ) { category ->
/*
                        CategoryItem(
                            category = category
                        )

 */
                        CategoryItem(

                            category = category,

                            onClick = {

                                viewModel.showEditDialog(category)

                            },
                            onDeleteClick = {

                                viewModel.showDeleteDialog(category)

                            }

                        )

                    }

                }

            }

        }
        // Add Category
    }
    if (uiState.showAddDialog) {

        AddCategoryDialog(

            onDismiss = {

                viewModel.hideAddDialog()

            },

            onSave = { category ->

                viewModel.addCategory(category)

            }

        )

    }

    // Edit Dialog
    val selectedCategory = uiState.selectedCategory

    if (
        uiState.showEditDialog &&
        selectedCategory != null
    ) {

        EditCategoryDialog(

            category = selectedCategory,

            onDismiss = {

                viewModel.hideEditDialog()

            },

            onUpdate = { updatedCategory ->

                viewModel.updateCategory(updatedCategory)

                viewModel.hideEditDialog()

            }

        )

    }


    // Delete Dialog

    if (
        uiState.showDeleteDialog &&
        uiState.categoryToDelete != null
    ) {

        DeleteCategoryDialog(

            category = uiState.categoryToDelete!!,

            onDismiss = {

                viewModel.hideDeleteDialog()

            },

            onDelete = {

                viewModel.deleteCategory(
                    uiState.categoryToDelete!!
                )

            }

        )

    }

}