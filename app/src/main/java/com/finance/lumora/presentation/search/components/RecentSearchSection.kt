package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecentSearchSection(
    recentSearches: List<String>,
    onSearchClick: (String) -> Unit
) {

    if (recentSearches.isEmpty()) return

    Text(
        text = "Recent Searches",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(12.dp))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        recentSearches.forEach { search ->

            SuggestionChip(
                onClick = {
                    onSearchClick(search)
                },
                label = {
                    Text(search)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = null
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors()
            )

        }

    }

}