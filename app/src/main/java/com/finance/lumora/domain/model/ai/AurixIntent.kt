package com.finance.lumora.domain.model.ai

sealed interface AurixIntent {
    // Single-entry transaction capture
    data class AddTransaction(val rawText: String) : AurixIntent

    // Financial query/analytics inquiry
    data class QueryFinance(
        val rawQuery: String,
        val queryType: QueryType,
        val categoryName: String? = null,
        val startDateIso: String? = null,
        val endDateIso: String? = null
    ) : AurixIntent
}

enum class QueryType {
    TOTAL_EXPENSE,
    TOTAL_INCOME,
    CATEGORY_SPENDING,
    RECENT_TRANSACTIONS,
    UNKNOWN
}