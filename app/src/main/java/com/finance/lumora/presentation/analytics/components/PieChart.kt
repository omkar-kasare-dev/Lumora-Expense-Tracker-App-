package com.finance.lumora.presentation.analytics.components



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.CategorySummary
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PieChart(
    categories: List<CategorySummary>,
    modifier: Modifier = Modifier
) {

    if (categories.isEmpty()) return

    //-----------------------------------------
    // Animation
    //-----------------------------------------

    var startAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(categories) {
        startAnimation = true
    }

    val animationProgress by animateFloatAsState(

        targetValue =
            if (startAnimation) 1f else 0f,

        animationSpec = tween(

            durationMillis = 900,

            easing = FastOutSlowInEasing

        ),

        label = "PieChartAnimation"

    )

    //-----------------------------------------
    // Pie Chart
    //-----------------------------------------

    Canvas(

        modifier = modifier
            .size(170.dp)
            .padding(8.dp)

    ) {

        var startAngle = -90f

        categories.forEach { category ->

            val sweepAngle =
                category.percentage *
                        3.6f *
                        animationProgress

            drawArc(

                color = Color(category.color),

                startAngle = startAngle,

                sweepAngle = sweepAngle,

                useCenter = false,

                style = Stroke(

                    width = 38f,

                    cap = StrokeCap.Butt

                ),

                size = Size(
                    width = size.width,
                    height = size.height
                )

            )

            startAngle += sweepAngle

        }

    }

}