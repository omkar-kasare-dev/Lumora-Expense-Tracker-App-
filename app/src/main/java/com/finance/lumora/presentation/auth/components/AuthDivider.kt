package com.finance.lumora.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthDivider(

    modifier: Modifier = Modifier,

    text: String = "OR"

) {

    Row(

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.Center

    ) {

        HorizontalDivider(

            modifier = Modifier.weight(1f),

            color = MaterialTheme.colorScheme.outlineVariant

        )

        Text(

            text = text,

            modifier = Modifier.padding(horizontal = 16.dp),

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

        HorizontalDivider(

            modifier = Modifier.weight(1f),

            color = MaterialTheme.colorScheme.outlineVariant

        )

    }

}