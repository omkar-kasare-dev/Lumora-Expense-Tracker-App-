package com.finance.lumora.presentation.dashboard.components




import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardLoading(
    modifier: Modifier = Modifier
) {
    //--------------------------------------------------
    // Rotation & Pulsing Animation States
    //--------------------------------------------------
    val infiniteTransition = rememberInfiniteTransition(label = "loading_transition")

    val mainRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "main_rotation"
    )

    val counterRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "counter_rotation"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //--------------------------------------------------
            // Custom Dual-Ring Canvas Indicator
            //--------------------------------------------------
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(primaryContainer.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(42.dp)
                ) {
                    val strokeWidth = 3.5.dp.toPx()
                    val outerStrokeWidth = 2.dp.toPx()

                    // Background Track Ring
                    drawCircle(
                        color = trackColor,
                        style = Stroke(width = strokeWidth)
                    )

                    // Main Sweep Arc
                    drawArc(
                        color = primaryColor,
                        startAngle = mainRotation,
                        sweepAngle = 110f,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    )

                    // Secondary Counter-Rotating Accent Arc
                    drawArc(
                        color = primaryColor.copy(alpha = 0.4f),
                        startAngle = counterRotation,
                        sweepAngle = 60f,
                        useCenter = false,
                        style = Stroke(
                            width = outerStrokeWidth,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            //--------------------------------------------------
            // Typography Label
            //--------------------------------------------------
            Text(
                text = "Loading Dashboard...",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    letterSpacing = 0.2.sp
                ),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
    }
}