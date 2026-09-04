package com.finance.lumora.presentation.ai.screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.presentation.ai.components.AurixErrorCard
import com.finance.lumora.presentation.ai.components.AurixHeader
import com.finance.lumora.presentation.ai.components.AurixInputSection
import com.finance.lumora.presentation.ai.components.AurixLoadingCard
import com.finance.lumora.presentation.ai.components.AurixResponseCard
import com.finance.lumora.presentation.ai.components.AurixWelcomeSection
import com.finance.lumora.presentation.ai.components.UserQuestionCard
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel
import kotlinx.coroutines.launch

@Composable
fun AurixScreen(
    viewModel: AurixViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var question by remember {
        mutableStateOf("")
    }

    val listState =
        rememberLazyListState()

    val coroutineScope =
        rememberCoroutineScope()

    val focusManager =
        LocalFocusManager.current

    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            coroutineScope.launch {

                listState.animateScrollToItem(
                    messages.lastIndex
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),

        /*
         * Disable Scaffold's default system-window inset
         * handling because the screen already handles IME
         * insets through imePadding().
         */
        contentWindowInsets =
            WindowInsets(0, 0, 0, 0),

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

            /*
             * Welcome section is visible only when there
             * is no active conversation.
             */
            if (
                messages.isEmpty() &&
                !isLoading
            ) {

                item {

                    AnimatedVisibility(
                        visible = messages.isEmpty(),

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

            /*
             * Every user and AURIX message is rendered
             * from the conversation state.
             */
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

            /*
             * Small bottom spacing so the last message
             * does not touch the input area.
             */
            item {

                Spacer(
                    modifier =
                        Modifier.size(8.dp)
                )
            }
        }
    }
}