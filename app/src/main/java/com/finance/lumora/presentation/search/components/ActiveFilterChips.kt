package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.search.model.SearchFilters
import com.finance.lumora.presentation.search.state.FilterChipType

@Composable
fun ActiveFilterChips(
    filters: SearchFilters,
    categoryName: String?,
    onRemoveFilter: (FilterChipType) -> Unit
) {


    val chips = mutableListOf<Pair<FilterChipType, String>>()

// Transaction Type
    filters.transactionType?.let { type ->

        chips.add(
            FilterChipType.TransactionType to type.name
        )

    }

// Category
    filters.categoryId?.let {

        chips.add(
            FilterChipType.Category to (
                    categoryName ?: "Category"
                    )
        )

    }

// Minimum Amount
    filters.minAmount?.let { amount ->

        chips.add(
            FilterChipType.MinAmount to
                    "Min ₹${formatAmount(amount)}"
        )

    }

// Maximum Amount
    filters.maxAmount?.let { amount ->

        chips.add(
            FilterChipType.MaxAmount to
                    "Max ₹${formatAmount(amount)}"
        )

    }

// Date Range
    if (
        filters.startDate != null ||
        filters.endDate != null
    ) {

        chips.add(
            FilterChipType.DateRange to "Date Range"
        )

    }

// Nothing selected
    if (chips.isEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(
                rememberScrollState()
            )
            .padding(vertical = 4.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        chips.forEach { (chipType, label) ->

            AssistChip(

                onClick = {
                    onRemoveFilter(chipType)
                },

                label = {

                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge
                    )

                },

                trailingIcon = {

                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Remove $label"
                    )

                },

                colors = AssistChipDefaults.assistChipColors()

            )

        }

    }


}

/**

 * Formats amount values for filter chips.
 *
 * Example:
 * 500.0 -> 500
 * 1250.50 -> 1250.50
 */
private fun formatAmount(
    amount: Double
): String {

    return if (amount % 1.0 == 0.0) {


        amount.toLong().toString()


    } else {


        amount.toString()

    }

}
