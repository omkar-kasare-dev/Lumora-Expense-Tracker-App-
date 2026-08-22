package com.finance.lumora.domain.analytics.model

/**
 * Represents a date range used by the
 * Analytics module.
 *
 * Example:
 * startDate = 1751328000000
 * endDate   = 1754006399999
 */
data class DateRange(
    val startDate: Long,

    val endDate: Long

) {
    val isValid: Boolean
        get() = startDate <= endDate

}