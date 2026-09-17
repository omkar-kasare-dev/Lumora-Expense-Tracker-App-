package com.finance.lumora.domain.model.ai

data class FinancialQueryResult(
    val formattedAnswer: String,
    val totalAmount: Double? = null,
    val queryType: QueryType = QueryType.UNKNOWN
)