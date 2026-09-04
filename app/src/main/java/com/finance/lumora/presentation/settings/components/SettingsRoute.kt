package com.finance.lumora.presentation.settings.components

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.core.security.biometric.BiometricAuthenticator
import com.finance.lumora.core.security.biometric.BiometricResult
import com.finance.lumora.presentation.settings.intent.SettingsEvent
import com.finance.lumora.presentation.settings.screen.SettingsScreen
import com.finance.lumora.presentation.settings.viewmodel.SettingsViewModel


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.finance.lumora.data.export.ExportFileManager
import com.finance.lumora.data.export.ExportShareManager
import com.finance.lumora.data.cache.CacheManager


@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSetBudgetClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onExportDataClick: () -> Unit,
    onClearCacheClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onAppVersionClick: ()->Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exportCsv by viewModel.exportCsv.collectAsStateWithLifecycle()
    val exportMessage by viewModel.exportMessage.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val cacheManager = CacheManager(context)

    // ---------------------------------------------------------
    // Find FragmentActivity safely
    // ---------------------------------------------------------

    val activity = remember(context) {
        context.findFragmentActivity()
    }

    // ---------------------------------------------------------
    // Biometric Authenticator
    // ---------------------------------------------------------

    val biometricAuthenticator = remember(context) {
        BiometricAuthenticator(context)
    }

    // ---------------------------------------------------------
    // Notification permission callbacks
    // ---------------------------------------------------------

    var pendingOnGranted by remember {
        mutableStateOf<(() -> Unit)?>(null)
    }

    var pendingOnDenied by remember {
        mutableStateOf<(() -> Unit)?>(null)
    }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                pendingOnGranted?.invoke()
            } else {
                pendingOnDenied?.invoke()
            }

            pendingOnGranted = null
            pendingOnDenied = null
        }

    // Data Export helper function:
    LaunchedEffect(exportCsv) {
        val csv = exportCsv ?: return@LaunchedEffect

        try {
            val fileManager = ExportFileManager(context)

            val fileUri = fileManager.createCsvFile(csv)

            ExportShareManager.shareCsv(
                context = context,
                fileUri = fileUri
            )
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Unable to export transactions.",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            viewModel.clearExportCsv()
        }
    }

    //Export message
    LaunchedEffect(exportMessage) {
        val message = exportMessage ?: return@LaunchedEffect

        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()

        viewModel.clearExportMessage()
    }
    //---------------------------------

    // ---------------------------------------------------------
    // Notification permission initialization
    // ---------------------------------------------------------

    /*
     * Do not repeatedly change settings every recomposition.
     *
     * This only handles Android 13+ notification permission.
     */

    // ---------------------------------------------------------
    // Notification permission helper
    // ---------------------------------------------------------

    fun checkAndRequestNotificationPermission(
        enabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {

        if (!enabled) {
            onPermissionDenied()
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val permissionGranted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (permissionGranted) {

                onPermissionGranted()

            } else {

                pendingOnGranted = onPermissionGranted
                pendingOnDenied = onPermissionDenied

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

        } else {

            onPermissionGranted()
        }
    }

    // ---------------------------------------------------------
    // UI STATE
    // ---------------------------------------------------------

    when {

        uiState.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = uiState.error
                        ?: "Something went wrong"
                )
            }
        }

        else -> {

            SettingsScreen(

                settings = uiState.settings,

                // -------------------------------------------------
                // Navigation
                // -------------------------------------------------

                onBackClick = onBackClick,

                onProfileClick = onProfileClick,

                onSetBudgetClick = onSetBudgetClick,

                onChangePasswordClick = onChangePasswordClick,

                onExportDataClick = {
                    viewModel.exportData()
                },

                onClearCacheClick = {
                    cacheManager.clearCache()

                    Toast.makeText(
                        context,
                        "Cache cleared successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                },

                onPrivacyPolicyClick = onPrivacyPolicyClick,

                onTermsClick = onTermsClick,

                onAppVersionClick = onAppVersionClick,

                // -------------------------------------------------
                // Currency
                // -------------------------------------------------

                onCurrencyChange = { currency ->

                    viewModel.onEvent(
                        SettingsEvent.ChangeCurrency(
                            currency
                        )
                    )
                },

                // -------------------------------------------------
                // Theme
                // -------------------------------------------------

                onThemeChange = { theme ->

                    viewModel.onEvent(
                        SettingsEvent.ChangeTheme(
                            theme
                        )
                    )
                },

                // -------------------------------------------------
                // BIOMETRIC
                // -------------------------------------------------

                onBiometricToggle = { enabled ->

                    // ---------------------------------------------
                    // User is disabling biometric
                    // ---------------------------------------------

                    if (!enabled) {

                        viewModel.onEvent(
                            SettingsEvent.ToggleBiometric(false)
                        )

                        return@SettingsScreen
                    }

                    // ---------------------------------------------
                    // User is enabling biometric
                    // ---------------------------------------------

                    val currentActivity = activity

                    if (currentActivity == null) {

                        Toast.makeText(
                            context,
                            "Unable to access the current activity.",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@SettingsScreen
                    }

                    // ---------------------------------------------
                    // Check biometric availability
                    // ---------------------------------------------

                    if (!biometricAuthenticator.canAuthenticate()) {

                        Toast.makeText(
                            context,
                            "Biometric authentication is not available or not enrolled on this device.",
                            Toast.LENGTH_LONG
                        ).show()

                        return@SettingsScreen
                    }

                    // ---------------------------------------------
                    // Start biometric authentication
                    // ---------------------------------------------

                    biometricAuthenticator.authenticate(
                        activity = currentActivity
                    ) { result ->

                        when (result) {

                            // -------------------------------------
                            // Authentication successful
                            // -------------------------------------

                            BiometricResult.Success -> {

                                viewModel.onEvent(
                                    SettingsEvent.ToggleBiometric(
                                        true
                                    )
                                )
                            }

                            // -------------------------------------
                            // Authentication failed
                            // -------------------------------------

                            BiometricResult.Failed -> {

                                Toast.makeText(
                                    context,
                                    "Biometric authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // -------------------------------------
                            // User cancelled
                            // -------------------------------------

                            BiometricResult.Cancelled -> {

                                Toast.makeText(
                                    context,
                                    "Biometric setup cancelled.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // -------------------------------------
                            // Biometric unavailable
                            // -------------------------------------

                            is BiometricResult.Unavailable -> {

                                Toast.makeText(
                                    context,
                                    "Biometric authentication is unavailable. Please enroll a fingerprint or face in your device settings.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            // -------------------------------------
                            // Other biometric error
                            // -------------------------------------

                            is BiometricResult.Error -> {

                                Toast.makeText(
                                    context,
                                    result.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {}
                        }
                    }
                },

                // -------------------------------------------------
                // Notifications
                // -------------------------------------------------

                onNotificationsToggle = { enabled ->

                    checkAndRequestNotificationPermission(
                        enabled = enabled,

                        onPermissionGranted = {

                            viewModel.onEvent(
                                SettingsEvent.ToggleNotifications(
                                    true
                                )
                            )
                        },

                        onPermissionDenied = {

                            viewModel.onEvent(
                                SettingsEvent.ToggleNotifications(
                                    false
                                )
                            )
                        }
                    )
                },

                // -------------------------------------------------
                // Budget Alerts
                // -------------------------------------------------

                onBudgetAlertsToggle = { enabled ->

                    checkAndRequestNotificationPermission(
                        enabled = enabled,

                        onPermissionGranted = {

                            viewModel.onEvent(
                                SettingsEvent.ToggleBudgetAlerts(
                                    true
                                )
                            )
                        },

                        onPermissionDenied = {

                            viewModel.onEvent(
                                SettingsEvent.ToggleBudgetAlerts(
                                    false
                                )
                            )
                        }
                    )
                },

                // -------------------------------------------------
                // Test Budget Alert
                // -------------------------------------------------

                onTestBudgetAlertClick = {

                    viewModel.onTestBudgetAlertClicked()
                }
            )
        }
    }
}


// =============================================================
// Context → FragmentActivity helper
// =============================================================

private tailrec fun Context.findFragmentActivity(): FragmentActivity? {

    return when (this) {

        is FragmentActivity -> this

        is ContextWrapper -> {

            val baseContext = baseContext

            if (baseContext === this) {
                null
            } else {
                baseContext.findFragmentActivity()
            }
        }

        else -> null
    }
}