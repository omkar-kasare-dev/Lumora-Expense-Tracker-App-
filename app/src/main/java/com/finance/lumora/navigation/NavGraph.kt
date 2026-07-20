package com.finance.lumora.navigation

/*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finance.lumora.presentation.budget.BudgetScreen

import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.expense.AddExpenseScreen
import com.finance.lumora.presentation.home.HomeScreen
import com.finance.lumora.presentation.profile.ProfileScreen
import com.finance.lumora.presentation.report.ReportScreen
import com.finance.lumora.presentation.settings.SettingsScreen
import com.finance.lumora.presentation.transaction.screen.TransactionScreen

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


        composable(Screen.Profile.route){
            ProfileScreen(navController)
        }

        composable(
            route = Screen.Transaction.route
        ) {

            TransactionScreen(
                navController = navController
            )

        }
    }
}

 */




import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.dashboard.screen.DashboardScreen
import com.finance.lumora.presentation.transaction.screen.TransactionScreen

@Composable
fun LumoraNavGraph(

    modifier: Modifier = Modifier

) {

    val navController = rememberNavController()

    NavHost(

        navController = navController,

        startDestination = Screen.Dashboard.route,

        modifier = modifier

    ) {

        //--------------------------------------------------
        // Dashboard
        //--------------------------------------------------

        composable(
            route = Screen.Dashboard.route
        ) {

            DashboardScreen(
                navController = navController
            )

        }

        //--------------------------------------------------
        // Transactions
        //--------------------------------------------------

        composable(
            route = Screen.Transactions.route
        ) {

            TransactionScreen(
                navController = navController
            )

        }

        //--------------------------------------------------
        // Add Transaction
        //--------------------------------------------------

        composable(
            route = Screen.AddTransaction.route
        ) {

            // TODO
            // AddTransactionScreen(navController)

        }

        //--------------------------------------------------
        // Transaction Details
        //--------------------------------------------------

        composable(

            route = Screen.TransactionDetails.route,

            arguments = listOf(

                navArgument("transactionId") {

                    type = NavType.LongType

                }

            )

        ) {

            val transactionId =

                it.arguments?.getLong("transactionId") ?: -1L

            // TODO
            // TransactionDetailsScreen(
            //     transactionId = transactionId
            // )

        }

        //--------------------------------------------------
        // Categories
        //--------------------------------------------------

        composable(Screen.Categories.route) {
            CategoryScreen(navController)
        }

        //--------------------------------------------------
        // Reports
        //--------------------------------------------------

        composable(
            route = Screen.Reports.route
        ) {

            // TODO
            // ReportsScreen(navController)

        }

        //--------------------------------------------------
        // Settings
        //--------------------------------------------------

        composable(
            route = Screen.Settings.route
        ) {

            // TODO
            // SettingsScreen(navController)

        }

    }

}