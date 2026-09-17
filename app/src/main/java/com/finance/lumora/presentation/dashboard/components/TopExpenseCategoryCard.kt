package com.finance.lumora.presentation.dashboard.components




import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.domain.model.TopExpenseCategory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TopExpenseCategoryCard(
    topExpenseCategory: TopExpenseCategory?,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = 0.8.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
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
    val rawCategoryColor = Color(category.color)

    // Ensure icon and background tints maintain high contrast regardless of surface color
    val iconBgColor = if (category.color == 0L || category.color.toInt() == 0) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    } else {
        rawCategoryColor.copy(alpha = 0.15f)
    }

    val iconTint = if (category.color == 0L || category.color.toInt() == 0) {
        MaterialTheme.colorScheme.primary
    } else {
        rawCategoryColor
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //--------------------------------------------------
        // Section Header
        //--------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Analytics,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "Top Expense Category",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        letterSpacing = 0.2.sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            ) {
                Text(
                    text = "This Month",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        //--------------------------------------------------
        // Category Avatar & Metric Info
        //--------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getIconFromName(category.icon),
                        contentDescription = category.name,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 15.sp,
                            letterSpacing = (-0.1).sp
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Highest spending category",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            //--------------------------------------------------
            // Amount Display
            //--------------------------------------------------
            Text(
                text = formatCurrency(topExpenseCategory.amount),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    letterSpacing = (-0.3).sp
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //--------------------------------------------------
        // Progress Representation
        //--------------------------------------------------
        LinearProgressIndicator(
            progress = { 0.75f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = iconTint,
            trackColor = iconBgColor,
            strokeCap = StrokeCap.Round
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Represents your highest spending category this month.",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}

//--------------------------------------------------
// Refined Minimalist Empty State
//--------------------------------------------------
@Composable
private fun EmptyTopExpenseCategory() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(44.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.Analytics,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "No Expense Data",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Your top expense category will appear here after adding transactions.",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f)
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale("en", "IN"))
        .format(amount)
}

private fun getIconFromName(iconName: String): ImageVector {
    return when (iconName) {
        "Filled.ShoppingCart" -> Icons.Default.ShoppingCart
        "Filled.Home" -> Icons.Default.Home
        else -> Icons.Default.List
    }
}