package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onFilterClick: () -> Unit
) {


    Surface(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        color =
            MaterialTheme.colorScheme
                .surfaceContainerHigh,

        tonalElevation = 1.dp

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 14.dp,
                    end = 8.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.spacedBy(4.dp)

        ) {

            // ========================================================
            // SEARCH ICON
            // ========================================================

            Icon(

                imageVector =
                    Icons.Outlined.Search,

                contentDescription =
                    "Search",

                modifier =
                    Modifier.size(22.dp),

                tint =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant

            )


            Spacer(
                modifier =
                    Modifier.size(4.dp)
            )


            // ========================================================
            // SEARCH INPUT
            // ========================================================

            androidx.compose.material3.OutlinedTextField(

                value = query,

                onValueChange =
                    onQueryChange,

                modifier =
                    Modifier.weight(1f),

                singleLine = true,

                placeholder = {

                    Text(

                        text =
                            "Search transactions...",

                        style =
                            MaterialTheme.typography
                                .bodyLarge,

                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant

                    )
                },

                leadingIcon = null,

                trailingIcon = null,

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    androidx.compose.material3
                        .OutlinedTextFieldDefaults
                        .colors(

                            focusedContainerColor =
                                MaterialTheme.colorScheme
                                    .surfaceContainerHigh,

                            unfocusedContainerColor =
                                MaterialTheme.colorScheme
                                    .surfaceContainerHigh,

                            focusedBorderColor =
                                MaterialTheme.colorScheme
                                    .surfaceContainerHigh,

                            unfocusedBorderColor =
                                MaterialTheme.colorScheme
                                    .surfaceContainerHigh

                        )

            )


            // ========================================================
            // CLEAR BUTTON
            // ========================================================

            if (query.isNotEmpty()) {

                IconButton(

                    onClick =
                        onClear,

                    modifier =
                        Modifier.size(40.dp)

                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Clear,

                        contentDescription =
                            "Clear search",

                        modifier =
                            Modifier.size(20.dp),

                        tint =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant

                    )
                }
            }


            // ========================================================
            // FILTER BUTTON
            // ========================================================

            Surface(

                modifier =
                    Modifier
                        .size(42.dp)
                        .clip(
                            RoundedCornerShape(14.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme
                                .primaryContainer
                        ),

                shape =
                    RoundedCornerShape(14.dp),

                color =
                    MaterialTheme.colorScheme
                        .primaryContainer

            ) {

                IconButton(

                    onClick =
                        onFilterClick,

                    modifier =
                        Modifier.size(42.dp)

                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Tune,

                        contentDescription =
                            "Filters",

                        modifier =
                            Modifier.size(21.dp),

                        tint =
                            MaterialTheme.colorScheme
                                .onPrimaryContainer

                    )
                }
            }
        }
    }
}