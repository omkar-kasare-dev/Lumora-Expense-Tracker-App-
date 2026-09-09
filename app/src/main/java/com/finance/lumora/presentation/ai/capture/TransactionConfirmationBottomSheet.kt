package com.finance.lumora.presentation.ai.capture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.ResolvedTransactionDraft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionConfirmationBottomSheet(
    resolvedDraft: ResolvedTransactionDraft,
    onConfirm: () -> Unit,
    onEdit: () -> Unit,
    onDismiss: () -> Unit,
    isSaving: Boolean = false
) {
    val draft = resolvedDraft.draft
    val category = resolvedDraft.category

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 32.dp
                )
        ) {

            Text(
                text = "Confirm Transaction",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "AURIX found the following transaction:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TransactionConfirmationRow(
                label = "Amount",
                value = formatAmount(
                    amount = draft.amount,
                    currency = draft.currency
                )
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            TransactionConfirmationRow(
                label = "Category",
                value = category?.name
                    ?: draft.categoryName
                    ?: "Not identified"
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            TransactionConfirmationRow(
                label = "Merchant",
                value = draft.merchantName
                    ?: "Not identified"
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            TransactionConfirmationRow(
                label = "Date",
                value = draft.transactionDate
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            if (category == null) {

                Text(
                    text = "AURIX couldn't match this transaction to an existing Lumora category. Please edit it before confirming.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    enabled = !isSaving,
                    onClick = onEdit
                ) {
                    Text("Edit")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = category != null && !isSaving,
                    onClick = onConfirm
                ) {
                    Text(
                        text = if (isSaving) {
                            "Saving..."
                        } else {
                            "Confirm"
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionConfirmationRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun formatAmount(
    amount: Double,
    currency: String?
): String {

    val currencySymbol = when (currency?.uppercase()) {
        "INR" -> "₹"
        "USD" -> "$"
        "EUR" -> "€"
        "GBP" -> "£"
        else -> currency ?: ""
    }

    return if (currencySymbol.isBlank()) {
        String.format("%.2f", amount)
    } else {
        "$currencySymbol${String.format("%.2f", amount)}"
    }
}