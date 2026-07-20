package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


/**
 * Button used for creating or updating transactions.
 *
 * UI responsibility only.
 */
@Composable
fun SaveTransactionButton(

    isEditMode: Boolean,

    onClick: () -> Unit,

    modifier: Modifier = Modifier

) {


    Button(

        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()

    ) {


        Text(

            text = if (isEditMode) {

                "Update Transaction"

            } else {

                "Save Transaction"

            }

        )

    }

}