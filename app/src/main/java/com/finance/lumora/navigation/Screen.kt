package com.finance.lumora.navigation



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


    data object Analytics : Screen("analytics")

    object Splash : Screen("splash")
    object Search : Screen("search")

    object SetBudget: Screen("set_budget")
    data object ChangePassword : Screen("change_password")
    object TermsOfService:Screen("Terms_Of_Service")
    object AppVersionScreen: Screen("AppVersionScreen")

    data object GeminiTest : Screen("gemini_test")
    data object Aurix : Screen("aurix")

}