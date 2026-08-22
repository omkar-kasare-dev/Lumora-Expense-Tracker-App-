package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale

/**
 * Professional Material 3 Transaction Item
 *
 * Drop-in replacement for the existing TransactionItem.
 * No business logic has been changed.
 */
@Composable
fun TransactionItem(
    transaction: Transaction,
    category: Category?,
    onEditClick: (Transaction) -> Unit,
    onDeleteClick: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {

    val amountColor =
        if (transaction.type == TransactionType.INCOME)
            Color(0xFF2E7D32)
        else
            Color(0xFFC62828)

    val categoryColor =
        if (transaction.type == TransactionType.INCOME)
            Color(0xFFE8F5E9)
        else
            Color(0xFFFFEBEE)

    val iconTint =
        if (transaction.type == TransactionType.INCOME)
            Color(0xFF2E7D32)
        else
            Color(0xFFC62828)

    val amountPrefix =
        if (transaction.type == TransactionType.INCOME)
            "+ ₹"
        else
            "- ₹"

    val formattedDate = SimpleDateFormat(
        "dd MMM yyyy",
        LocalLocale.current.platformLocale
    ).format(
        Date(transaction.transactionDate)
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            //--------------------------------------------------
            // Top Row
            //--------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                //--------------------------------------------------
                // Category Avatar
                //--------------------------------------------------

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(categoryColor),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = null,
                        tint = iconTint
                    )

                }

                Spacer(
                    modifier = Modifier.size(14.dp)
                )

                //--------------------------------------------------
                // Category + Date
                //--------------------------------------------------

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = category?.name ?: "Unknown Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                //--------------------------------------------------
                // Amount
                //--------------------------------------------------

                Text(
                    text = amountPrefix + "%.2f".format(transaction.amount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )

            }
/*
            //--------------------------------------------------
            // Note
            //--------------------------------------------------

            if (!transaction.note.isNullOrBlank()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Note",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = transaction.note,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Divider()

            Spacer(
                modifier = Modifier.height(12.dp)
            )
            */

            //--------------------------------------------------
            // Action Buttons
            //--------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                FilledTonalButton(
                    onClick = {
                        onEditClick(transaction)

                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )

                    Spacer(
                        modifier = Modifier.size(6.dp)
                    )

                    Text("Edit")

                }

                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                FilledTonalButton(
                    onClick = {
                        onDeleteClick(transaction)
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )

                    Spacer(
                        modifier = Modifier.size(6.dp)
                    )

                    Text("Delete")

                }

            }



        }

    }
}