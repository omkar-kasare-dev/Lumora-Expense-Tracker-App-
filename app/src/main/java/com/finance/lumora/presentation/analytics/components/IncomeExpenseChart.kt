package com.finance.lumora.presentation.analytics.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import com.finance.lumora.presentation.analytics.components.chart.ChartConstants
import com.finance.lumora.presentation.analytics.components.chart.ChartXAxis
import com.finance.lumora.presentation.analytics.components.chart.ChartYAxis
import com.finance.lumora.presentation.analytics.components.chart.drawChartGrid
import com.finance.lumora.presentation.analytics.components.chart.drawGroupedBars
import kotlin.math.max

private val IncomeColor = Color(0xFF4CAF50)
private val ExpenseColor = Color(0xFFE53935)

@Composable
fun IncomeExpenseChart(
    monthlyData: List<IncomeExpenseSummary>,
    modifier: Modifier = Modifier
) {

    if (monthlyData.isEmpty()) return

    var startAnimation by remember(monthlyData) {
        mutableStateOf(false)
    }

    LaunchedEffect(monthlyData) {
        startAnimation = true
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "IncomeExpenseChartAnimation"
    )

    val maxValue = max(
        monthlyData.maxOf { it.income },
        monthlyData.maxOf { it.expense }
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            //-----------------------------------------
            // Header
            //-----------------------------------------

            Text(
                text = "Income vs Expense",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            //-----------------------------------------
            // Legend
            //-----------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                LegendItem(
                    color = IncomeColor,
                    text = "Income"
                )
                Spacer(Modifier.width(10.dp))
                LegendItem(
                    color = ExpenseColor,
                    text = "Expense"
                )

            }

            Spacer(Modifier.height(14.dp))

            //-----------------------------------------
            // Chart
            //-----------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                ChartYAxis(
                    maxValue = maxValue
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ChartConstants.ChartHeight),
                        contentAlignment = Alignment.Center
                    ){
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(ChartConstants.ChartHeight)
                        ) {

                            val chartLeft = ChartConstants.LeftPadding
                            val chartTop = ChartConstants.TopPadding
                            val chartRight = size.width - ChartConstants.RightPadding
                            val chartBottom = size.height - ChartConstants.BottomPadding

                            drawChartGrid(
                                left = chartLeft,
                                top = chartTop,
                                right = chartRight,
                                bottom = chartBottom
                            )

                            drawGroupedBars(
                                monthlyData = monthlyData,
                                chartLeft = chartLeft,
                                chartTop = chartTop,
                                chartRight = chartRight,
                                chartBottom = chartBottom,
                                animationProgress = animationProgress
                            )
                        }

                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    ChartXAxis(
                        monthlyData = monthlyData
                    )


                }
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    text: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color,
                    CircleShape
                )
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}