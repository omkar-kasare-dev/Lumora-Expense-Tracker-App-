package com.finance.lumora.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

/**
 * Reusable form for creating and editing categories.
 *
 * Used by:
 * - AddCategoryDialog
 * - EditCategoryDialog
 */
@Composable
fun CategoryForm(

    name: String,
    onNameChange: (String) -> Unit,

    selectedIcon: String,
    onIconSelected: (String) -> Unit,

    selectedColor: Long,
    onColorSelected: (Long) -> Unit

) {

    val isNameValid = name.trim().isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        // Preview

        Row(

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Box(

                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(selectedColor)),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = categoryIcon(selectedIcon),

                    contentDescription = null,

                    tint = Color.White

                )

            }

            Column {

                Text(

                    text = if (name.isBlank())
                        "Category Preview"
                    else
                        name,

                    style = MaterialTheme.typography.titleMedium

                )

                Text(

                    text = selectedIcon,

                    style = MaterialTheme.typography.bodySmall,

                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )

            }

        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Category Name
        OutlinedTextField(

            value = name,

            onValueChange = onNameChange,

            modifier = Modifier.fillMaxWidth(),

            singleLine = true,

            label = {

                Text("Category Name")

            },

            isError = !isNameValid && name.isNotEmpty()

        )

        if (!isNameValid && name.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = "Category name is required.",

                color = MaterialTheme.colorScheme.error,

                style = MaterialTheme.typography.bodySmall

            )

        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Icon Picker

        Text(

            text = "Choose Icon",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        IconPicker(

            selectedIcon = selectedIcon,

            onIconSelected = onIconSelected

        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Color Picker
        Text(

            text = "Choose Color",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ColorPicker(

            selectedColor = selectedColor,

            onColorSelected = onColorSelected

        )

    }

}