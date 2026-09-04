package com.finance.lumora.presentation.settings.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.model.UserSettings
import com.finance.lumora.presentation.settings.components.SettingsClickableItem
import com.finance.lumora.presentation.settings.components.SettingsContainer
import com.finance.lumora.presentation.settings.components.SettingsDivider

import com.finance.lumora.presentation.settings.components.SettingsSectionHeader
import com.finance.lumora.presentation.settings.components.SettingsToggleItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: UserSettings,
    appVersion: String = "2.4.0",
    onBackClick: () -> Unit = {},
    onCurrencyChange: (String) -> Unit = {},
    onThemeChange: (AppTheme) -> Unit = {},
    onSetBudgetClick: () -> Unit = {}, // Added callback parameter for navigation
    onBiometricToggle: (Boolean) -> Unit = {},
    onNotificationsToggle: (Boolean) -> Unit = {},
    onBudgetAlertsToggle: (Boolean) -> Unit = {},
    onTestBudgetAlertClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onExportDataClick: () -> Unit = {},
    onClearCacheClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onAppVersionClick: () -> Unit = {}
) {
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    // cache dialog
    var showClearCacheDialog by remember {
        mutableStateOf(false)
    }
    //----

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
            // --------------------------------------------------
            // ACCOUNT
            // --------------------------------------------------
            SettingsSectionHeader(title = "Account")
            SettingsContainer {
                SettingsClickableItem(
                    icon = Icons.Outlined.Person,
                    title = "Profile",
                    subtitle = "Manage your account",
                    onClick = onProfileClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --------------------------------------------------
            // GENERAL PREFERENCES
            // --------------------------------------------------
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
                SettingsDivider()
                // ADDED: Set Monthly Budget Item
                SettingsClickableItem(
                    icon = Icons.Outlined.AccountBalanceWallet,
                    title = "Monthly Budget",
                    subtitle = "Set your monthly spending target",
                    onClick = onSetBudgetClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --------------------------------------------------
            // NOTIFICATIONS & ALERTS
            // --------------------------------------------------
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

                if (settings.isBudgetAlertsEnabled) {
                    SettingsDivider()
                    SettingsClickableItem(
                        icon = Icons.Outlined.BugReport,
                        title = "Test Budget Alert Instantly",
                        subtitle = "Triggers WorkManager to check budget immediately",
                        onClick = onTestBudgetAlertClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --------------------------------------------------
            // SECURITY & PRIVACY
            // --------------------------------------------------
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

            // --------------------------------------------------
            // DATA & STORAGE
            // --------------------------------------------------
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
                    onClick =  {
                        showClearCacheDialog = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --------------------------------------------------
            // ABOUT & LEGAL
            // --------------------------------------------------
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
                SettingsClickableItem(
                    icon = Icons.Outlined.Info,
                    title = "App Version",
                    value = "v$appVersion",
                    onClick = onAppVersionClick
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
    // Clear Cache Dialog
    if (showClearCacheDialog) {
        AlertDialog(
            onDismissRequest = {
                showClearCacheDialog = false
            },
            title = {
                Text("Clear Cache?")
            },
            text = {
                Text(
                    "This will remove temporary files from Lumora. " +
                            "Your transactions and settings will not be deleted."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showClearCacheDialog = false
                        onClearCacheClick()
                    }
                ) {
                    Text("Clear")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearCacheDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    //---
}
