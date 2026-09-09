package com.finance.lumora.presentation.ai.capture



import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.presentation.ai.model.VoiceInputState
import com.finance.lumora.presentation.ai.viewmodel.VoiceCaptureViewModel

@Composable
fun VoiceCaptureScreen(
    onDraftReady: (ResolvedTransactionDraft) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VoiceCaptureViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val resolvedDraft by viewModel.resolvedDraft.collectAsState()

    val microphonePermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                viewModel.startListening()
            }
        }

    fun startVoiceCapture() {
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            viewModel.startListening()
        } else {
            microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(resolvedDraft) {
        resolvedDraft?.let { draft ->
            onDraftReady(draft)
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Navigation Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AURIX Voice Assistant",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = {
                        viewModel.cancelListening()
                        onClose()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Animated State Container
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 4 }) togetherWith
                            (fadeOut() + slideOutVertically { -it / 4 })
                },
                label = "VoiceStateTransition",
                modifier = Modifier.fillMaxWidth()
            ) { currentState ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (currentState) {
                        VoiceInputState.Idle -> IdleVoiceContent(onStart = ::startVoiceCapture)

                        VoiceInputState.Listening -> ListeningVoiceContent(
                            onStop = { viewModel.stopListening() }
                        )

                        VoiceInputState.Processing -> ProcessingVoiceContent()

                        is VoiceInputState.Transcript -> TranscriptVoiceContent(
                            transcriptState = currentState,
                            onStop = { viewModel.stopListening() }
                        )

                        is VoiceInputState.Error -> ErrorVoiceContent(
                            message = currentState.message,
                            onRetry = ::startVoiceCapture
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1.5f))
        }
    }
}

// State Specific UI Views

@Composable
private fun IdleVoiceContent(onStart: () -> Unit) {
    Text(
        text = "Speak Your Expense",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Tap the mic and describe your transaction naturally.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(48.dp))

    IconButton(
        onClick = onStart,
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "Start Listening",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun ListeningVoiceContent(onStop: () -> Unit) {
    Text(
        text = "Listening...",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Try: \"Paid 150 rupees for lunch today\"",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(40.dp))
    AudioWaveVisualizer()
    Spacer(modifier = Modifier.height(40.dp))

    PulsingMicButton(
        onClick = onStop,
        icon = Icons.Default.Stop,
        contentDescription = "Stop Listening",
        containerColor = MaterialTheme.colorScheme.error
    )
}

@Composable
private fun ProcessingVoiceContent() {
    CircularProgressIndicator(
        modifier = Modifier.size(56.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 4.dp
    )
    Spacer(modifier = Modifier.height(28.dp))
    Text(
        text = "Understanding Transaction...",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Extracting amount, category, and payment details.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TranscriptVoiceContent(
    transcriptState: VoiceInputState.Transcript,
    onStop: () -> Unit
) {
    Text(
        text = if (transcriptState.isFinal) "Processing Transcript..." else "Listening...",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(20.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Text(
            text = transcriptState.text.ifBlank { "..." },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )
    }

    if (!transcriptState.isFinal) {
        Spacer(modifier = Modifier.height(36.dp))
        FilledTonalButton(onClick = onStop) {
            Icon(
                imageVector = Icons.Default.Stop,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Done")
        }
    }
}

@Composable
private fun ErrorVoiceContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(MaterialTheme.colorScheme.errorContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.MicOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.size(32.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Didn't Catch That",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedButton(onClick = onRetry) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Try Again")
    }
}

// Visual Effects & Animated Components

@Composable
private fun PulsingMicButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    containerColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(scale)
                .background(containerColor.copy(alpha = 0.2f), CircleShape)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(containerColor)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun AudioWaveVisualizer() {
    val infiniteTransition = rememberInfiniteTransition(label = "WaveTransition")
    val heights = List(5) { index ->
        infiniteTransition.animateFloat(
            initialValue = 12f,
            targetValue = 40f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400 + (index * 150),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "WaveHeight$index"
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(48.dp)
    ) {
        heights.forEach { heightState ->
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(heightState.value.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }
    }
}