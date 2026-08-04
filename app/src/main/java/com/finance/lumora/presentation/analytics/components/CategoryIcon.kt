package com.finance.lumora.presentation.analytics.components


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalGasStation
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.CategorySummary

@Composable
fun CategoryIcon(
    category: CategorySummary
) {

    Icon(
        imageVector = category.icon.toImageVector(),
        contentDescription = category.categoryName,
        tint = MaterialTheme.colorScheme.primary,
        modifier = androidx.compose.ui.Modifier.size(22.dp)
    )
}

private fun String.toImageVector(): ImageVector {

    return when (this) {

        "shopping_cart" -> Icons.Rounded.ShoppingCart

        "restaurant" -> Icons.Rounded.Restaurant

        "fastfood" -> Icons.Rounded.Fastfood

        "directions_car" -> Icons.Rounded.DirectionsCar

        "local_gas_station" -> Icons.Rounded.LocalGasStation

        "movie" -> Icons.Rounded.Movie

        "sports_esports" -> Icons.Rounded.SportsEsports

        "home" -> Icons.Rounded.Home

        "school" -> Icons.Rounded.School

        "work" -> Icons.Rounded.Work

        "favorite" -> Icons.Rounded.Favorite

        "payments" -> Icons.Rounded.Payments

        "attach_money" -> Icons.Rounded.AttachMoney

        "savings" -> Icons.Rounded.Savings

        "account_balance" -> Icons.Rounded.AccountBalance

        "directions_bus" -> Icons.Rounded.DirectionsBus

        "build" -> Icons.Rounded.Build

        else -> Icons.Rounded.Category
    }
}