package com.finance.lumora.navigation
/* Main
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(

    navController: NavHostController

) {

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentDestination =
        navBackStackEntry.value?.destination

    NavigationBar {

        BottomNavItem.items.forEach { item ->

            val selected =

                currentDestination
                    ?.hierarchy
                    ?.any {

                        it.route == item.route

                    } == true

            NavigationBarItem(

                selected = selected,

                onClick = {

                    navController.navigate(item.route) {

                        //--------------------------------------------------
                        // Avoid multiple copies
                        //--------------------------------------------------

                        launchSingleTop = true

                        //--------------------------------------------------
                        // Restore previous state
                        //--------------------------------------------------

                        restoreState = true

                        //--------------------------------------------------
                        // Pop to graph start
                        //--------------------------------------------------

                        popUpTo(
                            navController.graph.startDestinationId
                        ) {

                            saveState = true

                        }

                    }

                },

                icon = {

                    Icon(

                        imageVector = item.icon,

                        contentDescription = item.title

                    )

                },

                label = {

                    Text(item.title)

                }

            )

        }

    }

}

 */



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 2.dp
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            tonalElevation = 0.dp
        ) {
            BottomNavItem.items.forEach { item ->
                val selected = currentDestination
                    ?.hierarchy
                    ?.any { it.route == item.route } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            // Avoid multiple copies on top of back stack
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                            // Pop up to the start destination of the graph to avoid building up a large stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 11.sp,
                                letterSpacing = 0.2.sp
                            ),
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f)
                    )
                )
            }
        }
    }
}