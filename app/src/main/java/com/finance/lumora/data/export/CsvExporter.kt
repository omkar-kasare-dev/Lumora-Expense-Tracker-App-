package com.finance.lumora.data.export

import com.finance.lumora.domain.model.ExportTransaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CsvExporter {

    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun generate(transactions: List<ExportTransaction>): String {
        val csv = StringBuilder()

        // CSV header
        csv.append("Date,Type,Category,Amount,Note\r\n")

        val dateFormatter = SimpleDateFormat(
            DATE_FORMAT,
            Locale.US
        )

        transactions.forEach { transaction ->

            val date = dateFormatter.format(
                Date(transaction.transactionDate)
            )

            csv.append(
                listOf(
                    date,
                    transaction.type.name,
                    transaction.categoryName,
                    String.format(
                        Locale.US,
                        "%.2f",
                        transaction.amount
                    ),
                    transaction.note.orEmpty()
                ).joinToString(",") { field ->
                    escapeCsvField(field)
                }
            )

            csv.append("\r\n")
        }

        return csv.toString()
    }

    private fun escapeCsvField(value: String): String {
        val escapedValue = value.replace("\"", "\"\"")

        return if (
            escapedValue.contains(",") ||
            escapedValue.contains("\"") ||
            escapedValue.contains("\n") ||
            escapedValue.contains("\r")
        ) {
            "\"$escapedValue\""
        } else {
            escapedValue
        }
    }
}