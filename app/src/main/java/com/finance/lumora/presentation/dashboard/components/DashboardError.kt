package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DashboardError(

    message: String,

    onRetry: () -> Unit,

    modifier: Modifier = Modifier

) {

    Column(

        modifier = modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = onRetry
        ) {

            Text("Retry")

        }

    }

}