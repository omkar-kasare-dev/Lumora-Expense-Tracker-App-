package com.finance.lumora.presentation.home

/*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.Screen
//import com.finance.lumora.presentation.home.components.BalanceCard
import com.finance.lumora.presentation.home.components.DateSelector
import com.finance.lumora.presentation.home.components.GreetingSection
import com.finance.lumora.presentation.home.components.HomeBottomBar
import com.finance.lumora.presentation.home.components.HomeTopBar
import com.finance.lumora.presentation.home.components.QuickActionSection
import com.finance.lumora.presentation.home.components.RecentTransactionSection
import com.finance.lumora.presentation.drawer.LumoraNavigationDrawer

@Composable
fun HomeScreen(
    navController: NavHostController

) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    LumoraNavigationDrawer(

        drawerState = drawerState,

        selectedRoute = "home",

        onHomeClick = {
            // Already on Home
        },

        onTransactionClick = {
            // TODO
        },

        onCategoryClick = {
            navController.navigate(Screen.Categories.route)
        },

        onSettingsClick = {
            // TODO
        },

        onAboutClick = {
            // TODO
        },

        onRateAppClick = {
            // TODO
        }

    ) {

        Scaffold(

            topBar = {

                HomeTopBar(

                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },

                    onNotificationClick = {

                        // TODO

                    },

                    onProfileClick = {
                        navController.navigate(
                            Screen.Profile.route
                        )



                    }

                )

            },

            bottomBar = {

                HomeBottomBar(

                    selectedIndex = 0,

                    onHomeClick = {

                    },

                    onCategoryClick = {

                        navController.navigate(
                            Screen.Categories.route
                        )

                    },

                    onReportClick = {
                        navController.navigate(
                            Screen.Transaction.route
                        )



                    },

                    onSettingClick = {

                        // TODO

                    }

                )

            }

        ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(vertical = 16.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            //--------------------------------------------------
            // Greeting

            GreetingSection(

                userName = "Omkar",

                greeting = "Good Afternoon"

            )

            //--------------------------------------------------
            // Date Selector
            //--------------------------------------------------

            DateSelector(

                currentDate = "July 2026",

                onPreviousClick = {

                    // TODO

                },

                onNextClick = {

                    // TODO

                }

            )

            //--------------------------------------------------
            // Balance Card
            //--------------------------------------------------
/*
            BalanceCard(

                totalBalance = "₹25,000",

                totalIncome = "₹40,000",

                totalExpense = "₹15,000"

            )

 */

            //--------------------------------------------------
            // Quick Actions
            //--------------------------------------------------

            QuickActionSection(

                onAddExpense = {
                    navController.navigate(
                        Screen.Categories.route
                    )



                },

                onAddIncome = {

                    // TODO

                },

                onAddGoal = {

                    navController.navigate(
                        Screen.Categories.route
                    )

                },

                onAddNote = {

                    // TODO

                }



            )

            // Recent Transactions
/*
            RecentTransactionSection(

                onSeeAllClick = {

                    // TODO Transaction Screen

                },

                onTransactionClick = {

                    // TODO Transaction Details

                }

            )  */


            Spacer(
                modifier = Modifier.height(12.dp)
            )

        }

    }

}
}

 */