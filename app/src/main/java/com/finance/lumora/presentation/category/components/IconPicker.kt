package com.finance.lumora.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IconPicker(
    selectedIcon: String,
    onIconSelected: (String) -> Unit
) {

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 4,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        CategoryIcons.forEach { item ->

            val selected = item.name == selectedIcon

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            Color.Transparent
                    )
                    .border(
                        width = if (selected) 2.dp else 1.dp,
                        color = if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable {
                        onIconSelected(item.name)
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )

            }

        }

    }

}