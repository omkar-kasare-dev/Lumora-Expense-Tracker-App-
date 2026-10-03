package com.finance.lumora.presentation.ai.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AurixTheme {
    // Premium Color Palette
    val NavDark = Color(0xFF0F2640)
    val SurfaceDark = Color(0xFF133253)
    val AccentColor = Color(0xFF4FC3F7)
    val TextPrimary = Color.White
    val TextSecondary = Color.White.copy(alpha = 0.60f)
    val TextMuted = Color.White.copy(alpha = 0.40f)
    val DividerColor = Color.White.copy(alpha = 0.08f)
    val DestructiveRed = Color(0xFFEF5350)

    // Minimal Container Shapes & Sizing
    val ContainerShape = RoundedCornerShape(12.dp)
    val SmallContainerShape = RoundedCornerShape(8.dp)
    val CardPadding = 12.dp
    val ScreenPadding = 16.dp
    val IconSizeSmall = 18.dp
    val IconSizeMedium = 20.dp
    val IconSizeLarge = 40.dp

    // Premium Minimal Typography
    val Typography = Typography(
        titleLarge = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.15.sp,
            color = TextPrimary
        ),
        titleMedium = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
            color = TextPrimary
        ),
        bodyMedium = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp,
            letterSpacing = 0.25.sp,
            color = TextPrimary
        ),
        bodySmall = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 15.sp,
            letterSpacing = 0.4.sp,
            color = TextSecondary
        ),
        labelSmall = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp,
            color = AccentColor
        )
    )
}