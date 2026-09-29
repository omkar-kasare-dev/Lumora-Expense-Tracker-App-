package com.finance.lumora.presentation.ai.screen


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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
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
import androidx.tv.material3.rememberDrawerState
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.presentation.ai.capture.ReceiptCameraScreen
import com.finance.lumora.presentation.ai.capture.ReceiptOcrState
import com.finance.lumora.presentation.ai.capture.TransactionConfirmationHost
import com.finance.lumora.presentation.ai.capture.TransactionEditBottomSheet
import com.finance.lumora.presentation.ai.components.AurixDrawerContent
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
    onBackClick: () -> Unit,
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


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

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen, // Enable drag gesture when open
        drawerContent = {
            AurixDrawerContent(
                onNewChatClick = {
                    coroutineScope.launch { drawerState.close() }
                    viewModel.clearConversation()
                },
                onHistoryClick = {
                    coroutineScope.launch { drawerState.close() }
                },
                onExportChatClick = {
                    coroutineScope.launch { drawerState.close() }
                },
                onSettingsClick = {
                    coroutineScope.launch { drawerState.close() }
                },
                onHelpClick = {
                    coroutineScope.launch { drawerState.close() }
                },
                onAboutClick = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {



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
                    onBackClick = onBackClick,
                    onClearChatClick = { viewModel.clearConversation() },
                    onMoreOptionsClick = {
                        coroutineScope.launch {
                        drawerState.open()
                    }
                    }
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

