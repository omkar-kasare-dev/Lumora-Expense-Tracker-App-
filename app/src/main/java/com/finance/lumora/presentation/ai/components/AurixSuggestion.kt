package com.finance.lumora.presentation.ai.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@Composable
fun AurixSuggestion(
    text: String,
    onClick: (String) -> Unit
) {

    Card(
        onClick = {
            onClick(text)
        },
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}