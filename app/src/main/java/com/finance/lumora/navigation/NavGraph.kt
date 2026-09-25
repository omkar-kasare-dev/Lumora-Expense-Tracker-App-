package com.finance.lumora.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.presentation.ai.screen.AurixScreen
import com.finance.lumora.presentation.analytics.screen.AnalyticsScreen
import com.finance.lumora.presentation.auth.screen.ChangePasswordScreen
import com.finance.lumora.presentation.auth.screen.ForgotPasswordScreen
import com.finance.lumora.presentation.auth.screen.LoginScreen
import com.finance.lumora.presentation.auth.screen.RegisterScreen
import com.finance.lumora.presentation.category.screen.CategoryScreen
import com.finance.lumora.presentation.dashboard.screen.DashboardScreen
import com.finance.lumora.presentation.news.screen.NewsScreen
import com.finance.lumora.presentation.notification.NotificationRoute
import com.finance.lumora.presentation.profile.components.EditProfileRoute
import com.finance.lumora.presentation.profile.screen.ProfileScreen
import com.finance.lumora.presentation.profile.viewmodel.ProfileViewModel
import com.finance.lumora.presentation.search.screen.SearchScreen
import com.finance.lumora.presentation.settings.PrivacyPolicyScreen
import com.finance.lumora.presentation.settings.components.AppVersionScreen
import com.finance.lumora.presentation.settings.components.SettingsRoute
import com.finance.lumora.presentation.settings.components.TermsOfServiceScreen
import com.finance.lumora.presentation.settings.screen.SetBudgetScreen
import com.finance.lumora.presentation.splash.SplashScreen
import com.finance.lumora.presentation.transaction.screen.TransactionScreen

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
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
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
                        popUpTo(navController.graph.id) {
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

        composable(route = Screen.Notifications.route) {
            NotificationRoute(
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = { notification ->
                    when (notification.type) {

                        NotificationType.TRANSACTION_ALERT,
                        NotificationType.INCOME_ADDED,
                        NotificationType.LARGE_EXPENSE_WARNING -> {
                            navController.navigate(Screen.Transactions.route)
                        }

                        NotificationType.BUDGET_ALERT -> {
                            navController.navigate(Screen.SetBudget.route)
                        }

                        NotificationType.SECURITY -> {
                            navController.navigate(Screen.Settings.route)
                        }

                        NotificationType.SYSTEM -> {
                            navController.navigate(Screen.AppVersionScreen.route)
                        }

                        NotificationType.PROMOTION -> {
                            navController.navigate(Screen.Dashboard.route)
                        }
                    }
                }
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
                    navController.navigate(Screen.ChangePassword.route)
                },

                onPrivacyPolicyClick = {
                    navController.navigate(
                        Screen.PrivacyPolicy.route
                    )
                },//----------------


                onTermsClick = {
                    navController.navigate(Screen.TermsOfService.route)
                },

                onAppVersionClick = {
                    navController.navigate(Screen.AppVersionScreen.route)
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

        // Change Password Screen :
        composable(
            route = Screen.ChangePassword.route
        ) {
            ChangePasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPasswordChanged = {
                    navController.popBackStack()
                }
            )
        }

        // Terms of service:

        composable(route = Screen.TermsOfService.route) {
            TermsOfServiceScreen(
                onBackClick = { navController.popBackStack() },
                onContactSupportClick = { }
            )
        }

        // APP Version:
        composable(route= Screen.AppVersionScreen.route){
            AppVersionScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        //--------------------------------------------------
        // AURIX -
        composable(route = Screen.Aurix.route) {
            AurixScreen()
        }

        // News API
        composable(Screen.News.route) {
            NewsScreen(onBackClick = { navController.popBackStack() })
        }

    }
}