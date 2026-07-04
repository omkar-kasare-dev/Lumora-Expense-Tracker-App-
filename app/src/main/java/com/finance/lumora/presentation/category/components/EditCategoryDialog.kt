package com.finance.lumora.presentation.category.components



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
fun EditCategoryDialog(

    category: Category,

    onDismiss: () -> Unit,

    onUpdate: (Category) -> Unit

) {

    var name by remember(category.id) {
        mutableStateOf(category.name)
    }

    var selectedIcon by remember(category.id) {
        mutableStateOf(category.icon)
    }

    var selectedColor by remember(category.id) {
        mutableLongStateOf(category.color)
    }

    val isNameValid = name.trim().isNotEmpty()

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text("Edit Category")

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

                    onUpdate(

                        category.copy(

                            name = name.trim(),

                            icon = selectedIcon,

                            color = selectedColor

                        )

                    )

                }

            ) {

                Text("Update")

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