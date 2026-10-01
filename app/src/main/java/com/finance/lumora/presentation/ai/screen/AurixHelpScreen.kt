package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val AurixNavDark = Color(0xFF0F2640)
private val AurixSurfaceDark = Color(0xFF133253)
private val AurixAccentColor = Color(0xFF4FC3F7)

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
        containerColor = AurixNavDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Help & Examples",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AurixNavDark)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(examples) { (title, prompts) ->
                Column {
                    Text(
                        text = title,
                        color = AurixAccentColor,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                    )
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AurixSurfaceDark
                    ) {
                        Column(modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)) {
                            prompts.forEachIndexed { index, prompt ->
                                Text(
                                    text = prompt,
                                    color = Color.White.copy(alpha = 0.9f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                                if (index < prompts.lastIndex) {
                                    HorizontalDivider(color = Color.White.copy(alpha = 0.06f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}