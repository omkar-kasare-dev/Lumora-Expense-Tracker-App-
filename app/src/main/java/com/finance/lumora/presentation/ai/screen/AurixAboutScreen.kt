package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.ai.theme.AurixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixAboutScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = AurixTheme.NavDark,
        topBar = {
            TopAppBar(
                title = { Text("About Aurix AI", style = AurixTheme.Typography.titleLarge) },
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
                .padding(AurixTheme.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Aurix AI",
                style = AurixTheme.Typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
            Surface(
                shape = AurixTheme.ContainerShape,
                color = AurixTheme.SurfaceDark,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(AurixTheme.CardPadding)) {
                    Text(
                        "Aurix is Lumora's AI finance assistant, powered by Google Gemini via Firebase AI Logic. " +
                                "It can draft transactions from a receipt photo or your voice, and answer questions about " +
                                "your spending using the transaction history stored on your device.",
                        style = AurixTheme.Typography.bodyMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = AurixTheme.DividerColor)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "When Auto Financial Context is on, Aurix sends a summary of your relevant transactions and " +
                                "budget to Gemini to answer your question. Receipt text and voice transcripts are sent " +
                                "to Gemini only when you use those features. See the Privacy Policy for full details.",
                        style = AurixTheme.Typography.bodySmall
                    )
                }
            }
        }
    }
}