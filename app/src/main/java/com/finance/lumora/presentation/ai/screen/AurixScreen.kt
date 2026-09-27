package com.finance.lumora.presentation.ai.screen
/*
// Main:
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.presentation.ai.capture.ReceiptCameraScreen
import com.finance.lumora.presentation.ai.capture.ReceiptOcrState
import com.finance.lumora.presentation.ai.capture.TransactionConfirmationHost
import com.finance.lumora.presentation.ai.capture.TransactionEditBottomSheet
import com.finance.lumora.presentation.ai.capture.VoiceCaptureScreen
import com.finance.lumora.presentation.ai.components.AurixErrorCard
import com.finance.lumora.presentation.ai.components.AurixHeader
import com.finance.lumora.presentation.ai.components.AurixInputSection
import com.finance.lumora.presentation.ai.components.AurixLoadingCard
import com.finance.lumora.presentation.ai.components.AurixResponseCard
import com.finance.lumora.presentation.ai.components.AurixWelcomeSection
import com.finance.lumora.presentation.ai.components.UserQuestionCard
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel
import com.finance.lumora.presentation.ai.viewmodel.ReceiptCaptureViewModel

import com.finance.lumora.presentation.ai.viewmodel.ReceiptOcrViewModel
import com.finance.lumora.presentation.ai.viewmodel.TransactionEditViewModel
import com.finance.lumora.presentation.ai.viewmodel.VoiceCaptureViewModel
import kotlinx.coroutines.launch

@Composable
fun AurixScreen(
    viewModel: AurixViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showTransactionEditor by remember {
        mutableStateOf(false)
    }

    var showTransactionSavedMessage by remember {
        mutableStateOf(false)
    }

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    val receiptOcrViewModel: ReceiptOcrViewModel =
        hiltViewModel()

    val receiptCaptureViewModel: ReceiptCaptureViewModel =
        hiltViewModel()

    val voiceCaptureViewModel: VoiceCaptureViewModel =
        hiltViewModel()
    val transactionEditViewModel:
            TransactionEditViewModel =
        hiltViewModel()

    val receiptOcrState by
    receiptOcrViewModel.state.collectAsState()

    val receiptResolvedDraft by
    receiptCaptureViewModel.resolvedDraft.collectAsState()

    val voiceResolvedDraft by
    voiceCaptureViewModel.resolvedDraft.collectAsState()

    val categories by
    transactionEditViewModel.categories.collectAsState()

    var question by remember {
        mutableStateOf("")
    }

    var showReceiptCamera by remember {
        mutableStateOf(false)
    }

    var showVoiceCapture by remember {
        mutableStateOf(false)
    }

    var resolvedDraft by remember {
        mutableStateOf<ResolvedTransactionDraft?>(null)
    }

    val listState =
        rememberLazyListState()

    val coroutineScope =
        rememberCoroutineScope()

    val focusManager =
        LocalFocusManager.current

    /*
     * Automatically scroll to the latest AURIX
     * conversation message.
     */
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(
                    messages.lastIndex
                )
            }
        }
    }

    /*
     * OCR pipeline:
     *
     * Camera
     *   ↓
     * ReceiptOcrViewModel
     *   ↓
     * OCR result
     *   ↓
     * ReceiptCaptureViewModel
     *   ↓
     * Gemini
     *   ↓
     * ResolvedTransactionDraft
     */
    LaunchedEffect(receiptOcrState) {

        when (val state = receiptOcrState) {

            is ReceiptOcrState.Success -> {

                receiptCaptureViewModel.processOcrResult(
                    state.result
                )

                receiptOcrViewModel.reset()
            }

            else -> Unit
        }
    }

    /*
     * Receipt AI parsing completed.
     */
    LaunchedEffect(receiptResolvedDraft) {

        receiptResolvedDraft?.let { draft ->

            resolvedDraft = draft
            showReceiptCamera = false
        }
    }

    /*
     * Voice AI parsing completed.
     */
    LaunchedEffect(voiceResolvedDraft) {

        voiceResolvedDraft?.let { draft ->

            resolvedDraft = draft
            showVoiceCapture = false
        }
    }

    LaunchedEffect(
        showTransactionSavedMessage
    ) {
        if (showTransactionSavedMessage) {

            snackbarHostState.showSnackbar(
                message = "Transaction saved successfully."
            )

            showTransactionSavedMessage = false
        }
    }

    /*
     * Main AURIX screen.
     */
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),

        contentWindowInsets =
            WindowInsets(0, 0, 0, 0),

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },

        topBar = {

            AurixHeader(
                onClear = {

                    question = ""

                    viewModel.clearConversation()
                }
            )
        },

        bottomBar = {

            AurixInputSection(

                question = question,

                onQuestionChanged = {
                    question = it
                },

                onSend = {

                    if (question.isNotBlank()) {

                        val currentQuery =
                            question

                        question = ""

                        viewModel.askQuestion(
                            currentQuery
                        )

                        focusManager.clearFocus()
                    }
                },

                onCameraClick = {

                    focusManager.clearFocus()

                    showReceiptCamera = true
                },

                onVoiceClick = {

                    focusManager.clearFocus()

                    showVoiceCapture = true
                },

                isLoading = isLoading
            )
        }

    ) { innerPadding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),

            state = listState,

            contentPadding = PaddingValues(

                top =
                    innerPadding.calculateTopPadding() +
                            12.dp,

                bottom =
                    innerPadding.calculateBottomPadding() +
                            12.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            if (
                messages.isEmpty() &&
                !isLoading
            ) {

                item {

                    AnimatedVisibility(

                        visible =
                            messages.isEmpty(),

                        enter =
                            fadeIn() +
                                    slideInVertically(),

                        exit =
                            fadeOut() +
                                    slideOutVertically()
                    ) {

                        AurixWelcomeSection(

                            onQuestionSelected = {
                                    selectedQuestion ->

                                question =
                                    selectedQuestion

                                viewModel.askQuestion(
                                    selectedQuestion
                                )

                                question = ""
                            }
                        )
                    }
                }
            }

            items(

                items = messages,

                key = { message ->
                    message.id
                }

            ) { message ->

                when (message.role) {

                    ChatMessageRole.USER -> {

                        UserQuestionCard(
                            question =
                                message.content
                        )
                    }

                    ChatMessageRole.AURIX -> {

                        when (message.status) {

                            ChatMessageStatus.SENT -> {

                                AurixResponseCard(
                                    response =
                                        message.content
                                )
                            }

                            ChatMessageStatus.LOADING -> {

                                AurixLoadingCard()
                            }

                            ChatMessageStatus.ERROR -> {

                                AurixErrorCard(
                                    message =
                                        message.content,

                                    onRetry = {
                                        viewModel.retryLastQuestion()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {

                Spacer(
                    modifier =
                        Modifier.size(8.dp)
                )
            }
        }
    }

    /*
     * Receipt camera.
     *
     * The camera only captures the image.
     * OCR is handled by ReceiptOcrViewModel.
     */
    if (showReceiptCamera) {

        ReceiptCameraScreen(

            onImageCaptured = { imageUri ->

                showReceiptCamera = false

                receiptOcrViewModel.processReceipt(
                    imageUri
                )
            },

            onClose = {

                showReceiptCamera = false
            },

            onError = {

                showReceiptCamera = false
            }
        )
    }

    /*
     * Voice capture.
     */
    if (showVoiceCapture) {

        VoiceCaptureScreen(

            onDraftReady = { draft ->

                showVoiceCapture = false

                resolvedDraft = draft
            },
            onFinancialQuery = { query ->
                showVoiceCapture = false
                viewModel.askQuestion(query)
            },

            onClose = {

                showVoiceCapture = false
            }
        )
    }

    /*
     * Common transaction confirmation.
     *
     * Both OCR and voice eventually arrive here.
     */
    resolvedDraft?.let { draft ->

        if (showTransactionEditor) {

            TransactionEditBottomSheet(

                resolvedDraft = draft,

                categories = categories,

                onApplyEdit = { updatedDraft ->

                    resolvedDraft =
                        updatedDraft

                    showTransactionEditor =
                        false
                },

                onDismiss = {

                    showTransactionEditor =
                        false
                }
            )

        } else {

            TransactionConfirmationHost(

                resolvedDraft = draft,

                onDismiss = {

                    resolvedDraft = null

                    receiptCaptureViewModel.reset()

                    voiceCaptureViewModel.reset()
                },

                onSaved = {

                    resolvedDraft = null

                    receiptCaptureViewModel.reset()

                    voiceCaptureViewModel.reset()
                    showTransactionSavedMessage = true
                },

                onEdit = {

                    showTransactionEditor =
                        true
                }
            )
        }
    }
}

 */

/*
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.presentation.ai.capture.ReceiptCameraScreen
import com.finance.lumora.presentation.ai.capture.ReceiptOcrState
import com.finance.lumora.presentation.ai.capture.TransactionConfirmationHost
import com.finance.lumora.presentation.ai.capture.TransactionEditBottomSheet
import com.finance.lumora.presentation.ai.components.AurixErrorCard
import com.finance.lumora.presentation.ai.components.AurixInputSection
import com.finance.lumora.presentation.ai.components.AurixLoadingCard
import com.finance.lumora.presentation.ai.components.AurixResponseCard
import com.finance.lumora.presentation.ai.components.AurixWelcomeSection
import com.finance.lumora.presentation.ai.components.UserQuestionCard
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel
import com.finance.lumora.presentation.ai.viewmodel.ReceiptCaptureViewModel
import com.finance.lumora.presentation.ai.viewmodel.ReceiptOcrViewModel
import com.finance.lumora.presentation.ai.viewmodel.TransactionEditViewModel
import com.finance.lumora.presentation.ai.viewmodel.VoiceCaptureViewModel
import kotlinx.coroutines.launch

private val DarkBlueBg = Color(0xFF133253)

@Composable
fun AurixScreen(
    viewModel: AurixViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showTransactionEditor by remember { mutableStateOf(false) }
    var showTransactionSavedMessage by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val receiptOcrViewModel: ReceiptOcrViewModel = hiltViewModel()
    val receiptCaptureViewModel: ReceiptCaptureViewModel = hiltViewModel()
    val voiceCaptureViewModel: VoiceCaptureViewModel = hiltViewModel()
    val transactionEditViewModel: TransactionEditViewModel = hiltViewModel()

    val receiptOcrState by receiptOcrViewModel.state.collectAsState()
    val receiptResolvedDraft by receiptCaptureViewModel.resolvedDraft.collectAsState()
    val voiceResolvedDraft by voiceCaptureViewModel.resolvedDraft.collectAsState()
    val voiceState by voiceCaptureViewModel.state.collectAsState()
    val financialQuery by voiceCaptureViewModel.financialQuery.collectAsState()
    val categories by transactionEditViewModel.categories.collectAsState()

    var question by remember { mutableStateOf("") }
    var showReceiptCamera by remember { mutableStateOf(false) }
    var resolvedDraft by remember { mutableStateOf<ResolvedTransactionDraft?>(null) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // ---------------------------------------------------------
    // Microphone permission - moved here since VoiceCaptureScreen
    // is no longer used; a single mic tap now goes straight to
    // startListening() with no intermediate screen or second tap.
    // ---------------------------------------------------------
    val microphonePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            voiceCaptureViewModel.startListening()
        }
    }

    fun startVoiceCapture() {
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            voiceCaptureViewModel.startListening()
        } else {
            microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.lastIndex)
            }
        }
    }

    LaunchedEffect(receiptOcrState) {
        when (val state = receiptOcrState) {
            is ReceiptOcrState.Success -> {
                receiptCaptureViewModel.processOcrResult(state.result)
                receiptOcrViewModel.reset()
            }
            else -> Unit
        }
    }

    LaunchedEffect(receiptResolvedDraft) {
        receiptResolvedDraft?.let { draft ->
            resolvedDraft = draft
            showReceiptCamera = false
        }
    }

    // Voice draft ready: hand it to the edit/confirm sheet, and
    // bring the mic input bar back to its normal idle state.
    LaunchedEffect(voiceResolvedDraft) {
        voiceResolvedDraft?.let { draft ->
            resolvedDraft = draft
            voiceCaptureViewModel.reset()
        }
    }

    // Voice detected as a financial question rather than a
    // transaction: forward it to the normal chat flow.
    LaunchedEffect(financialQuery) {
        financialQuery?.let { query ->
            viewModel.askQuestion(query)
            voiceCaptureViewModel.reset()
        }
    }

    LaunchedEffect(showTransactionSavedMessage) {
        if (showTransactionSavedMessage) {
            snackbarHostState.showSnackbar("Transaction saved successfully.")
            showTransactionSavedMessage = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueBg)
    ) {
        AurixWaveBackground()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

            bottomBar = {
                AurixInputSection(
                    question = question,
                    onQuestionChanged = { question = it },
                    onSend = {
                        if (question.isNotBlank()) {
                            val currentQuery = question
                            question = ""
                            viewModel.askQuestion(currentQuery)
                            focusManager.clearFocus()
                        }
                    },

                    onCameraClick = {
                        focusManager.clearFocus()
                        showReceiptCamera = true
                    },
                    onStartVoice = {
                        focusManager.clearFocus()
                        startVoiceCapture()
                    },
                    onStopVoice = {
                        voiceCaptureViewModel.stopListening()
                    },
                    onCancelVoice = {
                        voiceCaptureViewModel.cancelListening()
                    },
                    onRetryVoice = {
                        startVoiceCapture()
                    },
                    onDismissTextInput = {
                        // Keep existing text.
                        // Only close the text-input mode.
                    },
                    voiceState = voiceState,
                    isLoading = isLoading
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                state = listState,
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 60.dp,
                    bottom = innerPadding.calculateBottomPadding() + 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (messages.isEmpty() && !isLoading) {
                    item {
                        AnimatedVisibility(
                            visible = messages.isEmpty(),
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            AurixWelcomeSection(
                                onQuestionSelected = { selectedQuestion ->
                                    question = selectedQuestion
                                    viewModel.askQuestion(selectedQuestion)
                                    question = ""
                                }
                            )
                        }
                    }
                }

                items(
                    items = messages,
                    key = { message -> message.id }
                ) { message ->
                    when (message.role) {
                        ChatMessageRole.USER -> {
                            UserQuestionCard(question = message.content)
                        }
                        ChatMessageRole.AURIX -> {
                            when (message.status) {
                                ChatMessageStatus.SENT -> {
                                    AurixResponseCard(response = message.content)
                                }
                                ChatMessageStatus.LOADING -> {
                                    AurixLoadingCard()
                                }
                                ChatMessageStatus.ERROR -> {
                                    AurixErrorCard(
                                        message = message.content,
                                        onRetry = { viewModel.retryLastQuestion() }
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

    if (showReceiptCamera) {
        ReceiptCameraScreen(
            onImageCaptured = { imageUri ->
                showReceiptCamera = false
                receiptOcrViewModel.processReceipt(imageUri)
            },
            onClose = { showReceiptCamera = false },
            onError = { showReceiptCamera = false }
        )
    }

    resolvedDraft?.let { draft ->
        if (showTransactionEditor) {
            TransactionEditBottomSheet(
                resolvedDraft = draft,
                categories = categories,
                onApplyEdit = { updatedDraft ->
                    resolvedDraft = updatedDraft
                    showTransactionEditor = false
                },
                onDismiss = { showTransactionEditor = false }
            )
        } else {
            TransactionConfirmationHost(
                resolvedDraft = draft,
                onDismiss = {
                    resolvedDraft = null
                    receiptCaptureViewModel.reset()
                    voiceCaptureViewModel.reset()
                },
                onSaved = {
                    resolvedDraft = null
                    receiptCaptureViewModel.reset()
                    voiceCaptureViewModel.reset()
                    showTransactionSavedMessage = true
                },
                onEdit = { showTransactionEditor = true }
            )
        }
    }
}

@Composable
private fun AurixWaveBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height * 0.38f
        val lineCount = 8
        val baseRadius = size.width * 0.25f

        for (i in 0 until lineCount) {
            val radius = baseRadius + (i * 45.dp.toPx())
            val alpha = (1f - (i.toFloat() / lineCount)) * 0.18f

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = radius,
                center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}

 */



import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.presentation.ai.capture.ReceiptCameraScreen
import com.finance.lumora.presentation.ai.capture.ReceiptOcrState
import com.finance.lumora.presentation.ai.capture.TransactionConfirmationHost
import com.finance.lumora.presentation.ai.capture.TransactionEditBottomSheet
import com.finance.lumora.presentation.ai.components.AurixErrorCard
import com.finance.lumora.presentation.ai.components.AurixInputSection
import com.finance.lumora.presentation.ai.components.AurixLoadingCard
import com.finance.lumora.presentation.ai.components.AurixResponseCard
import com.finance.lumora.presentation.ai.components.AurixTopAppBar
import com.finance.lumora.presentation.ai.components.AurixWaveBackground

import com.finance.lumora.presentation.ai.components.AurixWelcomeSection
import com.finance.lumora.presentation.ai.components.UserQuestionCard
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel
import com.finance.lumora.presentation.ai.viewmodel.ReceiptCaptureViewModel
import com.finance.lumora.presentation.ai.viewmodel.ReceiptOcrViewModel
import com.finance.lumora.presentation.ai.viewmodel.TransactionEditViewModel
import com.finance.lumora.presentation.ai.viewmodel.VoiceCaptureViewModel
import kotlinx.coroutines.launch

private val AurixBackground = Color(0xFF133253)

@Composable
fun AurixScreen(
    viewModel: AurixViewModel = hiltViewModel(),
) {
    // ---------------------------------------------------------
    // AURIX chat state
    // ---------------------------------------------------------

    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // ---------------------------------------------------------
    // Transaction / capture state
    // ---------------------------------------------------------

    var showTransactionEditor by remember {
        mutableStateOf(false)
    }

    var showTransactionSavedMessage by remember {
        mutableStateOf(false)
    }

    var question by remember {
        mutableStateOf("")
    }

    var showReceiptCamera by remember {
        mutableStateOf(false)
    }

    var resolvedDraft by remember {
        mutableStateOf<ResolvedTransactionDraft?>(null)
    }

    // ---------------------------------------------------------
    // ViewModels
    // ---------------------------------------------------------

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val receiptOcrViewModel: ReceiptOcrViewModel = hiltViewModel()
    val receiptCaptureViewModel: ReceiptCaptureViewModel = hiltViewModel()
    val voiceCaptureViewModel: VoiceCaptureViewModel = hiltViewModel()
    val transactionEditViewModel: TransactionEditViewModel = hiltViewModel()

    // ---------------------------------------------------------
    // State collection
    // ---------------------------------------------------------

    val receiptOcrState by receiptOcrViewModel.state.collectAsState()

    val receiptResolvedDraft by
    receiptCaptureViewModel.resolvedDraft.collectAsState()

    val voiceResolvedDraft by
    voiceCaptureViewModel.resolvedDraft.collectAsState()

    val voiceState by
    voiceCaptureViewModel.state.collectAsState()

    val financialQuery by
    voiceCaptureViewModel.financialQuery.collectAsState()

    val categories by
    transactionEditViewModel.categories.collectAsState()

    // ---------------------------------------------------------
    // Compose helpers
    // ---------------------------------------------------------

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // ---------------------------------------------------------
    // Microphone permission
    //
    // One tap on the AURIX mic directly starts voice capture.
    // No separate VoiceCaptureScreen is required.
    // ---------------------------------------------------------

    val microphonePermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                voiceCaptureViewModel.startListening()
            }
        }

    fun startVoiceCapture() {
        val permissionGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            voiceCaptureViewModel.startListening()
        } else {
            microphonePermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    // ---------------------------------------------------------
    // Auto-scroll when a new message appears
    // ---------------------------------------------------------

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(
                    index = messages.lastIndex
                )
            }
        }
    }

    // ---------------------------------------------------------
    // Receipt OCR result
    // ---------------------------------------------------------

    LaunchedEffect(receiptOcrState) {
        when (val state = receiptOcrState) {

            is ReceiptOcrState.Success -> {
                receiptCaptureViewModel.processOcrResult(
                    state.result
                )

                receiptOcrViewModel.reset()
            }

            else -> Unit
        }
    }

    // ---------------------------------------------------------
    // Receipt transaction draft
    // ---------------------------------------------------------

    LaunchedEffect(receiptResolvedDraft) {

        receiptResolvedDraft?.let { draft ->

            resolvedDraft = draft
            showReceiptCamera = false
        }
    }

    // ---------------------------------------------------------
    // Voice transaction draft
    //
    // Once the voice transaction is resolved, open the same
    // transaction confirmation flow used by OCR.
    // ---------------------------------------------------------

    LaunchedEffect(voiceResolvedDraft) {

        voiceResolvedDraft?.let { draft ->

            resolvedDraft = draft

            voiceCaptureViewModel.reset()
        }
    }

    // ---------------------------------------------------------
    // Voice financial question
    //
    // Example:
    // "How much did I spend this month?"
    // ---------------------------------------------------------

    LaunchedEffect(financialQuery) {

        financialQuery?.let { query ->

            viewModel.askQuestion(query)

            voiceCaptureViewModel.reset()
        }
    }

    // ---------------------------------------------------------
    // Transaction saved Snackbar
    // ---------------------------------------------------------

    LaunchedEffect(showTransactionSavedMessage) {

        if (showTransactionSavedMessage) {

            snackbarHostState.showSnackbar(
                message = "Transaction saved successfully."
            )

            showTransactionSavedMessage = false
        }
    }

    // ---------------------------------------------------------
    // Main AURIX UI
    // ---------------------------------------------------------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AurixBackground)
    ) {

        // Decorative AURIX background
        AurixWaveBackground()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),

            containerColor = Color.Transparent,

            contentWindowInsets = WindowInsets(
                left = 0,
                top = 0,
                right = 0,
                bottom = 0
            ),

            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
            topBar = {
                AurixTopAppBar(
                    onBackClick = {
                        //navController.popBackStack()
                                  },
                    onClearChatClick = { viewModel.clearConversation() },
                    onMoreOptionsClick = { /* Open settings/menu */ }
                )
            },


            // -------------------------------------------------
            // Bottom input section
            // -------------------------------------------------

            bottomBar = {

                AurixInputSection(
                    question = question,

                    onQuestionChanged = {
                        question = it
                    },

                    onSend = {

                        if (question.isNotBlank()) {

                            val currentQuery = question

                            question = ""

                            viewModel.askQuestion(
                                currentQuery
                            )

                            focusManager.clearFocus()
                        }
                    },

                    onCameraClick = {

                        focusManager.clearFocus()

                        showReceiptCamera = true
                    },

                    onStartVoice = {

                        focusManager.clearFocus()

                        startVoiceCapture()
                    },

                    onStopVoice = {

                        voiceCaptureViewModel.stopListening()
                    },

                    onCancelVoice = {

                        voiceCaptureViewModel.cancelListening()
                    },

                    onRetryVoice = {

                        startVoiceCapture()
                    },

                    onDismissTextInput = {
                        // The text-input visibility is controlled
                        // internally by AurixInputSection.
                        //
                        // We intentionally do not clear `question`
                        // here so the user's typed text is preserved.
                    },

                    voiceState = voiceState,

                    isLoading = isLoading
                )
            }
        ) { innerPadding ->

            // -------------------------------------------------
            // Chat content
            // -------------------------------------------------

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),

                state = listState,

                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 44.dp,
                    bottom = innerPadding.calculateBottomPadding() + 12.dp
                ),

                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // -------------------------------------------------
                // Welcome state
                // -------------------------------------------------

                if (messages.isEmpty() && !isLoading) {

                    item {

                        AnimatedVisibility(
                            visible = messages.isEmpty(),
                            enter = fadeIn() +
                                    slideInVertically(
                                        initialOffsetY = {
                                            it / 5
                                        }
                                    ),
                            exit = fadeOut() +
                                    slideOutVertically()
                        ) {

                            AurixWelcomeSection(
                                onQuestionSelected = { selectedQuestion ->

                                    question = selectedQuestion

                                    viewModel.askQuestion(
                                        selectedQuestion
                                    )

                                    question = ""
                                }
                            )
                        }
                    }
                }

                // -------------------------------------------------
                // Conversation messages
                // -------------------------------------------------

                items(
                    items = messages,
                    key = { message ->
                        message.id
                    }
                ) { message ->

                    when (message.role) {

                        ChatMessageRole.USER -> {

                            UserQuestionCard(
                                question = message.content
                            )
                        }

                        ChatMessageRole.AURIX -> {

                            when (message.status) {

                                ChatMessageStatus.SENT -> {

                                    AurixResponseCard(
                                        response = message.content
                                    )
                                }

                                ChatMessageStatus.LOADING -> {

                                    AurixLoadingCard()
                                }

                                ChatMessageStatus.ERROR -> {

                                    AurixErrorCard(
                                        message = message.content,

                                        onRetry = {
                                            viewModel.retryLastQuestion()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Receipt camera
    // ---------------------------------------------------------

    if (showReceiptCamera) {

        ReceiptCameraScreen(

            onImageCaptured = { imageUri ->

                showReceiptCamera = false

                receiptOcrViewModel.processReceipt(
                    imageUri
                )
            },

            onClose = {

                showReceiptCamera = false
            },

            onError = {

                showReceiptCamera = false
            }
        )
    }

    // ---------------------------------------------------------
    // Transaction confirmation / editing
    // ---------------------------------------------------------

    resolvedDraft?.let { draft ->

        if (showTransactionEditor) {

            TransactionEditBottomSheet(

                resolvedDraft = draft,

                categories = categories,

                onApplyEdit = { updatedDraft ->

                    resolvedDraft = updatedDraft

                    showTransactionEditor = false
                },

                onDismiss = {

                    showTransactionEditor = false
                }
            )

        } else {

            TransactionConfirmationHost(

                resolvedDraft = draft,

                onDismiss = {

                    resolvedDraft = null

                    receiptCaptureViewModel.reset()
                    voiceCaptureViewModel.reset()
                },

                onSaved = {

                    resolvedDraft = null

                    receiptCaptureViewModel.reset()
                    voiceCaptureViewModel.reset()

                    showTransactionSavedMessage = true
                },

                onEdit = {

                    showTransactionEditor = true
                }
            )
        }
    }
}

