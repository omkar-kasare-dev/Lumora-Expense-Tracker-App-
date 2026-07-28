package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun AuthBackground(

    modifier: Modifier = Modifier,

    contentPadding: PaddingValues = PaddingValues(24.dp),

    content: @Composable () -> Unit

) {

    val backgroundBrush = Brush.verticalGradient(

        colors = listOf(

            MaterialTheme.colorScheme.surface,

            MaterialTheme.colorScheme.surface,

            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.20f)

        )

    )

    Box(

        modifier = modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .systemBarsPadding()
            .padding(contentPadding)

    ) {

        content()

    }

}