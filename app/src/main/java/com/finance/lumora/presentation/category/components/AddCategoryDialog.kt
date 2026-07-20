package com.finance.lumora.presentation.category.components

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.finance.lumora.domain.model.Category

@Composable
fun AddCategoryDialog(

    onDismiss: () -> Unit,

    onSave: (Category) -> Unit

) {

    var name by remember {
        mutableStateOf("")
    }

    var selectedIcon by remember {
        mutableStateOf(
            CategoryIcons.first().name
        )
    }

    var selectedColor by remember {
        mutableLongStateOf(
            CategoryColors.first().colorLong
        )
    }

    val isNameValid = name.trim().isNotEmpty()

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text("Add Category")

        },

        text = {

            CategoryForm(

                name = name,

                onNameChange = {

                    name = it

                },

                selectedIcon = selectedIcon,

                onIconSelected = {

                    selectedIcon = it

                },

                selectedColor = selectedColor,

                onColorSelected = {

                    selectedColor = it

                }

            )

        },

        confirmButton = {

            Button(

                enabled = isNameValid,

                onClick = {
                    Log.d(
                        "CATEGORY_SAVE",
                        "Dialog Save Clicked: $name"
                    )

                    onSave(

                        Category(

                            name = name.trim(),

                            icon = selectedIcon,

                            color = selectedColor

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