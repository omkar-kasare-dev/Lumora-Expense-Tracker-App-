package com.finance.lumora.presentation.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun AuthFooter(

    message: String,

    actionText: String,

    onActionClick: () -> Unit,

    modifier: Modifier = Modifier

) {

    Column(

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {

        Text(

            text = buildAnnotatedString {

                append(message)
                append(" ")

                withStyle(

                    SpanStyle(

                        color = MaterialTheme.colorScheme.primary,

                        fontWeight = FontWeight.SemiBold

                    )

                ) {

                    append(actionText)

                }

            },

            modifier = Modifier.clickable {

                onActionClick()

            },

            textAlign = TextAlign.Center,

            style = MaterialTheme.typography.bodyMedium

        )

        Text(

            text = "By continuing, you agree to our Terms of Service and Privacy Policy.",

            style = MaterialTheme.typography.bodySmall,

            color = MaterialTheme.colorScheme.onSurfaceVariant,

            textAlign = TextAlign.Center

        )

    }

}