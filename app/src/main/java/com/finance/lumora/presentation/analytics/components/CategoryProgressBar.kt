package com.finance.lumora.presentation.analytics.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.finance.lumora.domain.analytics.model.CategorySummary

@Composable
fun CategoryProgressBar(
    category: CategorySummary
){
    val progress by animateFloatAsState(
        targetValue = category.percentage / 100f
    )
    LinearProgressIndicator(

        progress = { progress
            category.percentage / 100f
        },

        modifier = Modifier.fillMaxWidth(),

        color = Color(category.color)

    )

}