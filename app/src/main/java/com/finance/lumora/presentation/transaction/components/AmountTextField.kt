package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType


/**
 * Input field for transaction amount.
 *
 * Handles only UI input.
 * Business validation is handled by ViewModel.
 */
@Composable
fun AmountTextField(
    amount: String,
    onAmountChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(

        value = amount,

        onValueChange = { value ->

            /*
             * Allow only numeric values
             * with optional decimal point.
             *
             * Examples:
             * 100
             * 100.50
             */
            if (
                value.isEmpty() ||
                value.matches(
                    Regex("^\\d*\\.?\\d*$")
                )
            ) {

                onAmountChanged(value)

            }

        },

        modifier = modifier
            .fillMaxWidth(),

        label = {

            Text(
                text = "Amount"
            )

        },

        placeholder = {

            Text(
                text = "Enter amount"
            )

        },

        leadingIcon = {

            Icon(
                imageVector = Icons.Default.CurrencyRupee,
                contentDescription = "Currency"
            )

        },

        keyboardOptions = KeyboardOptions(

            keyboardType = KeyboardType.Decimal

        ),

        singleLine = true
    )
}