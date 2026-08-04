package com.finance.lumora.domain.analytics.utils

import com.finance.lumora.domain.analytics.model.AnalyticsFilter
import com.finance.lumora.domain.analytics.model.DateRange
import java.util.Calendar


object DateRangeFactory {


    fun create(
        filter: AnalyticsFilter
    ): DateRange {

        return when (filter) {
            AnalyticsFilter.TODAY ->
                today()

            AnalyticsFilter.THIS_WEEK ->
                thisWeek()

            AnalyticsFilter.THIS_MONTH ->
                thisMonth()

            AnalyticsFilter.LAST_MONTH ->
                lastMonth()

            AnalyticsFilter.THIS_YEAR ->
                thisYear()

            AnalyticsFilter.CUSTOM ->
                thisMonth() // Temporary fallback
        }

    }

    /**
     * Current calendar month.
     */
    private fun thisMonth(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            set(Calendar.DAY_OF_MONTH, 1)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        calendar.apply {

            add(Calendar.MONTH, 1)

            add(Calendar.MILLISECOND, -1)

        }

        val end = calendar.timeInMillis

        return DateRange(
            startDate = start,
            endDate = end
        )

    }

    /**
     * Previous calendar month.
     */
    private fun lastMonth(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            add(Calendar.MONTH, -1)

            set(Calendar.DAY_OF_MONTH, 1)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        calendar.apply {

            add(Calendar.MONTH, 1)

            add(Calendar.MILLISECOND, -1)

        }

        val end = calendar.timeInMillis

        return DateRange(
            startDate = start,
            endDate = end
        )

    }

    /**
     * Last N calendar months
     * including the current month.
     */
    private fun lastMonths(
        months: Int
    ): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            add(Calendar.MONTH, -(months - 1))

            set(Calendar.DAY_OF_MONTH, 1)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        val endCalendar = Calendar.getInstance()

        endCalendar.apply {

            add(Calendar.MONTH, 1)

            set(Calendar.DAY_OF_MONTH, 1)

            add(Calendar.MILLISECOND, -1)

        }

        return DateRange(
            startDate = start,
            endDate = endCalendar.timeInMillis
        )

    }

    /**
     * Current calendar year.
     */
    private fun thisYear(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            set(Calendar.MONTH, Calendar.JANUARY)

            set(Calendar.DAY_OF_MONTH, 1)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        calendar.apply {

            add(Calendar.YEAR, 1)

            add(Calendar.MILLISECOND, -1)

        }

        val end = calendar.timeInMillis

        return DateRange(
            startDate = start,
            endDate = end
        )

    }

    /**
     * Current day.
     *
     * Start : Today 00:00:00.000
     * End   : Today 23:59:59.999
     */
    private fun today(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        calendar.apply {

            add(Calendar.DAY_OF_MONTH, 1)

            add(Calendar.MILLISECOND, -1)

        }

        val end = calendar.timeInMillis

        return DateRange(

            startDate = start,

            endDate = end

        )

    }

    /**
     * Current calendar week.
     *
     * Start : First day of current week
     * End   : Last day of current week
     */
    private fun thisWeek(): DateRange {

        val calendar = Calendar.getInstance()

        calendar.apply {

            firstDayOfWeek = Calendar.MONDAY

            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        val start = calendar.timeInMillis

        calendar.apply {

            add(Calendar.WEEK_OF_YEAR, 1)

            add(Calendar.MILLISECOND, -1)

        }

        val end = calendar.timeInMillis

        return DateRange(

            startDate = start,

            endDate = end

        )

    }

}