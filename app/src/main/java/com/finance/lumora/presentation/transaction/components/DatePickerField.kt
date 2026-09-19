package com.finance.lumora.presentation.transaction.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

/**
 * Date selector for transactions.
 *
 * UI component only.
 * selectedDate : epoch millis in LOCAL time (<= 0 means "today").
 * onDateSelected : returns epoch millis of the START of the chosen day (LOCAL time).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    allowFutureDates: Boolean = false
) {
    // Survives rotation
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Guard against 0L -> 1970
    val effectiveDate = if (selectedDate > 0L) selectedDate else System.currentTimeMillis()

    val formattedDate = remember(effectiveDate) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            .format(Date(effectiveDate))
    }

    // ---------------- Field ----------------
    Box(modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Date") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Select Date"
                )
            },
            singleLine = true
        )

        // FIX 1: transparent overlay that reliably receives the tap
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    focusManager.clearFocus()   // hide keyboard
                    showDialog = true
                }
        )
    }

    // ---------------- Dialog ----------------
    if (showDialog) {

        val datePickerState = rememberDatePickerState(
            // FIX 2a: local -> UTC midnight (what DatePicker expects)
            initialSelectedDateMillis = effectiveDate.localToPickerUtc(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    allowFutureDates || utcTimeMillis <= todayPickerUtc()

                override fun isSelectableYear(year: Int): Boolean =
                    allowFutureDates || year <= LocalDate.now().year
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDialog = false },

            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let { utcMillis ->
                            // FIX 2b: UTC midnight -> start of that day in local time
                            onDateSelected(utcMillis.pickerUtcToLocalStartOfDay())
                        }
                        showDialog = false
                    }
                ) {
                    Text(text = "OK")
                }
            },

            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// ---------------------------------------------------------------------------
// Time-zone helpers (DatePicker = UTC midnight, app data = local time)
// ---------------------------------------------------------------------------

private fun Long.localToPickerUtc(): Long =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

private fun Long.pickerUtcToLocalStartOfDay(): Long =
    Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

private fun todayPickerUtc(): Long =
    LocalDate.now()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()