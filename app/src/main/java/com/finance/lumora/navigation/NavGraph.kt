package com.finance.lumora.navigation

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
import com.finance.lumora.presentation.profile.components.EditProfileRoute
import com.finance.lumora.presentation.profile.screen.ProfileScreen
import com.finance.lumora.presentation.profile.viewmodel.ProfileViewModel
import com.finance.lumora.presentation.search.screen.SearchScreen
import com.finance.lumora.presentation.settings.PrivacyPolicyScreen
import com.finance.lumora.presentation.settings.components.SettingsRoute
import com.finance.lumora.presentation.settings.screen.SetBudgetScreen
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

            SettingsRoute(

                onBackClick = {
                    navController.popBackStack()
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onSetBudgetClick = {
                    navController.navigate(Screen.SetBudget.route)
                },

                onChangePasswordClick = {
                    // TODO: Phase 10.6
                },

                onExportDataClick = {
                    // TODO: Phase 10.7
                },

                onClearCacheClick = {
                    // TODO: Phase 10.7
                },

                onPrivacyPolicyClick = {
                    navController.navigate(
                        Screen.PrivacyPolicy.route
                    )
                },//----------------


                onTermsClick = {
                    // TODO: Terms & Legal implementation
                },

                appVersion = "1.0.0"

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

        // --------------->
        //--------------------------------------------------
// Edit Profile
//--------------------------------------------------
        composable(route = Screen.EditProfile.route) {

            EditProfileRoute(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // In your NavHost / AppNavigation.kt
        composable(Screen.SetBudget.route) {
            SetBudgetScreen(
                onBackClick = { navController.popBackStack() },
                onSavedSuccessfully = {
                    navController.popBackStack()
                }
            )
        }
    }
}