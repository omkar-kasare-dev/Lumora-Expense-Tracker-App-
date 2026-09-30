package com.finance.lumora.presentation.ai.capture


import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import java.io.File

/**
 * Pure camera engine: preview feed + capture logic only.
 *
 * Deliberately has no UI chrome of its own (no flash button, no
 * status badges, no viewfinder overlay, no capture button) - all
 * of that previously duplicated what ReceiptCameraScreen, the
 * screen that wraps this composable, already provides. Rendering
 * both caused two overlapping flash buttons (only one of which was
 * actually wired to ImageCapture) and two overlapping capture
 * buttons. flashMode is now a parameter controlled by the caller,
 * not internal state.
 */
@Composable
fun ReceiptCamera(
    lifecycleOwner: LifecycleOwner,
    flashMode: Int,
    onImageCaptured: (Uri) -> Unit,
    onError: (Exception) -> Unit,
    onCaptureReady: (capture: () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setFlashMode(flashMode)
            .build()
    }

    // Reacts to the caller changing flashMode - this is now the
    // single place flash actually gets applied to the camera.
    LaunchedEffect(flashMode) {
        imageCapture.flashMode = flashMode
    }

    val triggerCapture = remember {
        {
            val outputFile = File(
                context.cacheDir,
                "receipt_${System.currentTimeMillis()}.jpg"
            )

            val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.d("ReceiptCamera", "onImageSaved() called")
                        Log.d("ReceiptCamera", "savedUri = ${outputFileResults.savedUri}")
                        Log.d("ReceiptCamera", "file exists = ${outputFile.exists()}")
                        Log.d("ReceiptCamera", "file size = ${outputFile.length()} bytes")

                        val uri = outputFileResults.savedUri
                            ?: FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.fileprovider",
                                outputFile
                            )

                        Log.d("ReceiptCamera", "final uri = $uri")
                        onImageCaptured(uri)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("ReceiptCamera", "Image capture failed", exception)
                        onError(exception)
                    }
                }
            )
        }
    }

    LaunchedEffect(imageCapture) {
        onCaptureReady { triggerCapture() }
    }

    LaunchedEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )

            } catch (exception: Exception) {
                onError(exception)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    Box(modifier = modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}
/*
class ReceiptCameraController(
    private val imageCapture: ImageCapture,
    private val context: android.content.Context,
    private val onImageCaptured: (Uri) -> Unit,
    private val onError: (Exception) -> Unit
) {

    fun capture() {

        val outputFile = File(
            context.cacheDir,
            "receipt_${System.currentTimeMillis()}.jpg"
        )

        val outputOptions =
            ImageCapture.OutputFileOptions.Builder(
                outputFile
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(
                    outputFileResults:
                    ImageCapture.OutputFileResults
                ) {

                    val uri = outputFileResults.savedUri
                        ?: FileProvider
                            .getUriForFile(
                                context,
                                "${context.packageName}.fileprovider",
                                outputFile
                            )

                    onImageCaptured(uri)
                }

                override fun onError(
                    exception: ImageCaptureException
                ) {
                    onError(exception)
                }
            }
        )
    }
}

 */