package com.finance.lumora.presentation.analytics.components.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ChartYAxis(
    maxValue: Double
) {

    val labels = List(6) { index ->

        val value =
            maxValue - (index * (maxValue / 5))

        value

    }

    Column(
        modifier = Modifier
            .height(ChartConstants.ChartHeight)
            .width(42.dp)
            .padding(
                top = ChartConstants.TopPadding.dp,
                bottom = ChartConstants.BottomPadding.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        labels.forEach {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = formatAxisValue(it),
                style = MaterialTheme.typography.labelSmall
            )

        }

    }

}
private fun formatAxisValue(
    value: Double
): String {

    return when {

        value >= 1_000_000 ->
            String.format("%.1fM", value / 1_000_000)

        value >= 1_000 ->
            String.format("%.1fK", value / 1_000)

        else ->
            value.toInt().toString()

    }

}