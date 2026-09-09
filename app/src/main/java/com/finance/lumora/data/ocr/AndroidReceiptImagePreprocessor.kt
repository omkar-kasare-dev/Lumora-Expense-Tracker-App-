package com.finance.lumora.data.ocr

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.finance.lumora.domain.ocr.ReceiptImagePreprocessor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidReceiptImagePreprocessor @Inject constructor(
    @ApplicationContext private val context: Context
) : ReceiptImagePreprocessor {

    override suspend fun preprocess(
        imageUri: Uri
    ): Uri {

        val inputStream = context.contentResolver
            .openInputStream(imageUri)
            ?: throw IllegalStateException(
                "Unable to open receipt image."
            )

        val bitmap = inputStream.use {
            BitmapFactory.decodeStream(it)
        } ?: throw IllegalStateException(
            "Unable to decode receipt image."
        )

        /*
         * Basic preprocessing will be added after the
         * end-to-end OCR pipeline is verified.
         */
        bitmap.recycle()

        return imageUri
    }
}