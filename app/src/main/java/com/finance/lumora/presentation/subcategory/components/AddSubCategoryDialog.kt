package com.finance.lumora.presentation.subcategory.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

import com.finance.lumora.domain.model.SubCategory

@Composable
fun AddSubCategoryDialog(

    onDismiss: () -> Unit,

    onSave: (SubCategory) -> Unit

) {

    var name by remember {

        mutableStateOf("")

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text("Add SubCategory")

        },

        text = {

            OutlinedTextField(

                value = name,

                onValueChange = {

                    name = it

                },

                label = {

                    Text("SubCategory Name")

                }

            )

        },

        confirmButton = {

            Button(

                enabled = name.isNotBlank(),

                onClick = {

                    onSave(

                        SubCategory(

                            categoryId = 0L,

                            name = name.trim()

                        )

                    )

                }

            ) {

                Text("Save")

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