package com.finance.lumora.presentation.home.model


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TransactionUi(

    val id: Long,

    val title: String,

    val category: String,

    val amount: String,

    val date: String,

    val icon: ImageVector,

    val iconBackground: Color,

    val amountColor: Color,

    val isIncome: Boolean

)