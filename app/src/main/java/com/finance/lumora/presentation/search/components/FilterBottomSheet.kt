package com.finance.lumora.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.search.model.SearchFilters

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filters: SearchFilters,
    categories: List<CategoryEntity>,
    onDismiss: () -> Unit,
    onApply: (SearchFilters) -> Unit,
    onClear: () -> Unit
) {
// ================================================================
// LOCAL FILTER STATE
// ================================================================

    var selectedTransactionType by remember(filters) {

        mutableStateOf(
            filters.transactionType
        )

    }

    var selectedCategoryId by remember(filters) {

        mutableStateOf(
            filters.categoryId
        )

    }

    var minAmountText by remember(filters) {

        mutableStateOf(
            filters.minAmount?.toString() ?: ""
        )

    }

    var maxAmountText by remember(filters) {

        mutableStateOf(
            filters.maxAmount?.toString() ?: ""
        )

    }

    var selectedStartDate by remember(filters) {

        mutableStateOf(
            filters.startDate
        )

    }

    var selectedEndDate by remember(filters) {

        mutableStateOf(
            filters.endDate
        )

    }

    var showDateRangePicker by remember {

        mutableStateOf(false)

    }


// ================================================================
// DATE RANGE PICKER
// ================================================================

    if (showDateRangePicker) {

        val dateRangePickerState =
            rememberDateRangePickerState(

                initialSelectedStartDateMillis =
                    selectedStartDate,

                initialSelectedEndDateMillis =
                    selectedEndDate

            )

        ModalBottomSheet(

            onDismissRequest = {

                showDateRangePicker = false

            }

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp
                    )

            ) {

                // ====================================================
                // HEADER
                // ====================================================

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(

                            text = "Select date range",

                            style =
                                MaterialTheme.typography
                                    .headlineSmall,

                            fontWeight =
                                FontWeight.SemiBold

                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(

                            text = "Choose the period you want to search",

                            style =
                                MaterialTheme.typography
                                    .bodyMedium,

                            color =
                                MaterialTheme.colorScheme
                                    .onSurfaceVariant

                        )
                    }

                    IconButton(

                        onClick = {

                            showDateRangePicker = false

                        }

                    ) {

                        Icon(

                            imageVector =
                                Icons.Outlined.Close,

                            contentDescription =
                                "Close"

                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                // ====================================================
                // CALENDAR
                // ====================================================

                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(24.dp),

                    tonalElevation = 2.dp

                ) {

                    DateRangePicker(

                        state =
                            dateRangePickerState,

                        modifier =
                            Modifier.fillMaxWidth()
                                .height(350.dp),

                        title = null,

                        headline = null,

                        showModeToggle = false

                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )


                // ====================================================
                // DATE ACTIONS
                // ====================================================

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)

                ) {

                    TextButton(

                        onClick = {

                            selectedStartDate = null
                            selectedEndDate = null

                            showDateRangePicker = false

                        },

                        modifier =
                            Modifier.weight(1f)

                    ) {

                        Icon(

                            imageVector =
                                Icons.Outlined.Refresh,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(18.dp)

                        )

                        Spacer(
                            modifier =
                                Modifier.size(6.dp)
                        )

                        Text("Clear")

                    }


                    Button(

                        onClick = {

                            selectedStartDate =
                                dateRangePickerState
                                    .selectedStartDateMillis

                            selectedEndDate =
                                dateRangePickerState
                                    .selectedEndDateMillis

                            showDateRangePicker = false

                        },

                        modifier =
                            Modifier.weight(1f)

                    ) {

                        Text("Done")

                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }

        return
    }


// ================================================================
// MAIN FILTER SHEET
// ================================================================

    ModalBottomSheet(

        onDismissRequest = onDismiss

    ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                )

        ) {
            // ========================================================
            // HEADER
            // ========================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Column(

                    modifier =
                        Modifier.weight(1f)

                ) {

                    Text(

                        text = "Filters",

                        style =
                            MaterialTheme.typography
                                .headlineSmall,

                        fontWeight =
                            FontWeight.SemiBold

                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(

                        text = "Refine your transactions",

                        style =
                            MaterialTheme.typography
                                .bodyMedium,

                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant

                    )
                }

                IconButton(

                    onClick = onDismiss

                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Close,

                        contentDescription =
                            "Close"

                    )
                }
            }
            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            // ========================================================
            // TRANSACTION TYPE
            // ========================================================

            Text(

                text = "Transaction Type",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold

            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            LazyRow(

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)

            ) {

                item {

                    FilterChip(

                        selected =
                            selectedTransactionType ==
                                    TransactionType.INCOME,

                        onClick = {

                            selectedTransactionType =
                                if (
                                    selectedTransactionType ==
                                    TransactionType.INCOME
                                ) {
                                    null
                                } else {
                                    TransactionType.INCOME
                                }

                        },

                        label = {
                            Text("Income")
                        },

                        colors =
                            FilterChipDefaults
                                .filterChipColors()

                    )
                }

                item {

                    FilterChip(

                        selected =
                            selectedTransactionType ==
                                    TransactionType.EXPENSE,

                        onClick = {

                            selectedTransactionType =
                                if (
                                    selectedTransactionType ==
                                    TransactionType.EXPENSE
                                ) {
                                    null
                                } else {
                                    TransactionType.EXPENSE
                                }

                        },

                        label = {
                            Text("Expense")
                        },

                        colors =
                            FilterChipDefaults
                                .filterChipColors()

                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(22.dp)
            )


            // ========================================================
            // CATEGORY
            // ========================================================

            Text(

                text = "Category",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold

            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            if (categories.isEmpty()) {

                Text(

                    text = "No categories available",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant

                )

            } else {

                LazyRow(

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)

                ) {

                    items(

                        items = categories,

                        key = {
                            it.id
                        }

                    ) { category ->

                        FilterChip(

                            selected =
                                selectedCategoryId ==
                                        category.id,

                            onClick = {

                                selectedCategoryId =
                                    if (
                                        selectedCategoryId ==
                                        category.id
                                    ) {
                                        null
                                    } else {
                                        category.id
                                    }

                            },

                            label = {

                                Text(
                                    category.name
                                )

                            },

                            colors =
                                FilterChipDefaults
                                    .filterChipColors()

                        )
                    }
                }
            }
            Spacer(
                modifier =
                    Modifier.height(22.dp)
            )
            // ========================================================
            // AMOUNT RANGE
            // ========================================================

            Text(

                text = "Amount",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold

            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                OutlinedTextField(

                    value =
                        minAmountText,

                    onValueChange = { value ->

                        minAmountText =
                            value.filter {

                                it.isDigit() ||
                                        it == '.'

                            }

                    },

                    modifier =
                        Modifier.weight(1f),

                    singleLine = true,

                    label = {
                        Text("Minimum")
                    }

                )

                OutlinedTextField(

                    value =
                        maxAmountText,

                    onValueChange = { value ->

                        maxAmountText =
                            value.filter {

                                it.isDigit() ||
                                        it == '.'

                            }

                    },

                    modifier =
                        Modifier.weight(1f),

                    singleLine = true,

                    label = {
                        Text("Maximum")
                    }

                )
            }
            Spacer(
                modifier =
                    Modifier.height(22.dp)
            )
            // ========================================================
            // DATE RANGE
            // ========================================================

            Text(

                text = "Date Range",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold

            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )
            DateRangeSummaryCard(

                startDate =
                    selectedStartDate,

                endDate =
                    selectedEndDate,

                onClick = {

                    showDateRangePicker = true

                },

                onClear = {

                    selectedStartDate = null
                    selectedEndDate = null

                }

            )


            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )


            // ========================================================
            // BOTTOM ACTIONS
            // ========================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                TextButton(

                    onClick = {

                        selectedTransactionType = null
                        selectedCategoryId = null
                        minAmountText = ""
                        maxAmountText = ""
                        selectedStartDate = null
                        selectedEndDate = null

                        onClear()

                    },

                    modifier =
                        Modifier.weight(1f)

                ) {

                    Text("Clear all")

                }


                Button(

                    onClick = {

                        val updatedFilters =
                            SearchFilters(

                                transactionType =
                                    selectedTransactionType,

                                categoryId =
                                    selectedCategoryId,

                                startDate =
                                    selectedStartDate,

                                endDate =
                                    selectedEndDate,

                                minAmount =
                                    minAmountText
                                        .toDoubleOrNull(),

                                maxAmount =
                                    maxAmountText
                                        .toDoubleOrNull()

                            )

                        onApply(
                            updatedFilters
                        )

                    },

                    modifier =
                        Modifier.weight(1f)

                ) {

                    Text("Apply filters")

                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )
        }
    }


}



