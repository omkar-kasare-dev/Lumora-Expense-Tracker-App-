package com.finance.lumora.presentation.splash.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue


@Composable
fun SplashBackground(
    modifier: Modifier = Modifier
) {


    val infiniteTransition =
        rememberInfiniteTransition(
            label = "Aurora"
        )

    val gradientOffset by infiniteTransition.animateFloat(

        initialValue = -300f,

        targetValue = 300f,

        animationSpec = infiniteRepeatable(

            animation = tween(

                durationMillis = 9000,

                easing = LinearEasing

            ),

            repeatMode = RepeatMode.Reverse

        ),

        label = "GradientOffset"

    )



    //--------------------------------------------------------------------------------------------
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        /*
         * ==========================
         * Large Soft Glow (Top Left)
         * ==========================
         */

        drawCircle(

            Brush.linearGradient(

                colors = listOf(

                    Color(0xFF0B1024),

                    Color(0xFF2E1D62),

                    Color(0xFF5C3DFF),

                    Color(0xFF9A52FF),

                    Color(0xFF1E5BFF)

                ),

                start = Offset(
                    gradientOffset,
                    0f
                ),

                end = Offset(
                    gradientOffset + 900f,
                    1800f
                )

            ),

            radius = size.minDimension * 0.45f,

            center = Offset(
                x = size.width * 0.20f,
                y = size.height * 0.18f
            )

        )

        /*
         * ==========================
         * Large Soft Glow (Top Right)
         * ==========================
         */

        drawCircle(

            brush = Brush.radialGradient(

                colors = listOf(
                    Color(0x15FFFFFF),
                    Color.Transparent
                ),

                center = Offset(
                    x = size.width * 0.82f,
                    y = size.height * 0.12f
                ),

                radius = size.minDimension * 0.35f

            ),

            radius = size.minDimension * 0.35f,

            center = Offset(
                x = size.width * 0.82f,
                y = size.height * 0.12f
            )

        )

        /*
         * ==========================
         * Decorative Stars
         * ==========================
         */

        val stars = listOf(

            Offset(size.width * 0.15f, size.height * 0.16f),
            Offset(size.width * 0.82f, size.height * 0.10f),
            Offset(size.width * 0.90f, size.height * 0.28f),
            Offset(size.width * 0.12f, size.height * 0.42f),
            Offset(size.width * 0.92f, size.height * 0.46f),
            Offset(size.width * 0.08f, size.height * 0.58f)

        )

        stars.forEach {

            drawCircle(

                color = Color.White,

                radius = 4f,

                center = it

            )

        }

        /*
         * ==========================
         * Golden Sparkles
         * ==========================
         */

        val sparkles = listOf(

            Offset(size.width * 0.80f, size.height * 0.20f),
            Offset(size.width * 0.72f, size.height * 0.34f),
            Offset(size.width * 0.30f, size.height * 0.60f),
            Offset(size.width * 0.55f, size.height * 0.72f)

        )

        sparkles.forEach {

            drawCircle(

                color = Color(0xFFFFD166),

                radius = 5f,

                center = it

            )

        }

        /*
         * ==========================
         * Tiny Glow Particles
         * ==========================
         */

        repeat(18) { index ->

            val x = (index * 73 % size.width.toInt()).toFloat()

            val y = (index * 181 % size.height.toInt()).toFloat()

            drawCircle(

                brush = Brush.radialGradient(

                    colors = listOf(
                        Color.White.copy(alpha = 0.18f),
                        Color.Transparent
                    )

                ),

                radius = 10f,

                center = Offset(x, y)

            )

        }

    }

}