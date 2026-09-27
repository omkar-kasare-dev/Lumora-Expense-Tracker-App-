package com.finance.lumora.presentation.ai.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AurixSuggestionCard(
    text: String,
    onClick: () -> Unit
) {

    Surface(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .clickable(
                onClick = onClick
            ),

        shape = RoundedCornerShape(16.dp),

        color = Color.White.copy(
            alpha = 0.08f
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        Color(0xFF90CAF9)
                    )
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = text,

                modifier = Modifier.weight(1f),

                style = MaterialTheme.typography.bodyMedium,

                color = Color.White.copy(
                    alpha = 0.90f
                ),

                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,

                contentDescription = "Ask",

                tint = Color.White.copy(
                    alpha = 0.55f
                ),

                modifier = Modifier.size(20.dp)
            )
        }
    }
}