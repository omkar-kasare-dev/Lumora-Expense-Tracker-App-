package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
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
private val AurixAccentColor = Color(0xFF4FC3F7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixModelSettingsScreen(
    smartInsightsEnabled: Boolean,
    autoContextEnabled: Boolean,
    onSmartInsightsToggle: (Boolean) -> Unit,
    onAutoContextToggle: (Boolean) -> Unit,
    onClearConversation: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = AurixNavDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            ListItem(
                headlineContent = {
                    Text("Smart Insights", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                },
                supportingContent = {
                    Text(
                        "Show proactive suggestions on the welcome screen",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                trailingContent = {
                    Switch(
                        checked = smartInsightsEnabled,
                        onCheckedChange = onSmartInsightsToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AurixAccentColor,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.06f))

            ListItem(
                headlineContent = {
                    Text("Auto Financial Context", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                },
                supportingContent = {
                    Text(
                        "Let Aurix see your transactions and budget when answering",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                trailingContent = {
                    Switch(
                        checked = autoContextEnabled,
                        onCheckedChange = onAutoContextToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AurixAccentColor,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )

            Spacer(Modifier.weight(1f))

            OutlinedButton(
                onClick = onClearConversation,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF5350))
            ) {
                Text(
                    text = "Clear Current Conversation",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}