package com.finance.lumora.presentation.ai.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.presentation.ai.theme.AurixTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun exportConversation(context: Context, messages: List<ChatMessage>) {
    val text = buildString {
        appendLine("Aurix Conversation Export")
        appendLine("Exported: ${SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())}")
        appendLine()
        messages.forEach { message ->
            val speaker = if (message.role == ChatMessageRole.USER) "You" else "Aurix"
            appendLine("$speaker: ${message.content}")
            appendLine()
        }
    }

    val file = File(context.cacheDir, "aurix_conversation_${System.currentTimeMillis()}.txt")
    file.writeText(text)

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Share conversation"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurixExportScreen(
    messages: List<ChatMessage>,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = AurixTheme.NavDark,
        topBar = {
            TopAppBar(
                title = { Text("Export Conversation", style = AurixTheme.Typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AurixTheme.TextPrimary,
                            modifier = Modifier.size(AurixTheme.IconSizeMedium)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AurixTheme.NavDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AurixTheme.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Download,
                contentDescription = null,
                tint = AurixTheme.AccentColor,
                modifier = Modifier.size(AurixTheme.IconSizeLarge)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "${messages.size} messages in this session",
                style = AurixTheme.Typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Exports as a plain text file you can share or save.",
                style = AurixTheme.Typography.bodySmall
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { exportConversation(context, messages) },
                enabled = messages.isNotEmpty(),
                shape = AurixTheme.SmallContainerShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AurixTheme.AccentColor,
                    contentColor = AurixTheme.NavDark
                ),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text("Export & Share", style = AurixTheme.Typography.titleMedium.copy(color = AurixTheme.NavDark))
            }
        }
    }
}