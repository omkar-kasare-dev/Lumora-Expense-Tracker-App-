package com.finance.lumora.domain.analytics.model

enum class AnalyticsFilter(
    val displayName: String
) {

    TODAY("Today"),

    THIS_WEEK("This Week"),

    THIS_MONTH("This Month"),

    LAST_MONTH("Last Month"),

    THIS_YEAR("This Year"),

    CUSTOM("Custom")

}