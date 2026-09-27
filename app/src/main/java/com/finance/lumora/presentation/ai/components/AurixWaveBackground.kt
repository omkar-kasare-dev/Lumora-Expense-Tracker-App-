package com.finance.lumora.presentation.ai.components
/*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * Decorative background for the AURIX screen.
 *
 * This stays intentionally subtle so that:
 * - chat content remains readable
 * - cards remain visually dominant
 * - the input section doesn't compete with the conversation
 */
@Composable
fun AurixWaveBackground() {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val centerX = size.width / 2f
        val centerY = size.height * 0.36f

        val lineCount = 8
        val baseRadius = size.width * 0.25f

        for (i in 0 until lineCount) {

            val radius =
                baseRadius + (i * 45.dp.toPx())

            val alpha =
                (1f - (i.toFloat() / lineCount)) * 0.18f

            drawCircle(

                color = Color.Black.copy(
                    alpha = alpha
                ),

                radius = radius,

                center = Offset(
                    centerX,
                    centerY
                ),

                style = Stroke(
                    width = 1.dp.toPx()
                )
            )
        }
    }
}

 */


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * AURIX Neural Finance Field
 *
 * A subtle futuristic background representing:
 *
 * - AURIX as the central intelligence
 * - Financial data flowing around the assistant
 * - Connected transaction / analytics nodes
 * - Neural processing and data relationships
 *
 * The background is intentionally low-contrast so that:
 *
 * - Chat messages remain readable
 * - AURIX cards stay visually dominant
 * - The bottom input area remains clear
 * - The design feels premium rather than decorative
 */
@Composable
fun AurixWaveBackground() {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val width = size.width
        val height = size.height

        // -----------------------------------------------------
        // AURIX CORE POSITION
        // -----------------------------------------------------

        val center = Offset(
            x = width * 0.50f,
            y = height * 0.30f
        )

        // -----------------------------------------------------
        // 1. SOFT CENTRAL INTELLIGENCE GLOW
        // -----------------------------------------------------

        drawCircle(

            brush = Brush.radialGradient(

                colors = listOf(
                    Color(0xFF64B5F6).copy(alpha = 0.13f),
                    Color(0xFF42A5F5).copy(alpha = 0.055f),
                    Color.Transparent
                ),

                center = center,

                radius = width * 0.42f
            ),

            radius = width * 0.42f,

            center = center
        )

        // -----------------------------------------------------
        // 2. NEURAL ORBIT RINGS
        //
        // Instead of perfect circles, use stretched elliptical
        // paths to make the background feel more organic.
        // -----------------------------------------------------

        val orbitRings = listOf(
            Pair(0.24f, 0.075f),
            Pair(0.34f, 0.105f),
            Pair(0.45f, 0.145f),
            Pair(0.57f, 0.19f)
        )

        orbitRings.forEachIndexed { index, (radiusX, radiusY) ->

            val path = Path()

            val horizontalRadius = width * radiusX
            val verticalRadius = height * radiusY

            val points = 120

            for (i in 0..points) {

                val angle =
                    (Math.PI * 2.0 * i / points)

                val x =
                    center.x +
                            cos(angle).toFloat() *
                            horizontalRadius

                val y =
                    center.y +
                            sin(angle).toFloat() *
                            verticalRadius

                if (i == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            path.close()

            drawPath(

                path = path,

                color = Color.White.copy(
                    alpha = when (index) {
                        0 -> 0.12f
                        1 -> 0.075f
                        2 -> 0.05f
                        else -> 0.032f
                    }
                ),

                style = Stroke(
                    width = when (index) {
                        0 -> 1.4.dp.toPx()
                        1 -> 1.1.dp.toPx()
                        else -> 0.8.dp.toPx()
                    }
                )
            )
        }

        // -----------------------------------------------------
        // 3. DIAGONAL NEURAL ORBIT
        //
        // Adds asymmetry so the background doesn't look like
        // a simple collection of circles.
        // -----------------------------------------------------

        val diagonalPath = Path()

        val diagonalRadiusX = width * 0.48f
        val diagonalRadiusY = height * 0.17f

        val rotation = Math.toRadians(-18.0)

        val diagonalPoints = 140

        for (i in 0..diagonalPoints) {

            val angle =
                Math.PI * 2.0 * i / diagonalPoints

            val rawX =
                cos(angle).toFloat() * diagonalRadiusX

            val rawY =
                sin(angle).toFloat() * diagonalRadiusY

            val rotatedX =
                rawX * cos(rotation).toFloat() -
                        rawY * sin(rotation).toFloat()

            val rotatedY =
                rawX * sin(rotation).toFloat() +
                        rawY * cos(rotation).toFloat()

            val x =
                center.x + rotatedX

            val y =
                center.y + rotatedY

            if (i == 0) {
                diagonalPath.moveTo(x, y)
            } else {
                diagonalPath.lineTo(x, y)
            }
        }

        diagonalPath.close()

        drawPath(

            path = diagonalPath,

            color = Color(0xFF90CAF9).copy(
                alpha = 0.055f
            ),

            style = Stroke(
                width = 1.dp.toPx()
            )
        )

        // -----------------------------------------------------
        // 4. NEURAL CONNECTIONS
        //
        // These represent relationships between financial
        // data points: transactions → categories → insights.
        // -----------------------------------------------------

        val nodePositions = listOf(

            Offset(
                center.x - width * 0.28f,
                center.y - height * 0.035f
            ),

            Offset(
                center.x - width * 0.17f,
                center.y + height * 0.075f
            ),

            Offset(
                center.x + width * 0.21f,
                center.y - height * 0.055f
            ),

            Offset(
                center.x + width * 0.30f,
                center.y + height * 0.075f
            ),

            Offset(
                center.x - width * 0.06f,
                center.y + height * 0.14f
            ),

            Offset(
                center.x + width * 0.08f,
                center.y - height * 0.12f
            )
        )

        // -----------------------------------------------------
        // Connection lines
        // -----------------------------------------------------

        val connections = listOf(

            0 to 1,
            1 to 4,
            4 to 2,
            2 to 3,
            5 to 0,
            5 to 2,
            5 to 4
        )

        connections.forEach { (startIndex, endIndex) ->

            drawLine(

                color = Color(0xFF90CAF9).copy(
                    alpha = 0.055f
                ),

                start = nodePositions[startIndex],

                end = nodePositions[endIndex],

                strokeWidth = 0.8.dp.toPx()
            )
        }

        // -----------------------------------------------------
        // 5. FINANCIAL DATA NODES
        // -----------------------------------------------------

        nodePositions.forEachIndexed { index, position ->

            val nodeRadius =
                when (index % 3) {
                    0 -> 2.5.dp.toPx()
                    1 -> 2.dp.toPx()
                    else -> 1.5.dp.toPx()
                }

            drawCircle(

                color = Color(0xFF90CAF9).copy(
                    alpha = 0.16f
                ),

                radius = nodeRadius,

                center = position
            )

            // Tiny surrounding halo

            drawCircle(

                color = Color(0xFF64B5F6).copy(
                    alpha = 0.035f
                ),

                radius = nodeRadius * 3.2f,

                center = position
            )
        }

        // -----------------------------------------------------
        // 6. CENTRAL AURIX CORE
        // -----------------------------------------------------

        drawCircle(

            color = Color.White.copy(
                alpha = 0.055f
            ),

            radius = 7.dp.toPx(),

            center = center
        )

        drawCircle(

            color = Color(0xFF90CAF9).copy(
                alpha = 0.12f
            ),

            radius = 3.dp.toPx(),

            center = center
        )

        // -----------------------------------------------------
        // 7. FLOWING DATA WAVES
        //
        // These are subtle horizontal curves near the bottom
        // of the neural field.
        // -----------------------------------------------------

        repeat(3) { waveIndex ->

            val wavePath = Path()

            val startY =
                height * (0.49f + waveIndex * 0.055f)

            val amplitude =
                height * (0.012f + waveIndex * 0.004f)

            val waveLength =
                width * (0.55f + waveIndex * 0.08f)

            val startX =
                (width - waveLength) / 2f

            val endX =
                startX + waveLength

            val controlOffset =
                waveLength * 0.25f

            wavePath.moveTo(
                startX,
                startY
            )

            wavePath.cubicTo(

                startX + controlOffset,
                startY - amplitude,

                endX - controlOffset,
                startY + amplitude,

                endX,
                startY
            )

            drawPath(

                path = wavePath,

                color = Color.White.copy(
                    alpha = when (waveIndex) {
                        0 -> 0.055f
                        1 -> 0.038f
                        else -> 0.025f
                    }
                ),

                style = Stroke(
                    width = 1.dp.toPx()
                )
            )
        }
    }
}
