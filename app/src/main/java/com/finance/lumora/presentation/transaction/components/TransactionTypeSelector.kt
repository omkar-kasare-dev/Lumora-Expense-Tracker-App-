package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import com.finance.lumora.data.local.enums.TransactionType


/**
 * Transaction type selector.
 *
 * Allows user to choose:
 * - Income
 * - Expense
 *
 * UI only component.
 * Business logic remains in ViewModel.
 */
@Composable
fun TransactionTypeSelector(

    selectedType: TransactionType,

    onTypeSelected: (
        TransactionType
    ) -> Unit,

    modifier: Modifier = Modifier

) {


    Row(

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {


        TransactionType.entries.forEach { type ->


            FilterChip(

                selected = selectedType == type,

                onClick = {

                    onTypeSelected(type)

                },

                label = {

                    Text(

                        text = type.name
                            .lowercase()
                            .replaceFirstChar {

                                it.uppercase()

                            }

                    )

                }

            )

        }

    }

}