package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Date selector for transactions.
 *
 * UI component only.
 * Stores date as Long timestamp.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(

    selectedDate: Long,

    onDateSelected: (
        Long
    ) -> Unit,

    modifier: Modifier = Modifier

) {

    var showDialog by remember {

        mutableStateOf(false)

    }


    val formattedDate = remember(selectedDate) {

        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        )
            .format(
                Date(selectedDate)
            )

    }


    OutlinedTextField(

        value = formattedDate,

        onValueChange = {},

        readOnly = true,

        modifier = modifier
            .fillMaxWidth()
            .clickable {

                showDialog = true

            },

        label = {

            Text(
                text = "Date"
            )

        },

        trailingIcon = {

            Icon(

                imageVector = Icons.Default.CalendarMonth,

                contentDescription = "Select Date"

            )

        },

        singleLine = true

    )


    if (showDialog) {


        val datePickerState = rememberDatePickerState(

            initialSelectedDateMillis = selectedDate

        )


        DatePickerDialog(

            onDismissRequest = {

                showDialog = false

            },


            confirmButton = {


                TextButton(

                    onClick = {


                        datePickerState
                            .selectedDateMillis
                            ?.let { date ->


                                onDateSelected(
                                    date
                                )


                            }


                        showDialog = false

                    }

                ) {

                    Text(
                        text = "OK"
                    )

                }

            },


            dismissButton = {


                TextButton(

                    onClick = {

                        showDialog = false

                    }

                ) {

                    Text(
                        text = "Cancel"
                    )

                }

            }

        ) {


            DatePicker(

                state = datePickerState

            )

        }

    }

}