package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.ai.theme.AurixTheme

private val examples = listOf(
    "Capturing transactions" to listOf(
        "\"I spent 450 on groceries at D-Mart\" (voice)",
        "Snap a photo of a receipt",
        "\"I received 25000 salary today\""
    ),
    "Asking about your spending" to listOf(
        "How much did I spend this month?",
        "How much did I spend today?",
        "How much did I spend in August?",
        "What's my largest spending category?"
    ),
    "Budget" to listOf(
        "Am I over budget?",
        "How much budget do I have left?",
        "What is my monthly budget?"
    ),
    "Trends" to listOf(
        "Is my spending increasing or decreasing?",
        "How does this month compare to last month?"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixHelpScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = AurixTheme.NavDark,
        topBar = {
            TopAppBar(
                title = { Text("Help & Prompt Examples", style = AurixTheme.Typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AurixTheme.TextPrimary,
                            modifier = Modifier.size(AurixTheme.IconSizeMedium)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AurixTheme.NavDark)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(AurixTheme.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(examples) { (title, prompts) ->
                Text(
                    text = title,
                    style = AurixTheme.Typography.labelSmall,
                    modifier = Modifier.padding(start = 2.dp)
                )
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = AurixTheme.ContainerShape,
                    color = AurixTheme.SurfaceDark
                ) {
                    Column(modifier = Modifier.padding(AurixTheme.CardPadding)) {
                        prompts.forEachIndexed { index, prompt ->
                            Text(
                                text = "• $prompt",
                                style = AurixTheme.Typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                            if (index < prompts.lastIndex) {
                                HorizontalDivider(
                                    color = AurixTheme.DividerColor,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}