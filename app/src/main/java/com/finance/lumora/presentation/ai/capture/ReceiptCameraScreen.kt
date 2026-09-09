package com.finance.lumora.presentation.ai.capture



import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun ReceiptCameraScreen(
    onImageCaptured: (Uri) -> Unit,
    onClose: () -> Unit,
    onError: (Exception) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var isFlashEnabled by remember { mutableStateOf(false) }
    var captureAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var isCapturing by remember { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasCameraPermission = granted
            if (!granted) {
                onError(SecurityException("Camera permission was denied."))
            }
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Black
    ) {
        if (!hasCameraPermission) {
            CameraPermissionDeniedContent(
                onRequestPermission = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                onClose = onClose
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                // Live Camera Stream
                ReceiptCamera(
                    lifecycleOwner = lifecycleOwner,
                    onImageCaptured = { imageUri ->
                        isCapturing = false
                        onImageCaptured(imageUri)
                    },
                    onError = { exception ->
                        isCapturing = false
                        onError(exception)
                    },
                    onCaptureReady = { capture ->
                        captureAction = capture
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Document Frame Overlay
                ReceiptDocumentOverlay(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 96.dp)
                )

                // Top Control Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Camera",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Scan Receipt",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(
                        onClick = { isFlashEnabled = !isFlashEnabled },
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFlashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Toggle Flash",
                            tint = if (isFlashEnabled) MaterialTheme.colorScheme.primary else Color.White
                        )
                    }
                }

                // Bottom Controls Container
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Position receipt within frame",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Shutter Trigger Button
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .border(4.dp, Color.White, CircleShape)
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                if (!isCapturing) {
                                    isCapturing = true
                                    captureAction?.invoke()
                                }
                            },
                            enabled = captureAction != null && !isCapturing,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (isCapturing) Color.LightGray else Color.White,
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Capture",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Custom Document Frame Overlay Visualizer
@Composable
private fun ReceiptDocumentOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            val cornerLength = 32.dp.toPx()
            val cornerRadius = 16.dp.toPx()

            // Draw bounding corner guides
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)

            drawRoundRect(
                color = Color.White.copy(alpha = 0.5f),
                size = size,
                cornerRadius = CornerRadius(cornerRadius),
                style = Stroke(width = 1.dp.toPx(), pathEffect = pathEffect)
            )

            // Top-Left Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(0f, cornerLength),
                end = Offset(0f, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(0f, 0f),
                end = Offset(cornerLength, 0f),
                strokeWidth = strokeWidth
            )

            // Top-Right Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(size.width - cornerLength, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, 0f),
                end = Offset(size.width, cornerLength),
                strokeWidth = strokeWidth
            )

            // Bottom-Left Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(0f, size.height - cornerLength),
                end = Offset(0f, size.height),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(0f, size.height),
                end = Offset(cornerLength, size.height),
                strokeWidth = strokeWidth
            )

            // Bottom-Right Corner Accent
            drawLine(
                color = Color.White,
                start = Offset(size.width - cornerLength, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, size.height - cornerLength),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }
    }
}

// Permission Denied View Fallback
@Composable
private fun CameraPermissionDeniedContent(
    onRequestPermission: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Camera Access Required",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "AURIX needs access to your camera to scan physical receipts and extract transaction details automatically.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onRequestPermission,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Grant Permission")
                }

                Spacer(modifier = Modifier.height(8.dp))

                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}