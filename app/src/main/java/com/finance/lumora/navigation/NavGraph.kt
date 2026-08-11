package com.finance.lumora.navigation

/*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.presentation.analytics.screen.AnalyticsScreen
import com.finance.lumora.presentation.auth.screen.ForgotPasswordScreen
import com.finance.lumora.presentation.auth.screen.LoginScreen
import com.finance.lumora.presentation.auth.screen.RegisterScreen
import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.dashboard.screen.DashboardScreen
import com.finance.lumora.presentation.notification.NotificationScreen
import com.finance.lumora.presentation.profile.ProfileScreen
import com.finance.lumora.presentation.profile.viewmodel.ProfileViewModel
import com.finance.lumora.presentation.search.screen.SearchScreen

import com.finance.lumora.presentation.settings.PrivacyPolicyScreen
import com.finance.lumora.presentation.settings.SettingsScreen
import com.finance.lumora.presentation.splash.SplashScreen
import com.finance.lumora.presentation.transaction.screen.TransactionScreen
import java.util.concurrent.TimeUnit

@Composable
fun LumoraNavGraph(

    modifier: Modifier = Modifier

) {

    val navController = rememberNavController()

    NavHost(

        navController = navController,

        startDestination = Screen.Splash.route,

        modifier = modifier

    ) {
         //--------------------------------------------------
        // SplashScreen
        //--------------------------------------------------
        composable(
            Screen.Splash.route
        ) {

            SplashScreen(
                navController = navController
            )

        }

        //--------------------------------------------------
        // Auth
        //------------------------------------------

            /**
             * Login
             */

            composable(Screen.Login.route) {

                LoginScreen(

                    onNavigateToRegister = {

                        navController.navigate(
                            Screen.Register.route
                        )

                    },

                    onNavigateToHome = {

                        navController.navigate(
                            Screen.Dashboard.route
                        ) {

                            popUpTo(Screen.Login.route) {

                                inclusive = true

                            }

                        }

                    },

                    onForgotPassword = {

                        navController.navigate(
                            Screen.ForgotPassword.route
                        )

                    }

                )

            }

            /**
             * Register
             */

            composable(Screen.Register.route) {

                RegisterScreen(

                    onNavigateToLogin = {

                        navController.popBackStack()

                    },

                    onRegisterSuccess = {

                        navController.navigate(
                            Screen.Dashboard.route
                        ) {

                            popUpTo(Screen.Register.route) {

                                inclusive = true

                            }

                        }

                    }

                )

            }

        composable(
            route = Screen.ForgotPassword.route
        ) {

            ForgotPasswordScreen(

                onNavigateBack = {

                    navController.popBackStack()

                },

                onResetEmailSent = {

                    navController.popBackStack()

                }

            )

        }


/*
            composable(Screen.Dashboard.route) {

                DashboardScreen(
                    navController = navController
                )

            }

 */


        //--------------------------------------------------


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
                onBackClick = {

                    navController.popBackStack()

                },
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
            CategoryScreen(onBackClick = {

                navController.popBackStack()

            },navController)
        }

        //--------------------------------------------------
        // Analytics route setup
        //--------------------------------------------------

        composable(

            route = Screen.Analytics.route

        ) {

            AnalyticsScreen( onBackClick = {

                navController.popBackStack()

            }, navController = navController)

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


        composable(route = Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEditProfileClick = {
                    navController.navigate(Screen.EditProfile.route)
                },
               onLogoutClick = {
                   profileViewModel.logout()
                   navController.navigate(Screen.Login.route) {

                       popUpTo(0) {
                           inclusive = true
                       }

                       launchSingleTop = true
                   }
               }
            )
        }


        // Notification section:

        val sampleNotifications = listOf(
            NotificationItem(
                title = "Large Expense Warning",
                message = "You spent ₹14,500 at Electronics Hub. You've reached 85% of your monthly shopping budget.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(25),
                type = NotificationType.TRANSACTION_ALERT,
                isRead = false
            ),
            NotificationItem(
                title = "New Device Login Detected",
                message = "Your Lumora account was accessed from Chrome on Windows (Mumbai, India).",
                timestampMillis = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3),
                type = NotificationType.SECURITY,
                isRead = false
            ),
            NotificationItem(
                title = "Salary Credited 🎉",
                message = "₹85,000 credited to your HDFC Bank account ****4102.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(18),
                type = NotificationType.TRANSACTION_ALERT,
                isRead = true
            ),
            NotificationItem(
                title = "System Maintenance",
                message = "Lumora cloud sync will be undergoing scheduled maintenance tonight from 2:00 AM to 3:00 AM IST.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2),
                type = NotificationType.SYSTEM,
                isRead = true
            )
        )


        composable(route = Screen.Notifications.route) {
            NotificationScreen(
                notifications = sampleNotifications,
                onBackClick = { navController.popBackStack() },
                onNotificationClick = { notification ->
                    // Handle clicking a specific notification (e.g. open transaction details)
                },
                onMarkAllAsReadClick = {
                    // Update ViewModel state
                },
                onClearAllClick = {
                    // Clear notifications
                }
            )
        }

        // Settings screen

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onChangePasswordClick = {
                    // Navigate to Password Reset / Change screen
                },
                onExportDataClick = {
                    // Trigger CSV/PDF export logic
                },
                onPrivacyPolicyClick = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                }
            )
        }

        // privacy policy
        composable(route = Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() },
                onContactSupportClick = {
                    // Open email intent or contact screen
                }
            )
        }

        // Search Screen Navigation:
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }



    }

}

 */



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.presentation.analytics.screen.AnalyticsScreen
import com.finance.lumora.presentation.auth.screen.ForgotPasswordScreen
import com.finance.lumora.presentation.auth.screen.LoginScreen
import com.finance.lumora.presentation.auth.screen.RegisterScreen
import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.dashboard.screen.DashboardScreen
import com.finance.lumora.presentation.notification.NotificationScreen
import com.finance.lumora.presentation.profile.ProfileScreen
import com.finance.lumora.presentation.profile.viewmodel.ProfileViewModel
import com.finance.lumora.presentation.search.screen.SearchScreen
import com.finance.lumora.presentation.settings.PrivacyPolicyScreen
import com.finance.lumora.presentation.settings.SettingsScreen
import com.finance.lumora.presentation.splash.SplashScreen
import com.finance.lumora.presentation.transaction.screen.TransactionScreen
import java.util.concurrent.TimeUnit

@Composable
fun LumoraNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        //--------------------------------------------------
        // SplashScreen
        //--------------------------------------------------
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        //--------------------------------------------------
        // Auth
        //--------------------------------------------------
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onResetEmailSent = {
                    navController.popBackStack()
                }
            )
        }

        //--------------------------------------------------
        // Dashboard
        //--------------------------------------------------
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        //--------------------------------------------------
        // Transactions
        //--------------------------------------------------
        composable(route = Screen.Transactions.route) {
            TransactionScreen(
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        //--------------------------------------------------
        // Add Transaction
        //--------------------------------------------------
        composable(route = Screen.AddTransaction.route) {
            // TODO: AddTransactionScreen(navController)
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
            val transactionId = it.arguments?.getLong("transactionId") ?: -1L
            // TODO: TransactionDetailsScreen(transactionId = transactionId)
        }

        //--------------------------------------------------
        // Categories
        //--------------------------------------------------
        composable(Screen.Categories.route) {
            CategoryScreen(
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        //--------------------------------------------------
        // Analytics
        //--------------------------------------------------
        composable(route = Screen.Analytics.route) {
            AnalyticsScreen(
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        //--------------------------------------------------
        // Profile
        //--------------------------------------------------
        composable(route = Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEditProfileClick = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onLogoutClick = {
                    profileViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        // ✅ FIX: Safely pop up to the start destination (Splash) inclusively
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        //--------------------------------------------------
        // Notifications
        //--------------------------------------------------
        val sampleNotifications = listOf(
            NotificationItem(
                title = "Large Expense Warning",
                message = "You spent ₹14,500 at Electronics Hub. You've reached 85% of your monthly shopping budget.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(25),
                type = NotificationType.TRANSACTION_ALERT,
                isRead = false
            ),
            NotificationItem(
                title = "New Device Login Detected",
                message = "Your Lumora account was accessed from Chrome on Windows (Mumbai, India).",
                timestampMillis = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3),
                type = NotificationType.SECURITY,
                isRead = false
            ),
            NotificationItem(
                title = "Salary Credited 🎉",
                message = "₹85,000 credited to your HDFC Bank account ****4102.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(18),
                type = NotificationType.TRANSACTION_ALERT,
                isRead = true
            ),
            NotificationItem(
                title = "System Maintenance",
                message = "Lumora cloud sync will be undergoing scheduled maintenance tonight from 2:00 AM to 3:00 AM IST.",
                timestampMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2),
                type = NotificationType.SYSTEM,
                isRead = true
            )
        )

        composable(route = Screen.Notifications.route) {
            NotificationScreen(
                notifications = sampleNotifications,
                onBackClick = { navController.popBackStack() },
                onNotificationClick = { notification -> },
                onMarkAllAsReadClick = { },
                onClearAllClick = { }
            )
        }

        //--------------------------------------------------
        // Settings (✅ Duplicate entry removed)
        //--------------------------------------------------
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onChangePasswordClick = { },
                onExportDataClick = { },
                onPrivacyPolicyClick = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                }
            )
        }

        //--------------------------------------------------
        // Privacy Policy
        //--------------------------------------------------
        composable(route = Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() },
                onContactSupportClick = { }
            )
        }

        //--------------------------------------------------
        // Search
        //--------------------------------------------------
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}