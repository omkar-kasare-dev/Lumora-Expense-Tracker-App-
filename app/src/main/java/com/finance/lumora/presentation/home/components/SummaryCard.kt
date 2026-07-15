package com.finance.lumora.presentation.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.home.model.SummaryItem

@Composable
fun SummaryCard(

    item: SummaryItem,

    modifier: Modifier = Modifier,

    onClick: () -> Unit = {}

) {

    ElevatedCard(

        modifier = modifier,

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp
        ),

        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        onClick = onClick

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),

            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Column {

                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(item.iconBackground),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = item.icon,

                        contentDescription = item.title,

                        tint = item.amountColor

                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(

                    text = item.title,

                    style = MaterialTheme.typography.bodyLarge

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = item.amount,

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    color = item.amountColor

                )

            }

            Text(

                text = item.subtitle,

                style = MaterialTheme.typography.bodyMedium,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

        }

    }
}