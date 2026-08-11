package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateRangeSummaryCard(
    startDate: Long?,
    endDate: Long?,
    onClick: () -> Unit,
    onClear: () -> Unit
){
    val hasDateRange =
        startDate != null ||
                endDate != null

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(18.dp)
            )
            .background(
                MaterialTheme.colorScheme
                    .surfaceContainer
            )
            .border(

                width = 1.dp,

                color =
                    MaterialTheme.colorScheme
                        .outlineVariant,

                shape =
                    RoundedCornerShape(18.dp)

            )
            .clickable(
                onClick = onClick
            )
            .padding(16.dp),

        verticalAlignment =
            Alignment.CenterVertically

    ) {

        Surface(

            modifier =
                Modifier.size(44.dp),

            shape =
                RoundedCornerShape(14.dp),

            color =
                MaterialTheme.colorScheme
                    .primaryContainer

        ) {

            Icon(

                imageVector =
                    Icons.Outlined.CalendarMonth,

                contentDescription =
                    null,

                modifier =
                    Modifier.padding(11.dp),

                tint =
                    MaterialTheme.colorScheme
                        .onPrimaryContainer

            )
        }


        Spacer(
            modifier =
                Modifier.size(14.dp)
        )


        Column(

            modifier =
                Modifier.weight(1f)

        ) {

            Text(

                text = if (
                    hasDateRange
                ) {
                    "Selected period"
                } else {
                    "Choose date range"
                },

                style =
                    MaterialTheme.typography
                        .labelLarge,

                color =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant

            )

            Spacer(
                modifier =
                    Modifier.height(3.dp)
            )

            Text(

                text = when {

                    startDate != null &&
                            endDate != null ->

                        "${formatDate(startDate)}  →  " +
                                formatDate(endDate)

                    startDate != null ->

                        "From ${formatDate(startDate)}"

                    else ->

                        "All dates"

                },

                style =
                    MaterialTheme.typography
                        .bodyLarge,

                fontWeight =
                    FontWeight.Medium

            )
        }


        if (hasDateRange) {

            IconButton(

                onClick = onClear

            ) {

                Icon(

                    imageVector =
                        Icons.Outlined.Close,

                    contentDescription =
                        "Clear date range"

                )
            }

        } else {

            Icon(

                imageVector =
                    Icons.Outlined.Event,

                contentDescription =
                    null,

                tint =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant

            )
        }
    }

}

private fun formatDate(
    timestamp: Long
): String {
    return SimpleDateFormat(

        "dd MMM yyyy",

        Locale.getDefault()

    ).format(
        Date(timestamp)
    )
}