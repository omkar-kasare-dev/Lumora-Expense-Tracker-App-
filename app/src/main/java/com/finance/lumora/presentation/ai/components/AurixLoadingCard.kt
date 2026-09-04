package com.finance.lumora.presentation.ai.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment


@Composable
fun AurixLoadingCard() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )

                Spacer(
                    modifier = Modifier.size(12.dp)
                )

                Text(
                    text = "AURIX is thinking...",
                    style =
                        MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}