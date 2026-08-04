package com.finance.lumora.presentation.analytics.intent

import com.finance.lumora.domain.analytics.model.AnalyticsFilter

sealed interface AnalyticsEvent {

    /**
     * Load analytics.
     */
    data object LoadAnalytics : AnalyticsEvent

    /**
     * Refresh analytics.
     */
    data object Refresh : AnalyticsEvent

    /**
     * Change date range.
     */
    data class ChangeDateRange(

        val startDate: Long,

        val endDate: Long

    ) : AnalyticsEvent

    data class ChangeFilter(
        val filter: AnalyticsFilter
    ) : AnalyticsEvent
}