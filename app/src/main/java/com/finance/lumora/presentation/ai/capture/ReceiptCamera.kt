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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File

@Composable
fun ReceiptCamera(
    lifecycleOwner: LifecycleOwner,
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
            .setCaptureMode(
                ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
            )
            .build()
    }

    LaunchedEffect(imageCapture) {

        onCaptureReady {

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

                        Log.d(
                            "ReceiptCamera",
                            "onImageSaved() called"
                        )

                        Log.d(
                            "ReceiptCamera",
                            "savedUri = ${outputFileResults.savedUri}"
                        )

                        Log.d(
                            "ReceiptCamera",
                            "file exists = ${outputFile.exists()}"
                        )

                        Log.d(
                            "ReceiptCamera",
                            "file size = ${outputFile.length()} bytes"
                        )

                        val uri =
                            outputFileResults.savedUri
                                ?: androidx.core.content.FileProvider
                                    .getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        outputFile
                                    )

                        Log.d(
                            "ReceiptCamera",
                            "final uri = $uri"
                        )
                        onImageCaptured(uri)
                    }

                    override fun onError(
                        exception: ImageCaptureException
                    ) {
                        Log.e(
                            "ReceiptCamera",
                            "Image capture failed",
                            exception
                        )
                        onError(exception)
                    }
                }
            )
        }
    }

    LaunchedEffect(lifecycleOwner) {

        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({

            try {
                val cameraProvider =
                    cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.surfaceProvider =
                            previewView.surfaceProvider
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

    AndroidView(
        factory = {
            previewView
        },
        modifier = modifier
    )
}

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
                        ?: androidx.core.content.FileProvider
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