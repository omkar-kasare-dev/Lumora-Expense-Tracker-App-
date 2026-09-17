package com.finance.lumora.presentation.analytics.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter

            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter.displayName,
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.5.sp),
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    )
                },
                modifier = Modifier.height(34.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = if (isSelected) 1.dp else 0.8.dp,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    }
                ),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}