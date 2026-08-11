package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptySearchState() {

    Column(

        modifier =
            Modifier
                .padding(horizontal = 32.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center

    ) {

        // ========================================================
        // SEARCH EMPTY ICON
        // ========================================================

        Surface(

            modifier =
                Modifier.size(76.dp),

            shape =
                CircleShape,

            color =
                MaterialTheme.colorScheme
                    .secondaryContainer

        ) {

            Icon(

                imageVector =
                    Icons.Outlined.SearchOff,

                contentDescription =
                    "No search results",

                modifier =
                    Modifier.padding(22.dp),

                tint =
                    MaterialTheme.colorScheme
                        .onSecondaryContainer

            )
        }


        Spacer(
            modifier =
                Modifier.height(20.dp)
        )


        // ========================================================
        // TITLE
        // ========================================================

        Text(

            text =
                "No Results Found",

            style =
                MaterialTheme.typography
                    .titleLarge,

            color =
                MaterialTheme.colorScheme
                    .onSurface

        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        // ========================================================
        // DESCRIPTION
        // ========================================================

        Text(

            text =
                "Try another keyword",

            style =
                MaterialTheme.typography
                    .bodyMedium,

            color =
                MaterialTheme.colorScheme
                    .onSurfaceVariant

        )
    }


}
