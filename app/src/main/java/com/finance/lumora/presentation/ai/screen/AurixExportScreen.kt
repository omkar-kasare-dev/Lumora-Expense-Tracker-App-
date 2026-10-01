package com.finance.lumora.presentation.ai.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val AurixNavDark = Color(0xFF0F2640)
private val AurixAccentColor = Color(0xFF4FC3F7)

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
        containerColor = AurixNavDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Export",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AurixNavDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.IosShare,
                contentDescription = null,
                tint = AurixAccentColor,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "${messages.size} Messages Ready",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Exports session transcripts as a standard text file for secure sharing or archiving.",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = { exportConversation(context, messages) },
                enabled = messages.isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AurixAccentColor,
                    disabledContainerColor = AurixAccentColor.copy(alpha = 0.2f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Export & Share",
                    color = AurixNavDark,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}