package com.finance.lumora.navigation

/*
/**
 * Represents all navigation destinations in Lumora.
 *
 * Each object defines a unique navigation route.
 */
sealed class Screen(
    val route: String
) {

    /**
     * Home Dashboard
     */
    data object Home : Screen("home")

    data object Dashboard : Screen(
        route = "dashboard"
    )

    /**
     * Add Expense Screen
     */
    data object AddExpense : Screen("add_expense")

    /**
     * Category Management Screen
     */
    data object Categories : Screen("categories")

    /**
     * Monthly Reports Screen
     */
    data object Reports : Screen("reports")

    /**
     * Budget Tracking Screen
     */
    data object Budget : Screen("budget")

    /**
     * Application Settings Screen
     */
    data object Settings : Screen("settings")

    data object Profile:Screen("profile")

    data object Transaction:Screen(route="transaction")
}

 */



sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register : Screen("register")

    object ForgotPassword : Screen("forgot_password")

    object Dashboard : Screen("dashboard")

    object Transactions : Screen("transactions")

    object AddTransaction : Screen("add_transaction")

    object TransactionDetails :
        Screen("transaction_details/{transactionId}") {

        fun createRoute(transactionId: Long): String {

            return "transaction_details/$transactionId"

        }

    }

    object Categories : Screen("categories")


    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")

    object Notifications : Screen("notifications")

    object Reports : Screen("reports")

    object Settings : Screen("settings")

    object PrivacyPolicy : Screen("privacy_policy")

    object Splash : Screen("splash")

}