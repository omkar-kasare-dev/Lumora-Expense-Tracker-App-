package com.finance.lumora.presentation.home.components



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Bottom Navigation used on Home Screen.
 */
@Composable
fun HomeBottomBar(

    selectedIndex: Int,

    onHomeClick: () -> Unit,

    onCategoryClick: () -> Unit,

    onReportClick: () -> Unit,

    onSettingClick: () -> Unit

) {

    NavigationBar {

        NavigationBarItem(

            selected = selectedIndex == 0,

            onClick = onHomeClick,

            icon = {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )

            },

            label = {

                Text("Home")

            },

            colors = NavigationBarItemDefaults.colors()

        )

        NavigationBarItem(

            selected = selectedIndex == 1,

            onClick = onCategoryClick,

            icon = {

                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = "Categories"
                )

            },

            label = {

                Text("Categories")

            },

            colors = NavigationBarItemDefaults.colors()

        )

        NavigationBarItem(

            selected = selectedIndex == 2,

            onClick = onReportClick,

            icon = {

                Icon(
                    imageVector = Icons.Default.PieChart,
                    contentDescription = "Reports"
                )

            },

            label = {

                Text("Reports")

            },

            colors = NavigationBarItemDefaults.colors()

        )

        NavigationBarItem(

            selected = selectedIndex == 3,

            onClick = onSettingClick,

            icon = {

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )

            },

            label = {

                Text("Settings")

            },

            colors = NavigationBarItemDefaults.colors()

        )

    }

}