package com.finance.lumora.presentation.transaction.dialog


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Confirmation dialog shown before permanently deleting
 * a transaction.
 */
@Composable
fun DeleteTransactionDialog(

    onDismiss: () -> Unit,

    onConfirm: () -> Unit

) {

    AlertDialog(

        onDismissRequest = onDismiss,

        icon = {

            Icon(

                imageVector = Icons.Default.DeleteForever,

                contentDescription = "Delete Transaction"

            )

        },

        title = {

            Text(

                text = "Delete Transaction",

                style = MaterialTheme.typography.titleLarge

            )

        },

        text = {

            Text(

                text = "Are you sure you want to delete this transaction? This action cannot be undone.",

                style = MaterialTheme.typography.bodyMedium

            )

        },

        confirmButton = {

            TextButton(

                onClick = onConfirm

            ) {

                Text(

                    text = "Delete"

                )

            }

        },

        dismissButton = {

            TextButton(

                onClick = onDismiss

            ) {

                Text(

                    text = "Cancel"

                )

            }

        }

    )

}