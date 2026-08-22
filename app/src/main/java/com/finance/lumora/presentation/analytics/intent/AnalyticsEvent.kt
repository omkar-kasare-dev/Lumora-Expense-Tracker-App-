package com.finance.lumora.presentation.analytics.intent

import com.finance.lumora.domain.analytics.model.AnalyticsFilter

sealed interface AnalyticsEvent {

    data object LoadAnalytics : AnalyticsEvent

    data object Refresh : AnalyticsEvent


    data class ChangeDateRange(

        val startDate: Long,

        val endDate: Long

    ) : AnalyticsEvent

    data class ChangeFilter(
        val filter: AnalyticsFilter
    ) : AnalyticsEvent
}