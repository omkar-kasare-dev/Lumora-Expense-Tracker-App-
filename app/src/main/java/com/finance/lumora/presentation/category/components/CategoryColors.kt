package com.finance.lumora.presentation.category.components

import androidx.compose.ui.graphics.Color

/**
 * Represents a selectable category color.
 *
 * @property color Color object used for UI rendering.
 * @property colorLong ARGB color value stored in Room Database.
 */
data class CategoryColorItem(
    val color: Color,
    val colorLong: Long
)

/**
 * Centralized list of colors used throughout the application.
 *
 * The same list is used by:
 * - Add Category
 * - Edit Category
 * - Category Preview
 * - Category Item UI
 */
val CategoryColors = listOf(

    // Red
    CategoryColorItem(
        color = Color(0xFFE53935),
        colorLong = 0xFFE53935
    ),

    // Pink
    CategoryColorItem(
        color = Color(0xFFD81B60),
        colorLong = 0xFFD81B60
    ),

    // Purple
    CategoryColorItem(
        color = Color(0xFF8E24AA),
        colorLong = 0xFF8E24AA
    ),

    // Deep Purple
    CategoryColorItem(
        color = Color(0xFF5E35B1),
        colorLong = 0xFF5E35B1
    ),

    // Indigo
    CategoryColorItem(
        color = Color(0xFF3949AB),
        colorLong = 0xFF3949AB
    ),

    // Blue
    CategoryColorItem(
        color = Color(0xFF1E88E5),
        colorLong = 0xFF1E88E5
    ),

    // Light Blue
    CategoryColorItem(
        color = Color(0xFF039BE5),
        colorLong = 0xFF039BE5
    ),

    // Teal
    CategoryColorItem(
        color = Color(0xFF00897B),
        colorLong = 0xFF00897B
    ),

    // Green
    CategoryColorItem(
        color = Color(0xFF43A047),
        colorLong = 0xFF43A047
    ),

    // Light Green
    CategoryColorItem(
        color = Color(0xFF7CB342),
        colorLong = 0xFF7CB342
    ),

    // Yellow
    CategoryColorItem(
        color = Color(0xFFFDD835),
        colorLong = 0xFFFDD835
    ),

    // Amber
    CategoryColorItem(
        color = Color(0xFFFFB300),
        colorLong = 0xFFFFB300
    ),

    // Orange
    CategoryColorItem(
        color = Color(0xFFFB8C00),
        colorLong = 0xFFFB8C00
    ),

    // Brown
    CategoryColorItem(
        color = Color(0xFF6D4C41),
        colorLong = 0xFF6D4C41
    ),

    // Blue Grey
    CategoryColorItem(
        color = Color(0xFF546E7A),
        colorLong = 0xFF546E7A
    ),

    // Grey
    CategoryColorItem(
        color = Color(0xFF757575),
        colorLong = 0xFF757575
    )
)