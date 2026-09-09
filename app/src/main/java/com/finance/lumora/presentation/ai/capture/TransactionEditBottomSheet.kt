package com.finance.lumora.presentation.ai.capture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.ResolvedTransactionDraft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditBottomSheet(
    resolvedDraft: ResolvedTransactionDraft,
    categories: List<Category>,
    onApplyEdit: (ResolvedTransactionDraft) -> Unit,
    onDismiss: () -> Unit
) {

    val draft = resolvedDraft.draft

    var amountText by remember(draft.amount) {
        mutableStateOf(
            draft.amount.toString()
        )
    }

    var merchantName by remember(draft.merchantName) {
        mutableStateOf(
            draft.merchantName.orEmpty()
        )
    }

    var transactionDate by remember(draft.transactionDate) {
        mutableStateOf(
            draft.transactionDate
        )
    }

    var selectedCategory by remember(
        resolvedDraft.category
    ) {
        mutableStateOf(
            resolvedDraft.category
        )
    }

    var categoryMenuExpanded by remember {
        mutableStateOf(false)
    }

    var amountError by remember {
        mutableStateOf<String?>(null)
    }

    var dateError by remember {
        mutableStateOf<String?>(null)
    }

    /*
     * Keep the selected category synchronized
     * with the resolved draft when the editor opens.
     */
    LaunchedEffect(resolvedDraft.category) {

        selectedCategory =
            resolvedDraft.category
    }

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
                text = "Edit Transaction",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Review and correct the transaction details before confirming.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
             * Amount
             */
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    amountError = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Amount")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = amountError != null,
                supportingText = {
                    amountError?.let {
                        Text(it)
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            /*
             * Category
             */
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            ExposedDropdownMenuBox(
                expanded = categoryMenuExpanded,
                onExpandedChange = {
                    categoryMenuExpanded =
                        !categoryMenuExpanded
                }
            ) {

                OutlinedTextField(
                    value = selectedCategory?.name
                        ?: "Select category",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = {
                        Text("Category")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = categoryMenuExpanded
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = categoryMenuExpanded,
                    onDismissRequest = {
                        categoryMenuExpanded = false
                    }
                ) {

                    categories.forEach { category ->

                        DropdownMenuItem(
                            text = {
                                Text(category.name)
                            },
                            onClick = {

                                selectedCategory =
                                    category

                                categoryMenuExpanded =
                                    false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            /*
             * Merchant
             */
            OutlinedTextField(
                value = merchantName,
                onValueChange = {
                    merchantName = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Merchant")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            /*
             * Date
             */
            OutlinedTextField(
                value = transactionDate,
                onValueChange = {
                    transactionDate = it
                    dateError = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Date")
                },
                placeholder = {
                    Text("YYYY-MM-DD")
                },
                singleLine = true,
                isError = dateError != null,
                supportingText = {
                    dateError?.let {
                        Text(it)
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {

                        val amount =
                            amountText.toDoubleOrNull()

                        if (
                            amount == null ||
                            amount <= 0.0 ||
                            !amount.isFinite()
                        ) {

                            amountError =
                                "Enter a valid amount greater than zero."

                            return@Button
                        }

                        if (
                            !isValidDate(
                                transactionDate
                            )
                        ) {

                            dateError =
                                "Enter a valid date in YYYY-MM-DD format."

                            return@Button
                        }

                        if (selectedCategory == null) {
                            return@Button
                        }

                        val updatedDraft =
                            resolvedDraft.copy(
                                draft = draft.copy(
                                    amount = amount,
                                    merchantName =
                                        merchantName
                                            .trim()
                                            .ifBlank {
                                                null
                                            },
                                    categoryName =
                                        selectedCategory
                                            ?.name,
                                    transactionDate =
                                        transactionDate.trim()
                                ),
                                category =
                                    selectedCategory
                            )

                        onApplyEdit(
                            updatedDraft
                        )
                    }
                ) {
                    Text("Apply Edit")
                }
            }
        }
    }
}

private fun isValidDate(
    date: String
): Boolean {

    return try {

        java.time.LocalDate.parse(
            date.trim(),
            java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
        )

        true

    } catch (_: java.time.format.DateTimeParseException) {

        false
    }
}