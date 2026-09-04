package com.finance.lumora.presentation.ai.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.presentation.ai.viewmodel.GeminiTestViewModel

@Composable
fun GeminiTestScreen(
    viewModel: GeminiTestViewModel = hiltViewModel()
) {

    val response by viewModel.response.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "AURIX Gemini Test",
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = viewModel::testGemini,
            enabled = !isLoading
        ) {
            Text("Test Gemini Connection")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (response.isNotBlank()) {

            Text(
                text = "Gemini Response:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = response)
        }

        error?.let {

            Text(
                text = "Error: $it",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}