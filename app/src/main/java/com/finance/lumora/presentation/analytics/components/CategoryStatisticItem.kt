package com.finance.lumora.presentation.analytics.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.CategorySummary

@Composable
fun CategoryStatisticItem(
    category: CategorySummary
){
    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            CategoryIcon(category)

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = category.categoryName,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = category.totalAmount.toCurrency()
            )

        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        CategoryProgressBar(category)

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = String.format(
                "%.1f%%",
                category.percentage
            )
        )

    }
}

