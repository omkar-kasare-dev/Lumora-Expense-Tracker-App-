package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.ai.theme.AurixTheme

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
        containerColor = AurixTheme.NavDark,
        topBar = {
            TopAppBar(
                title = { Text("Model Settings", style = AurixTheme.Typography.titleLarge) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AurixTheme.ScreenPadding)
        ) {
            ListItem(
                headlineContent = { Text("Smart Insights", style = AurixTheme.Typography.titleMedium) },
                supportingContent = {
                    Text("Show proactive suggestions on the welcome screen", style = AurixTheme.Typography.bodySmall)
                },
                trailingContent = {
                    Switch(
                        checked = smartInsightsEnabled,
                        onCheckedChange = onSmartInsightsToggle,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = AurixTheme.AccentColor,
                            checkedThumbColor = AurixTheme.NavDark
                        )
                    )
                },
                colors = ListItemDefaults.colors(containerColor = AurixTheme.NavDark)
            )

            HorizontalDivider(color = AurixTheme.DividerColor)

            ListItem(
                headlineContent = { Text("Auto Financial Context", style = AurixTheme.Typography.titleMedium) },
                supportingContent = {
                    Text("Let Aurix see your transactions and budget when answering", style = AurixTheme.Typography.bodySmall)
                },
                trailingContent = {
                    Switch(
                        checked = autoContextEnabled,
                        onCheckedChange = onAutoContextToggle,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = AurixTheme.AccentColor,
                            checkedThumbColor = AurixTheme.NavDark
                        )
                    )
                },
                colors = ListItemDefaults.colors(containerColor = AurixTheme.NavDark)
            )

            Spacer(Modifier.height(20.dp))

            OutlinedButton(
                onClick = onClearConversation,
                modifier = Modifier.fillMaxWidth(),
                shape = AurixTheme.SmallContainerShape,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AurixTheme.DestructiveRed),
                //border = ButtonDefaults.outlinedToolboxBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(AurixTheme.DestructiveRed.copy(alpha = 0.5f)))
            ) {
                Text("Clear Current Conversation", style = AurixTheme.Typography.bodyMedium.copy(color = AurixTheme.DestructiveRed))
            }
        }
    }
}