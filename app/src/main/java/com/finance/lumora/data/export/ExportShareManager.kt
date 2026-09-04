package com.finance.lumora.data.export

import android.content.Context
import android.content.Intent
import android.net.Uri

object ExportShareManager {

    fun shareCsv(
        context: Context,
        fileUri: Uri
    ) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(
            shareIntent,
            "Export Lumora Transactions"
        )

        context.startActivity(chooserIntent)
    }
}