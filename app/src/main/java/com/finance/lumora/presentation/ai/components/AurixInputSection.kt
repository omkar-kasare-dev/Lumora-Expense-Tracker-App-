package com.finance.lumora.presentation.ai.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

@Composable
fun AurixInputSection(
    question: String,
    onQuestionChanged: (String) -> Unit,
    onSend: () -> Unit,
    isLoading: Boolean
) {

    Surface(
        tonalElevation = 3.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment =
                Alignment.Bottom
        ) {

            OutlinedTextField(
                value = question,
                onValueChange = onQuestionChanged,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Ask AURIX anything...")
                },
                maxLines = 4,
                enabled = !isLoading,
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(
                modifier = Modifier.size(8.dp)
            )

            IconButton(
                onClick = onSend,
                enabled =
                    question.isNotBlank() &&
                            !isLoading
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}