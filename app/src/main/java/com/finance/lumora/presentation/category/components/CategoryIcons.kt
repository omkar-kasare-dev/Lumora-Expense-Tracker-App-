package com.finance.lumora.presentation.category.components



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryIconItem(
    val name: String,
    val icon: ImageVector
)

val CategoryIcons = listOf(

    CategoryIconItem(
        name = "restaurant",
        icon = Icons.Default.Restaurant
    ),

    CategoryIconItem(
        name = "shopping_cart",
        icon = Icons.Default.ShoppingCart
    ),

    CategoryIconItem(
        name = "payments",
        icon = Icons.Default.Payments
    ),

    CategoryIconItem(
        name = "wallet",
        icon = Icons.Default.AccountBalanceWallet
    ),

    CategoryIconItem(
        name = "home",
        icon = Icons.Default.Home
    ),

    CategoryIconItem(
        name = "hospital",
        icon = Icons.Default.LocalHospital
    ),

    CategoryIconItem(
        name = "education",
        icon = Icons.Default.School
    ),

    CategoryIconItem(
        name = "travel",
        icon = Icons.Default.DirectionsBus
    ),

    CategoryIconItem(
        name = "gift",
        icon = Icons.Default.CardGiftcard
    ),

    CategoryIconItem(
        name = "game",
        icon = Icons.Default.SportsEsports
    ),

    CategoryIconItem(
        name = "fitness",
        icon = Icons.Default.FitnessCenter
    ),

    CategoryIconItem(
        name = "salary",
        icon = Icons.Default.Work
    ),

    CategoryIconItem(
        name = "saving",
        icon = Icons.Default.Savings
    )
)