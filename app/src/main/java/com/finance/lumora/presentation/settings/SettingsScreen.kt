package com.finance.lumora.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.model.UserSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: UserSettings = UserSettings(),
    appVersion: String = "1.2.0",
    onBackClick: () -> Unit = {},
    onCurrencyChange: (String) -> Unit = {},
    onThemeChange: (AppTheme) -> Unit = {},
    onBiometricToggle: (Boolean) -> Unit = {},
    onNotificationsToggle: (Boolean) -> Unit = {},
    onBudgetAlertsToggle: (Boolean) -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onExportDataClick: () -> Unit = {},
    onClearCacheClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onTermsClick: () -> Unit = {}
) {
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // --- GENERAL PREFERENCES ---
            SettingsSectionHeader(title = "Preferences")
            SettingsContainer {
                SettingsClickableItem(
                    icon = Icons.Outlined.AttachMoney,
                    title = "Primary Currency",
                    subtitle = settings.currency,
                    onClick = { showCurrencyDialog = true }
                )
                SettingsDivider()
                SettingsClickableItem(
                    icon = Icons.Outlined.Palette,
                    title = "App Theme",
                    subtitle = when (settings.theme) {
                        AppTheme.SYSTEM -> "System Default"
                        AppTheme.LIGHT -> "Light Mode"
                        AppTheme.DARK -> "Dark Mode"
                    },
                    onClick = { showThemeDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- NOTIFICATIONS & ALERTS ---
            SettingsSectionHeader(title = "Notifications & Alerts")
            SettingsContainer {
                SettingsToggleItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Push Notifications",
                    subtitle = "Receive app updates & reminders",
                    checked = settings.isNotificationsEnabled,
                    onCheckedChange = onNotificationsToggle
                )
                SettingsDivider()
                SettingsToggleItem(
                    icon = Icons.Outlined.PriceCheck,
                    title = "Budget Alerts",
                    subtitle = "Notify when reaching 80% limit",
                    checked = settings.isBudgetAlertsEnabled,
                    onCheckedChange = onBudgetAlertsToggle
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- SECURITY & PRIVACY ---
            SettingsSectionHeader(title = "Security & Privacy")
            SettingsContainer {
                SettingsToggleItem(
                    icon = Icons.Outlined.Fingerprint,
                    title = "Biometric Lock",
                    subtitle = "Require Fingerprint / Face ID to open app",
                    checked = settings.isBiometricEnabled,
                    onCheckedChange = onBiometricToggle
                )
                SettingsDivider()
                SettingsClickableItem(
                    icon = Icons.Outlined.Lock,
                    title = "Change Password",
                    onClick = onChangePasswordClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- DATA & STORAGE ---
            SettingsSectionHeader(title = "Data & Storage")
            SettingsContainer {
                SettingsClickableItem(
                    icon = Icons.Outlined.FileDownload,
                    title = "Export Data",
                    subtitle = "Download transactions as CSV / PDF",
                    onClick = onExportDataClick
                )
                SettingsDivider()
                SettingsClickableItem(
                    icon = Icons.Outlined.CleaningServices,
                    title = "Clear Cache",
                    subtitle = "Frees up local storage space",
                    onClick = onClearCacheClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- ABOUT & LEGAL ---
            SettingsSectionHeader(title = "About Lumora")
            SettingsContainer {
                SettingsClickableItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    onClick = onPrivacyPolicyClick
                )
                SettingsDivider()
                SettingsClickableItem(
                    icon = Icons.Outlined.Description,
                    title = "Terms of Service",
                    onClick = onTermsClick
                )
                SettingsDivider()
                SettingsInfoItem(
                    icon = Icons.Outlined.Info,
                    title = "App Version",
                    value = "v$appVersion"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // --- CURRENCY DIALOG ---
    if (showCurrencyDialog) {
        val currencies = listOf("INR (₹)", "USD ($)", "EUR (€)", "GBP (£)", "AED (د.إ)")
        AlertDialog(
            onDismissRequest = { showCurrencyDialog = false },
            title = { Text("Select Primary Currency") },
            text = {
                Column {
                    currencies.forEach { currency ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCurrencyChange(currency)
                                    showCurrencyDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (currency == settings.currency),
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = currency, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCurrencyDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- THEME DIALOG ---
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Choose Theme") },
            text = {
                Column {
                    AppTheme.entries.forEach { themeOption ->
                        val label = when (themeOption) {
                            AppTheme.SYSTEM -> "System Default"
                            AppTheme.LIGHT -> "Light"
                            AppTheme.DARK -> "Dark"
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onThemeChange(themeOption)
                                    showThemeDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (themeOption == settings.theme),
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = label, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// --- SUB-COMPONENTS ---

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsContainer(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsInfoItem(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}