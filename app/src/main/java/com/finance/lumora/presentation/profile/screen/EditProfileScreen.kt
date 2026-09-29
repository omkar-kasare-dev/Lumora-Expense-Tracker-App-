package com.finance.lumora.presentation.profile.screen
/*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.profile.components.CurrencySelectionDialog
import com.finance.lumora.presentation.profile.components.LanguageSelectionDialog
import com.finance.lumora.presentation.profile.state.EditProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    state: EditProfileState,
    onBackClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onCurrencyChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {

    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ----------------------------------------
                // FULL NAME
                // ----------------------------------------
                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = onFullNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    enabled = !state.isSaving
                )

                // ----------------------------------------
                // EMAIL
                // ----------------------------------------
                OutlinedTextField(
                    value = state.email,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    enabled = false
                )

                Text(
                    text = "Email changes are managed separately for account security.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // ----------------------------------------
                // CURRENCY
                // ----------------------------------------
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.currency,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Primary Currency") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AttachMoney,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        enabled = !state.isSaving,
                        singleLine = true,
                        trailingIcon = {
                            TextButton(
                                onClick = { showCurrencyDialog = true },
                                enabled = !state.isSaving
                            ) {
                                Text("Change")
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(enabled = !state.isSaving) {
                                showCurrencyDialog = true
                            }
                    )
                }

                // ----------------------------------------
                // LANGUAGE
                // ----------------------------------------
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.language,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Language") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Language,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        enabled = !state.isSaving,
                        singleLine = true,
                        trailingIcon = {
                            TextButton(
                                onClick = { showLanguageDialog = true },
                                enabled = !state.isSaving
                            ) {
                                Text("Change")
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(enabled = !state.isSaving) {
                                showLanguageDialog = true
                            }
                    )
                }

                // ----------------------------------------
                // ERROR MESSAGE
                // ----------------------------------------
                state.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // ----------------------------------------
                // SAVE BUTTON
                // ----------------------------------------
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Save Changes",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))
            }

            // ----------------------------------------
            // CURRENCY DIALOG
            // ----------------------------------------
            if (showCurrencyDialog) {
                CurrencySelectionDialog(
                    selectedCurrency = state.currency,
                    onCurrencySelected = { currency ->
                        onCurrencyChange(currency)
                        showCurrencyDialog = false
                    },
                    onDismiss = { showCurrencyDialog = false }
                )
            }

            // ----------------------------------------
            // LANGUAGE DIALOG
            // ----------------------------------------
            if (showLanguageDialog) {
                LanguageSelectionDialog(
                    selectedLanguage = state.language,
                    onLanguageSelected = { language ->
                        onLanguageChange(language)
                        showLanguageDialog = false
                    },
                    onDismiss = { showLanguageDialog = false }
                )
            }
        }
    }
}

 */



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.profile.components.CurrencySelectionDialog
import com.finance.lumora.presentation.profile.components.LanguageSelectionDialog
import com.finance.lumora.presentation.profile.state.EditProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    state: EditProfileState,
    onBackClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onCurrencyChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // ----------------------------------------
                // ACCOUNT INFO
                // ----------------------------------------
                Text(
                    text = "Account Details",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // FULL NAME
                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = onFullNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    singleLine = true,
                    enabled = !state.isSaving,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                // EMAIL (READ ONLY)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email Address") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        singleLine = true,
                        enabled = false,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                    )

                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Email changes are managed separately for security.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // ----------------------------------------
                // PREFERENCES (COMPACT GROUP)
                // ----------------------------------------
                Text(
                    text = "Preferences",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    tonalElevation = 1.dp
                ) {
                    Column {
                        CompactSelectorRow(
                            label = "Primary Currency",
                            value = state.currency,
                            icon = Icons.Outlined.AttachMoney,
                            enabled = !state.isSaving,
                            onClick = { showCurrencyDialog = true }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        CompactSelectorRow(
                            label = "Language",
                            value = formatLanguageDisplayName(state.language),
                            icon = Icons.Outlined.Language,
                            enabled = !state.isSaving,
                            onClick = { showLanguageDialog = true }
                        )
                    }
                }

                // ----------------------------------------
                // ERROR MESSAGE
                // ----------------------------------------
                state.errorMessage?.let { error ->
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // ----------------------------------------
                // SAVE BUTTON
                // ----------------------------------------
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    enabled = !state.isSaving,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Save Changes",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // ----------------------------------------
            // DIALOGS
            // ----------------------------------------
            if (showCurrencyDialog) {
                CurrencySelectionDialog(
                    selectedCurrency = state.currency,
                    onCurrencySelected = { currency ->
                        onCurrencyChange(currency)
                        showCurrencyDialog = false
                    },
                    onDismiss = { showCurrencyDialog = false }
                )
            }

            if (showLanguageDialog) {
                LanguageSelectionDialog(
                    selectedLanguage = state.language,
                    onLanguageSelected = { language ->
                        onLanguageChange(language)
                        showLanguageDialog = false
                    },
                    onDismiss = { showLanguageDialog = false }
                )
            }
        }
    }
}

@Composable
private fun CompactSelectorRow(
    label: String,
    value: String,
    icon: ImageVector,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value.ifEmpty { "Select" },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun formatLanguageDisplayName(code: String): String {
    return when (code.lowercase()) {
        "en" -> "English"
        "hi" -> "Hindi"
        "mr" -> "Marathi"
        "gr", "de" -> "German"
        else -> code
    }
}