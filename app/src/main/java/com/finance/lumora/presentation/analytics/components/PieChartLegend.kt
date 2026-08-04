package com.finance.lumora.presentation.analytics.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.CategorySummary
import java.text.NumberFormat

@Composable
fun PieChartLegend(
    categories: List<CategorySummary>,
    modifier: Modifier = Modifier
) {

    if (categories.isEmpty()) {

        Text(
            text = "No expense data available.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        return
    }

    Column(

        modifier = modifier.fillMaxWidth(),

        verticalArrangement = Arrangement.spacedBy(18.dp)

    ) {

        categories.forEach {

            PieChartLegendItem(it)

        }

    }

}

@Composable
private fun PieChartLegendItem(
    category: CategorySummary
) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically

    ) {

        //----------------------------------------
        // Color Indicator
        //----------------------------------------

        Box(

            modifier = Modifier
                .size(14.dp)
                .background(
                    Color(category.color),
                    CircleShape
                )

        )

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        //----------------------------------------
        // Category Details
        //----------------------------------------

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(

                text = category.categoryName,

                style = MaterialTheme.typography.bodyMedium,

                fontWeight = FontWeight.Medium

            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(

                text = String.format("%.1f%%", category.percentage),

                style = MaterialTheme.typography.bodySmall,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

        }

        //----------------------------------------
        // Amount
        //----------------------------------------

        Text(

            text = category.totalAmount.toCurrency(),

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.Bold

        )

    }

}

fun Double.toCurrency(): String {

    return NumberFormat
        .getCurrencyInstance()
        .format(this)

}