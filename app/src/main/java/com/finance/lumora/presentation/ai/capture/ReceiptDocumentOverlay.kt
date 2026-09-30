package com.finance.lumora.presentation.ai.capture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
// Custom Document Frame Overlay Visualizer

@Composable
fun ReceiptDocumentOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            val cornerLength = 32.dp.toPx()
            val cornerRadius = 16.dp.toPx()

            // Draw bounding corner guides
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)

            drawRoundRect(
                color = Color.White.copy(alpha = 0.5f),
                size = size,
                cornerRadius = CornerRadius(cornerRadius),
                style = Stroke(width = 1.dp.toPx(), pathEffect = pathEffect)
            )

            // Top-Left Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(0f, cornerLength),
                end = Offset(0f, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(0f, 0f),
                end = Offset(cornerLength, 0f),
                strokeWidth = strokeWidth
            )

            // Top-Right Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(size.width - cornerLength, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, 0f),
                end = Offset(size.width, cornerLength),
                strokeWidth = strokeWidth
            )

            // Bottom-Left Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(0f, size.height - cornerLength),
                end = Offset(0f, size.height),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(0f, size.height),
                end = Offset(cornerLength, size.height),
                strokeWidth = strokeWidth
            )

            // Bottom-Right Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(size.width - cornerLength, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, size.height - cornerLength),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }
    }
}