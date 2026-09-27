package com.finance.lumora.presentation.ai.components


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AurixLogo(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "AurixLogoTransition")

    // Rotation for outer financial ring
    val outerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OuterRotation"
    )

    // Reverse rotation for inner AI node ring
    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "InnerRotation"
    )

    // Pulse animation for the central neural diamond core
    val corePulse by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CorePulse"
    )

    // Dynamic theme-aware colors
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val radius = this.size.minDimension / 2f

            // 1. Ambient Background Radial Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.28f),
                        secondaryColor.copy(alpha = 0.10f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // 2. Outer Orbital Ring (Fintech Data Circuit)
            rotate(outerRotation, pivot = center) {
                val outerStrokeWidth = 2.dp.toPx()
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.15f),
                            primaryColor,
                            secondaryColor,
                            Color.Transparent
                        ),
                        center = center
                    ),
                    radius = radius * 0.82f,
                    center = center,
                    style = Stroke(
                        width = outerStrokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                    )
                )

                // Outer Node Accents (Symbolizing transaction data points)
                val nodeRadius = 3.5.dp.toPx()
                for (i in 0 until 3) {
                    val angle = Math.toRadians((i * 120).toDouble())
                    val x = center.x + (radius * 0.82f) * cos(angle).toFloat()
                    val y = center.y + (radius * 0.82f) * sin(angle).toFloat()
                    drawCircle(
                        color = primaryColor,
                        radius = nodeRadius,
                        center = Offset(x, y)
                    )
                }
            }

            // 3. Inner Counter-Rotating Ring (AI Processing Circuit)
            rotate(innerRotation, pivot = center) {
                val innerStrokeWidth = 1.8.dp.toPx()
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            tertiaryColor,
                            Color.Transparent,
                            secondaryColor.copy(alpha = 0.8f)
                        ),
                        center = center
                    ),
                    radius = radius * 0.58f,
                    center = center,
                    style = Stroke(
                        width = innerStrokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }

            // 4. Center Neural Diamond Gem (Aurix Core)
            val diamondSize = radius * 0.38f * corePulse
            val diamondPath = Path().apply {
                moveTo(center.x, center.y - diamondSize) // Top vertex
                lineTo(center.x + diamondSize * 0.85f, center.y) // Right vertex
                lineTo(center.x, center.y + diamondSize) // Bottom vertex
                lineTo(center.x - diamondSize * 0.85f, center.y) // Left vertex
                close()
            }

            // Core Glow
            drawPath(
                path = diamondPath,
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor,
                        tertiaryColor
                    )
                )
            )

            // Inner Facet Overlay for Sparkle / AI Intelligence effect
            val innerFacetPath = Path().apply {
                moveTo(center.x, center.y - diamondSize)
                lineTo(center.x, center.y + diamondSize)
                moveTo(center.x - diamondSize * 0.85f, center.y)
                lineTo(center.x + diamondSize * 0.85f, center.y)
            }

            drawPath(
                path = innerFacetPath,
                color = Color.White.copy(alpha = 0.45f),
                style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}