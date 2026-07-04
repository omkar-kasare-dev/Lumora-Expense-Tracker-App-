package com.finance.lumora.presentation.category.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.finance.lumora.domain.model.Category

@Composable
fun DeleteCategoryDialog(

    category: Category,

    onDismiss: () -> Unit,

    onDelete: () -> Unit

) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text("Delete Category")

        },

        text = {

            Text(

                text = "Are you sure you want to delete \"${category.name}\"?\n\nThis action cannot be undone.",

                style = MaterialTheme.typography.bodyMedium

            )

        },

        confirmButton = {

            Button(

                onClick = onDelete

            ) {

                Text("Delete")

            }

        },

        dismissButton = {

            TextButton(

                onClick = onDismiss

            ) {

                Text("Cancel")

            }

        }

    )

}