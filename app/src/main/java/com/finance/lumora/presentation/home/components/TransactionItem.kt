package com.finance.lumora.presentation.home.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.finance.lumora.presentation.home.model.TransactionUi

@Composable
fun TransactionItem(

    transaction: TransactionUi,

    modifier: Modifier = Modifier,

    onClick: () -> Unit = {}

) {

    ElevatedCard(

        modifier = modifier.fillMaxWidth(),

        onClick = onClick,

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            //--------------------------------------------------
            // Left Side
            //--------------------------------------------------

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(transaction.iconBackground),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = transaction.icon,

                        contentDescription = transaction.category,

                        tint = Color.White

                    )

                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(

                        text = transaction.title,

                        style = MaterialTheme.typography.titleMedium,

                        fontWeight = FontWeight.SemiBold

                    )

                    Text(

                        text = transaction.date,

                        style = MaterialTheme.typography.bodySmall,

                        color = MaterialTheme.colorScheme.onSurfaceVariant

                    )

                }

            }

            //--------------------------------------------------
            // Right Side
            //--------------------------------------------------

            Text(

                text = transaction.amount,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = transaction.amountColor

            )

        }

    }

}