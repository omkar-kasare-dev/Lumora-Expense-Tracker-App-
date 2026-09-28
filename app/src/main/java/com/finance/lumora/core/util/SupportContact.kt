package com.finance.lumora.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.net.toUri
import com.finance.lumora.BuildConfig

object SupportContact {

    // TODO: replace with your real address before release. These can
    // be the same address, or separate ones if you ever want to split
    // privacy and legal requests.
    const val PRIVACY_EMAIL = "privacy.lumora@gmail.com"
    const val LEGAL_EMAIL = "privacy.lumora@gmail.com"
}

/**
 * Opens the user's email app with a pre-filled draft to [recipient].
 * If no email app is installed, shows a short message instead of
 * crashing or silently doing nothing.
 */
fun Context.contactSupport(
    recipient: String,
    subject: String
) {
    val body = buildString {
        appendLine("Hi Lumora team,")
        appendLine()
        appendLine()
        appendLine()
        appendLine("---")
        appendLine("App version: ${BuildConfig.VERSION_NAME}")
        appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})")
    }

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            this,
            "No email app found. You can reach us at $recipient",
            Toast.LENGTH_LONG
        ).show()
    }
}