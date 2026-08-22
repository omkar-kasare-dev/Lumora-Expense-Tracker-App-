package com.finance.lumora.presentation.dashboard.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.TopExpenseCategory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TopExpenseCategoryCard(

    topExpenseCategory: TopExpenseCategory?,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )

    ) {

        if (topExpenseCategory == null) {

            EmptyTopExpenseCategory()

        } else {

            TopExpenseContent(
                topExpenseCategory = topExpenseCategory
            )

        }

    }

}

@Composable
private fun TopExpenseContent(

    topExpenseCategory: TopExpenseCategory

) {

    val category = topExpenseCategory.category

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)

    ) {

        //--------------------------------------------------
        // Header
        //--------------------------------------------------

        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(

                text = "Top Expense Category",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold

            )

            Icon(

                imageVector = Icons.Rounded.Analytics,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary

            )

        }

        Spacer(modifier = Modifier.height(24.dp))

        //--------------------------------------------------
        // Category
        //--------------------------------------------------

        Row(

            verticalAlignment = Alignment.CenterVertically

        ) {

            Surface(

                modifier = Modifier.size(56.dp),

                shape = CircleShape,

                color = Color(category.color)

            ) {

                Box(

                    contentAlignment = Alignment.Center

                ) {
/*
                    Text(

                        text = category.icon,

                        style = MaterialTheme.typography.headlineSmall

                    )

 */
                    Icon(
                        imageVector = getIconFromName(category.icon),
                        contentDescription = category.name,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(32.dp) // Gives it clean, deliberate proportions
                    )

                }

            }

            Spacer(modifier = Modifier.size(16.dp))

            Column {

                Text(

                    text = category.name,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

                Text(

                    text = "Highest spending category",

                    style = MaterialTheme.typography.bodyMedium,

                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )

            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        //--------------------------------------------------
        // Amount
        //--------------------------------------------------

        Text(

            text = formatCurrency(
                topExpenseCategory.amount
            ),

            style = MaterialTheme.typography.headlineMedium,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.primary

        )

        Spacer(modifier = Modifier.height(16.dp))

        //--------------------------------------------------
        // Progress
        //--------------------------------------------------

        LinearProgressIndicator(

            progress = { 0.75f },

            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "Represents your highest spending category this month.",

            style = MaterialTheme.typography.bodySmall,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

    }

}

@Composable
private fun EmptyTopExpenseCategory() {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Icon(

            imageVector = Icons.Rounded.Analytics,

            contentDescription = null,

            modifier = Modifier.size(48.dp),

            tint = MaterialTheme.colorScheme.primary

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(

            text = "No Expense Data",

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = "Your top expense category will appear here after you add expense transactions.",

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

    }

}

private fun formatCurrency(

    amount: Double

): String {

    return NumberFormat

        .getCurrencyInstance(

            Locale("en", "IN")

        )

        .format(amount)

}

private fun getIconFromName(iconName: String): ImageVector {
    return when (iconName) {
        "Filled.ShoppingCart" -> Icons.Default.ShoppingCart
        "Filled.Home" -> Icons.Default.Home
        // Add the other string mappings you save in your database here...
        else -> Icons.Default.List // Fallback icon so your app never crashes
    }
}



