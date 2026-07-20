package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardTopBar(

    userName: String = "User",

    modifier: Modifier = Modifier,

    onNotificationClick: () -> Unit = {},

    onProfileClick: () -> Unit = {}

) {

    Surface(

        modifier = modifier.fillMaxWidth(),

        color = MaterialTheme.colorScheme.surface,

        tonalElevation = 2.dp,

        shadowElevation = 0.dp

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically

        ) {

            //--------------------------------------------------
            // Greeting
            //--------------------------------------------------

            Column {

                Text(

                    text = greeting(),

                    style = MaterialTheme.typography.titleMedium,

                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )

                Text(

                    text = userName,

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold

                )

                Text(

                    text = todayDate(),

                    style = MaterialTheme.typography.bodyMedium,

                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )

            }

            //--------------------------------------------------
            // Actions
            //--------------------------------------------------

            Row {

                IconButton(

                    onClick = onNotificationClick

                ) {

                    Icon(

                        imageVector = Icons.Outlined.Notifications,

                        contentDescription = "Notifications"

                    )

                }

                IconButton(

                    onClick = onProfileClick

                ) {

                    Icon(

                        imageVector = Icons.Outlined.Person,

                        contentDescription = "Profile"

                    )

                }

            }

        }

    }

}

/**
 * Returns greeting according to current time.
 */
private fun greeting(): String {

    val hour = java.util.Calendar
        .getInstance()
        .get(java.util.Calendar.HOUR_OF_DAY)

    return when (hour) {

        in 5..11 -> "Good Morning 👋"

        in 12..16 -> "Good Afternoon ☀️"

        in 17..20 -> "Good Evening 🌇"

        else -> "Good Night 🌙"

    }

}

/**
 * Returns today's formatted date.
 */
private fun todayDate(): String {

    return SimpleDateFormat(

        "EEEE, dd MMMM yyyy",

        Locale.getDefault()

    ).format(Date())

}