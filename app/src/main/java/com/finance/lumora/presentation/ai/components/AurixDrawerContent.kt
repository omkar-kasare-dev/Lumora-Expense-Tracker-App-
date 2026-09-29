package com.finance.lumora.presentation.ai.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AurixNavDark = Color(0xFF0F2640)
private val AurixSurfaceDark = Color(0xFF133253)
private val AurixAccentColor = Color(0xFF4FC3F7)
private val TextPrimary = Color.White
private val TextSecondary = Color.White.copy(alpha = 0.65f)
private val TextMuted = Color.White.copy(alpha = 0.45f)
private val DividerColor = Color.White.copy(alpha = 0.08f)

@Composable
fun AurixDrawerContent(
    onNewChatClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onExportChatClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var smartSuggestionsEnabled by remember { mutableStateOf(true) }
    var autoContextEnabled by remember { mutableStateOf(true) }

    ModalDrawerSheet(
        modifier = modifier
            .width(310.dp)
            .fillMaxHeight(),
        drawerContainerColor = AurixNavDark,
        drawerContentColor = TextPrimary,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // ---------------------------------------------------------
            // Header Section: Copilot Profile Info
            // ---------------------------------------------------------
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AurixSurfaceDark),
                    contentAlignment = Alignment.Center
                ) {
                    AurixLogo(size = 38.dp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Aurix Assistant",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                letterSpacing = 0.3.sp
                            ),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "v1.0",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = AurixAccentColor,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(AurixAccentColor.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Gemini Pro Financial Model",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                }
            }

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // ---------------------------------------------------------
            // Scrollable Menu Options
            // ---------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DrawerSectionHeader(title = "CHATS & DATA")

                DrawerMenuItem(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    label = "New Chat Session",
                    onClick = onNewChatClick
                )

                DrawerMenuItem(
                    icon = Icons.Outlined.History,
                    label = "Conversation History",
                    badgeText = "12 saved",
                    onClick = onHistoryClick
                )

                DrawerMenuItem(
                    icon = Icons.Outlined.Download,
                    label = "Export Financial Summary",
                    onClick = onExportChatClick
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = DividerColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                DrawerSectionHeader(title = "COPILOT PREFERENCES")

                DrawerToggleMenuItem(
                    icon = Icons.Outlined.Psychology,
                    label = "Smart Insights",
                    subtitle = "Proactive spending suggestions",
                    isChecked = smartSuggestionsEnabled,
                    onCheckedChange = { smartSuggestionsEnabled = it }
                )

                DrawerToggleMenuItem(
                    icon = Icons.Outlined.Tune,
                    label = "Auto Financial Context",
                    subtitle = "Include monthly budget in queries",
                    isChecked = autoContextEnabled,
                    onCheckedChange = { autoContextEnabled = it }
                )

                DrawerMenuItem(
                    icon = Icons.Outlined.Settings,
                    label = "Model Settings",
                    onClick = onSettingsClick
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = DividerColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                DrawerSectionHeader(title = "SUPPORT & INFO")

                DrawerMenuItem(
                    icon = Icons.Outlined.HelpOutline,
                    label = "Help & Prompt Examples",
                    onClick = onHelpClick
                )

                DrawerMenuItem(
                    icon = Icons.Outlined.Info,
                    label = "About Lumora AI",
                    onClick = onAboutClick
                )
            }

            // ---------------------------------------------------------
            // Minimal Footer
            // ---------------------------------------------------------
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Lumora AI Copilot • Offline First",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = TextMuted
            )
        }
    }
}

@Composable
private fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp
        ),
        color = TextMuted,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    badgeText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )

        if (badgeText != null) {
            Text(
                text = badgeText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = TextSecondary,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
private fun DrawerToggleMenuItem(
    icon: ImageVector,
    label: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCheckedChange(!isChecked) }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = TextMuted
            )
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AurixAccentColor,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = Color.White.copy(alpha = 0.1f),
                uncheckedBorderColor = Color.Transparent
            ),
           // modifier = Modifier.size(scale = 0.75f, height = 24.dp)
        )
    }
}