package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchResultCard(
    category: String,
    title: String,
    amount: String,
    date: String
) {


    ElevatedCard(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(20.dp),

        colors =
            CardDefaults.elevatedCardColors(

                containerColor =
                    MaterialTheme.colorScheme
                        .surfaceContainerLow

            ),

        elevation =
            CardDefaults.elevatedCardElevation(

                defaultElevation = 2.dp

            )

    ) {

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            // ========================================================
            // TRANSACTION ICON
            // ========================================================

            Surface(

                modifier =
                    Modifier.size(48.dp),

                shape =
                    CircleShape,

                color =
                    MaterialTheme.colorScheme
                        .primaryContainer

            ) {

                Icon(

                    imageVector =
                        Icons.Outlined.Receipt,

                    contentDescription =
                        "Transaction",

                    modifier =
                        Modifier
                            .padding(12.dp),

                    tint =
                        MaterialTheme.colorScheme
                            .onPrimaryContainer

                )
            }


            Spacer(
                modifier =
                    Modifier.width(14.dp)
            )


            // ========================================================
            // TRANSACTION INFORMATION
            // ========================================================

            Column(

                modifier =
                    Modifier.weight(1f),

                verticalArrangement =
                    Arrangement.spacedBy(4.dp)

            ) {

                // ----------------------------------------------------
                // CATEGORY
                // ----------------------------------------------------

                Text(

                    text =
                        category,

                    style =
                        MaterialTheme.typography
                            .labelMedium,

                    color =
                        MaterialTheme.colorScheme
                            .primary

                )


                // ----------------------------------------------------
                // TRANSACTION TITLE
                // ----------------------------------------------------

                Text(

                    text =
                        title,

                    style =
                        MaterialTheme.typography
                            .titleMedium,

                    color =
                        MaterialTheme.colorScheme
                            .onSurface,

                    maxLines = 1

                )


                // ----------------------------------------------------
                // DATE
                // ----------------------------------------------------

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.CalendarToday,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(14.dp),

                        tint =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant

                    )

                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )

                    Text(

                        text =
                            date,

                        style =
                            MaterialTheme.typography
                                .bodySmall,

                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant

                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )


            // ========================================================
            // AMOUNT
            // ========================================================

            Column(

                horizontalAlignment =
                    Alignment.End

            ) {

                Text(

                    text =
                        amount,

                    style =
                        MaterialTheme.typography
                            .titleMedium,

                    color =
                        MaterialTheme.colorScheme
                            .onSurface

                )

            }
        }
    }


}
