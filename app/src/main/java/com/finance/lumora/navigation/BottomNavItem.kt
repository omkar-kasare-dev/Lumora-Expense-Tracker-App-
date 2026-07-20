package com.finance.lumora.navigation



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(

    val route: String,

    val title: String,

    val icon: ImageVector

) {

    data object Dashboard : BottomNavItem(

        route = Screen.Dashboard.route,

        title = "Dashboard",

        icon = Icons.Outlined.AccountBalanceWallet

    )

    data object Transactions : BottomNavItem(

        route = Screen.Transactions.route,

        title = "Transactions",

        icon = Icons.Outlined.ReceiptLong

    )

    data object Categories : BottomNavItem(

        route = Screen.Categories.route,

        title = "Categories",

        icon = Icons.Outlined.Category

    )

    data object Settings : BottomNavItem(

        route = Screen.Settings.route,

        title = "Settings",

        icon = Icons.Outlined.Settings

    )

    companion object {

        val items = listOf(

            Dashboard,

            Transactions,

            Categories,

            Settings

        )

    }

}