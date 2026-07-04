package com.finance.lumora.presentation.category.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.vector.ImageVector

fun categoryIcon(name: String): ImageVector {

    return CategoryIcons
        .firstOrNull {
            it.name.equals(name, ignoreCase = true)
        }
        ?.icon
        ?: Icons.Default.Category
}