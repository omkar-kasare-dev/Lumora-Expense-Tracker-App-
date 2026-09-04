package com.finance.lumora.data.export

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExportFileManager(
    private val context: Context
) {

    fun createCsvFile(csvContent: String): Uri {

        val exportDirectory = File(
            context.cacheDir,
            "exports"
        )

        if (!exportDirectory.exists()) {
            exportDirectory.mkdirs()
        }

        val dateFormatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        )

        val fileName = "lumora_transactions_${
            dateFormatter.format(Date())
        }.csv"

        val file = File(
            exportDirectory,
            fileName
        )

        file.writeText(
            text = csvContent,
            charset = Charsets.UTF_8
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}