package com.finance.lumora.navigation



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