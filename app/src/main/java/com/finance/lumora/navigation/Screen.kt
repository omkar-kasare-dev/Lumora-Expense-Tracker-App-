package com.finance.lumora.navigation


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
}