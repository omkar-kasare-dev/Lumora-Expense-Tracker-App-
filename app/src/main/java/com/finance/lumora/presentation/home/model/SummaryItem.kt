package com.finance.lumora.presentation.home.model



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SummaryItem(

    val title: String,

    val amount: String,

    val subtitle: String,

    val icon: ImageVector,

    val iconBackground: Color,

    val amountColor: Color

)