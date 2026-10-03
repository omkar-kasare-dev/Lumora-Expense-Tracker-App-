package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.presentation.ai.theme.AurixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixHistoryScreen(
    messages: List<ChatMessage>,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = AurixTheme.NavDark,
        topBar = {
            TopAppBar(
                title = { Text("Conversation History", style = AurixTheme.Typography.titleLarge) },
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
        if (messages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.History,
                        contentDescription = null,
                        tint = AurixTheme.TextMuted,
                        modifier = Modifier.size(AurixTheme.IconSizeLarge)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "No conversation yet this session.",
                        style = AurixTheme.Typography.bodySmall,
                        color = AurixTheme.TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(AurixTheme.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        "Showing this session's conversation. Past sessions aren't saved yet.",
                        style = AurixTheme.Typography.bodySmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                items(messages, key = { it.id }) { message ->
                    val isUser = message.role == ChatMessageRole.USER
                    Surface(
                        shape = AurixTheme.ContainerShape,
                        color = if (isUser) AurixTheme.AccentColor.copy(alpha = 0.12f) else AurixTheme.SurfaceDark
                    ) {
                        Column(modifier = Modifier.padding(AurixTheme.CardPadding)) {
                            Text(
                                text = if (isUser) "You" else "Aurix",
                                style = AurixTheme.Typography.labelSmall
                            )
                            Spacer(Modifier.height(3.dp))
                            Text(
                                text = message.content,
                                style = AurixTheme.Typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}