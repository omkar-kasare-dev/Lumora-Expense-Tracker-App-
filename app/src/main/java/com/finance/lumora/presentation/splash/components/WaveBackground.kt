package com.finance.lumora.presentation.splash.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun WaveBackground(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {

        /*
         * =====================================
         * Wave 1 (Purple)
         * =====================================
         */

        val wave1 = Path().apply {

            moveTo(0f, size.height * 0.72f)

            cubicTo(
                size.width * 0.18f,
                size.height * 0.42f,

                size.width * 0.38f,
                size.height * 1.05f,

                size.width * 0.65f,
                size.height * 0.70f
            )

            cubicTo(
                size.width * 0.82f,
                size.height * 0.48f,

                size.width,
                size.height * 0.76f,

                size.width,
                size.height
            )

            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(

            path = wave1,

            brush = Brush.verticalGradient(

                listOf(

                    Color(0xAA8E5BFF),

                    Color(0x553F3DFF),

                    Color.Transparent

                )

            )

        )

        /*
         * =====================================
         * Wave 2 (Blue)
         * =====================================
         */

        val wave2 = Path().apply {

            moveTo(0f, size.height * 0.88f)

            cubicTo(

                size.width * 0.25f,
                size.height * 0.55f,

                size.width * 0.55f,
                size.height * 1.10f,

                size.width * 0.78f,
                size.height * 0.60f

            )

            cubicTo(

                size.width * 0.90f,
                size.height * 0.35f,

                size.width,
                size.height * 0.65f,

                size.width,
                size.height

            )

            lineTo(size.width, size.height)

            lineTo(0f, size.height)

            close()

        }

        drawPath(

            path = wave2,

            brush = Brush.verticalGradient(

                listOf(

                    Color(0xAA3A9DFF),

                    Color(0x552D63FF),

                    Color.Transparent

                )

            )

        )

        /*
         * =====================================
         * Cyan Highlight
         * =====================================
         */

        val highlight = Path().apply {

            moveTo(size.width * 0.45f, size.height * 0.74f)

            cubicTo(

                size.width * 0.60f,
                size.height * 0.45f,

                size.width * 0.82f,
                size.height * 0.82f,

                size.width,
                size.height * 0.40f

            )

        }

        drawPath(

            path = highlight,

            brush = Brush.linearGradient(

                listOf(

                    Color.Transparent,

                    Color(0xFF45D9FF),

                    Color.Transparent

                )

            ),

            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 6f
            )

        )

        /*
         * =====================================
         * Purple Highlight
         * =====================================
         */

        val highlight2 = Path().apply {

            moveTo(0f, size.height * 0.78f)

            cubicTo(

                size.width * 0.18f,
                size.height * 0.52f,

                size.width * 0.42f,
                size.height,

                size.width * 0.60f,
                size.height * 0.74f

            )

        }

        drawPath(

            path = highlight2,

            brush = Brush.linearGradient(

                listOf(

                    Color.Transparent,

                    Color(0xFFA06DFF),

                    Color.Transparent

                )

            ),

            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 5f
            )

        )

        /*
         * =====================================
         * Soft Glow
         * =====================================
         */

        drawCircle(

            brush = Brush.radialGradient(

                colors = listOf(

                    Color(0x2238CFFF),

                    Color.Transparent

                )

            ),

            radius = size.width * 0.45f,

            center = Offset(

                size.width * 0.82f,

                size.height * 0.42f

            ),

            blendMode = BlendMode.Screen

        )

    }

}