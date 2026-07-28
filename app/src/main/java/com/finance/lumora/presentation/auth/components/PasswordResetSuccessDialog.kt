package com.finance.lumora.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PasswordResetSuccessDialog(

    email: String,

    onDismiss: () -> Unit

) {

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {

            TextButton(

                onClick = onDismiss

            ) {

                Text("Back to Login")

            }

        },

        icon = {

            Icon(

                imageVector = Icons.Default.MarkEmailRead,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary

            )

        },

        title = {

            Text(

                text = "Password Reset Email Sent",

                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Center,

                modifier = Modifier.fillMaxWidth()

            )

        },

        text = {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                Text(

                    text =
                        "We've sent a password reset link to:",

                    textAlign = TextAlign.Center

                )

                Text(

                    text = email,

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.SemiBold,

                    color = MaterialTheme.colorScheme.primary,

                    textAlign = TextAlign.Center

                )

                Text(

                    text =
                        "Please check your inbox. If you don't see the email, check your spam folder.",

                    textAlign = TextAlign.Center,

                    style = MaterialTheme.typography.bodyMedium,

                    modifier = Modifier.padding(top = 8.dp)

                )

            }

        }

    )

}