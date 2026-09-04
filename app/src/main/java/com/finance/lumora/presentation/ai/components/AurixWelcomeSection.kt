package com.finance.lumora.presentation.ai.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AurixWelcomeSection(
    onQuestionSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                bottom = 16.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Hi, I'm AURIX",
            style =
                MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text =
                "I can help you understand your spending, " +
                        "budget, and financial habits using your " +
                        "Lumora data.",
            style =
                MaterialTheme.typography.bodyLarge,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "Try asking:",
            style =
                MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        AurixSuggestion(
            text = "How much did I spend this month?",
            onClick = onQuestionSelected
        )

        AurixSuggestion(
            text = "Where did most of my money go?",
            onClick = onQuestionSelected
        )

        AurixSuggestion(
            text = "Am I within my budget?",
            onClick = onQuestionSelected
        )

        AurixSuggestion(
            text = "How can I save more money?",
            onClick = onQuestionSelected
        )
    }
}