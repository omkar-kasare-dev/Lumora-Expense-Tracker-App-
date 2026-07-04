package com.finance.lumora.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finance.lumora.presentation.budget.BudgetScreen

import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.expense.AddExpenseScreen
import com.finance.lumora.presentation.home.HomeScreen
import com.finance.lumora.presentation.report.ReportScreen
import com.finance.lumora.presentation.settings.SettingsScreen

/**
 * Main Navigation Graph of Lumora.
 */
@Composable
fun LumoraNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(navController)
        }

        composable(Screen.Categories.route) {
            CategoryScreen(navController)
        }

        composable(Screen.Reports.route) {
            ReportScreen(navController)
        }

        composable(Screen.Budget.route) {
            BudgetScreen(navController)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}