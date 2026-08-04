package com.finance.lumora.presentation.analytics.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.AnalyticsFilter

@Composable
fun AnalyticsFilterRow(
    selectedFilter: AnalyticsFilter,
    onFilterSelected: (AnalyticsFilter) -> Unit,
    modifier: Modifier = Modifier
) {

    val filters = listOf(
        AnalyticsFilter.TODAY,
        AnalyticsFilter.THIS_WEEK,
        AnalyticsFilter.THIS_MONTH,
        AnalyticsFilter.LAST_MONTH,
        AnalyticsFilter.THIS_YEAR,
        AnalyticsFilter.CUSTOM
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        filters.forEach { filter ->

            FilterChip(

                selected = filter == selectedFilter,

                onClick = {
                    onFilterSelected(filter)
                },

                label = {

                    Text(
                        text = filter.displayName
                    )

                },

                colors = FilterChipDefaults.filterChipColors(

                    selectedContainerColor =
                        MaterialTheme.colorScheme.primaryContainer,

                    selectedLabelColor =
                        MaterialTheme.colorScheme.onPrimaryContainer

                )

            )

        }

    }

}

