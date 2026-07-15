package com.finance.lumora.presentation.home.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays greeting message on Home Screen.
 *
 * Example:
 *
 * Good Morning ☀
 * Omkar
 * Welcome back!
 */
@Composable
fun GreetingSection(

    userName: String,

    greeting: String,

    modifier: Modifier = Modifier

) {

    Column(

        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)

    ) {

        Text(

            text = greeting,

            style = MaterialTheme.typography.titleMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

        Text(

            text = userName,

            style = MaterialTheme.typography.headlineSmall,

            color = MaterialTheme.colorScheme.onSurface

        )

        Text(

            text = "Welcome back!",

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

    }

}