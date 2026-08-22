package com.finance.lumora.presentation.profile.screen

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