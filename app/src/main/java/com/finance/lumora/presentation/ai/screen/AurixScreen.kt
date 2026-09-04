package com.finance.lumora.presentation.ai.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.presentation.ai.components.AurixErrorCard
import com.finance.lumora.presentation.ai.components.AurixHeader
import com.finance.lumora.presentation.ai.components.AurixInputSection
import com.finance.lumora.presentation.ai.components.AurixLoadingCard
import com.finance.lumora.presentation.ai.components.AurixResponseCard
import com.finance.lumora.presentation.ai.components.AurixWelcomeSection
import com.finance.lumora.presentation.ai.components.UserQuestionCard
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel

@Composable
fun AurixScreen(
    viewModel: AurixViewModel = hiltViewModel()
) {

    val response by viewModel.response.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var question by remember {
        mutableStateOf("")
    }

    val listState =
        rememberLazyListState()

    LaunchedEffect(response) {
        if (response.isNotBlank()) {
            listState.animateScrollToItem(
                1
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
    ) {

        AurixHeader(
            onClear = {
                question = ""
                viewModel.clearResponse()
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                if (
                    response.isBlank() &&
                    error == null &&
                    !isLoading
                ) {
                    AurixWelcomeSection(
                        onQuestionSelected = {
                            question = it
                        }
                    )
                }
            }

            if (response.isNotBlank()) {

                item {
                    UserQuestionCard(
                        question = question
                    )
                }

                item {
                    AurixResponseCard(
                        response = response
                    )
                }
            }

            if (isLoading) {

                item {
                    AurixLoadingCard()
                }
            }

            error?.let { message ->

                item {
                    AurixErrorCard(
                        message = message,
                        onRetry = {
                            viewModel.askQuestion(
                                question
                            )
                        }
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
            }
        }

        AurixInputSection(
            question = question,
            onQuestionChanged = {
                question = it
            },
            onSend = {
                viewModel.askQuestion(
                    question
                )
            },
            isLoading = isLoading
        )
    }
}