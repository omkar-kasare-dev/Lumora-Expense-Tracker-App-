package com.finance.lumora.presentation.category.components

import androidx.compose.ui.graphics.Color

/**
 * Represents a selectable category color.
 */
data class CategoryColorItem(
    val color: Color,
    val colorLong: Long
)

/**
 * Centralized list of colors used throughout the application.
 * The same list is used by:
 * - Add Category
 * - Edit Category
 * - Category Preview
 * - Category Item UI
 */
val CategoryColors = listOf(

    // Reds
    CategoryColorItem(Color(0xFFE53935), 0xFFE53935),
    CategoryColorItem(Color(0xFFD32F2F), 0xFFD32F2F),
    CategoryColorItem(Color(0xFFEF5350), 0xFFEF5350),

    // Pinks
    CategoryColorItem(Color(0xFFD81B60), 0xFFD81B60),
    CategoryColorItem(Color(0xFFEC407A), 0xFFEC407A),
    CategoryColorItem(Color(0xFFF06292), 0xFFF06292),

    // Purples
    CategoryColorItem(Color(0xFF8E24AA), 0xFF8E24AA),
    CategoryColorItem(Color(0xFFAB47BC), 0xFFAB47BC),
    CategoryColorItem(Color(0xFF5E35B1), 0xFF5E35B1),

    // Blues
    CategoryColorItem(Color(0xFF3949AB), 0xFF3949AB),
    CategoryColorItem(Color(0xFF1E88E5), 0xFF1E88E5),
    CategoryColorItem(Color(0xFF42A5F5), 0xFF42A5F5),
    CategoryColorItem(Color(0xFF039BE5), 0xFF039BE5),

    // Cyan / Teal
    CategoryColorItem(Color(0xFF00ACC1), 0xFF00ACC1),
    CategoryColorItem(Color(0xFF00897B), 0xFF00897B),
    CategoryColorItem(Color(0xFF26A69A), 0xFF26A69A),

    // Greens
    CategoryColorItem(Color(0xFF43A047), 0xFF43A047),
    CategoryColorItem(Color(0xFF66BB6A), 0xFF66BB6A),
    CategoryColorItem(Color(0xFF7CB342), 0xFF7CB342),
    CategoryColorItem(Color(0xFF9CCC65), 0xFF9CCC65),

    // Yellow / Lime
    CategoryColorItem(Color(0xFFC0CA33), 0xFFC0CA33),
    CategoryColorItem(Color(0xFFFDD835), 0xFFFDD835),
    CategoryColorItem(Color(0xFFFFEB3B), 0xFFFFEB3B),

    // Orange
    CategoryColorItem(Color(0xFFFFB300), 0xFFFFB300),
    CategoryColorItem(Color(0xFFFB8C00), 0xFFFB8C00),
    CategoryColorItem(Color(0xFFF57C00), 0xFFF57C00),

    // Brown
    CategoryColorItem(Color(0xFF8D6E63), 0xFF8D6E63),
    CategoryColorItem(Color(0xFF6D4C41), 0xFF6D4C41),

    // Blue Grey
    CategoryColorItem(Color(0xFF78909C), 0xFF78909C),
    CategoryColorItem(Color(0xFF546E7A), 0xFF546E7A),

    // Greys
    CategoryColorItem(Color(0xFF9E9E9E), 0xFF9E9E9E),
    CategoryColorItem(Color(0xFF757575), 0xFF757575),

    // Black
    CategoryColorItem(Color(0xFF424242), 0xFF424242)
)