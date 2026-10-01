package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val AurixNavDark = Color(0xFF0F2640)
private val AurixSurfaceDark = Color(0xFF133253)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixAboutScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = AurixNavDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About AI",
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Aurix",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = AurixSurfaceDark,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Aurix is Lumora's AI finance assistant, powered by Google Gemini via Firebase AI Logic. " +
                                "It can draft transactions from a receipt photo or your voice, and answer questions about " +
                                "your spending using the transaction history stored on your device.",
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "When Auto Financial Context is on, Aurix sends a summary of your relevant transactions and " +
                                "budget to Gemini to answer your question. Receipt text and voice transcripts are sent " +
                                "to Gemini only when you use those features. See the Privacy Policy for full details.",
                        color = Color.White.copy(alpha = 0.55f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}