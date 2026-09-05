package com.finance.lumora.presentation.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.ChatMessageStatus
import com.finance.lumora.domain.usecase.ai.AskAurixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.finance.lumora.domain.model.ai.AurixException

@HiltViewModel
class AurixViewModel @Inject constructor(
    private val askAurixUseCase: AskAurixUseCase
) : ViewModel() {

    private val _messages =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages: StateFlow<List<ChatMessage>> =
        _messages.asStateFlow()

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private var currentRequestJob: Job? = null

    /**
     * Sends a new user question to AURIX.
     *
     * The current question is added to the conversation first.
     * AURIX then receives the previous conversation history
     * separately from the current question.
     */
    fun askQuestion(question: String) {

        val trimmedQuestion = question.trim()

        if (
            trimmedQuestion.isBlank() ||
            _isLoading.value
        ) {
            return
        }

        val previousMessages = _messages.value

        val userMessage =
            ChatMessage(
                id = generateMessageId(),
                role = ChatMessageRole.USER,
                content = trimmedQuestion,
                status = ChatMessageStatus.SENT
            )

        val aurixMessageId = generateMessageId()

        val loadingMessage =
            ChatMessage(
                id = aurixMessageId,
                role = ChatMessageRole.AURIX,
                content = "",
                status = ChatMessageStatus.LOADING
            )

        _messages.value =
            previousMessages +
                    userMessage +
                    loadingMessage

        _isLoading.value = true

        currentRequestJob?.cancel()

        currentRequestJob =
            viewModelScope.launch {

                try {
                    val conversationHistory =
                        previousMessages.takeLast(10)

                    val result =
                        askAurixUseCase(
                            question = trimmedQuestion,
                            conversationHistory = previousMessages
                        )

                    _messages.value =
                        _messages.value.map { message ->

                            if (
                                message.id ==
                                aurixMessageId
                            ) {
                                message.copy(
                                    content = result,
                                    status =
                                        ChatMessageStatus.SENT
                                )
                            } else {
                                message
                            }
                        }

                } catch (
                    exception: CancellationException
                ) {

                    throw exception

                } catch (
                    exception: Exception
                ) {

                    val errorMessage =
                        getUserFriendlyErrorMessage(
                            exception
                        )

                    _messages.value =
                        _messages.value.map { message ->

                            if (
                                message.id ==
                                aurixMessageId
                            ) {
                                message.copy(
                                    content = errorMessage,
                                    status =
                                        ChatMessageStatus.ERROR
                                )
                            } else {
                                message
                            }
                        }

                } finally {

                    _isLoading.value = false
                    currentRequestJob = null
                }
            }
    }

    /**
     * Retries the most recent failed AURIX response.
     *
     * The original user question is reused instead of creating
     * another duplicate user message.
     */
    fun retryLastQuestion() {

        if (_isLoading.value) {
            return
        }

        val currentMessages = _messages.value

        val errorMessageIndex =
            currentMessages.indexOfLast { message ->
                message.role == ChatMessageRole.AURIX &&
                        message.status == ChatMessageStatus.ERROR
            }

        if (errorMessageIndex == -1) {
            return
        }

        val userMessageIndex =
            currentMessages
                .subList(0, errorMessageIndex)
                .indexOfLast { message ->
                    message.role == ChatMessageRole.USER
                }

        if (userMessageIndex == -1) {
            return
        }

        val userMessage =
            currentMessages[userMessageIndex]

        val aurixMessage =
            currentMessages[errorMessageIndex]

        val conversationHistory =
            currentMessages.take(userMessageIndex)

        val loadingMessage =
            aurixMessage.copy(
                content = "",
                status = ChatMessageStatus.LOADING
            )

        _messages.value =
            currentMessages.map { message ->

                if (
                    message.id ==
                    aurixMessage.id
                ) {
                    loadingMessage
                } else {
                    message
                }
            }

        _isLoading.value = true

        currentRequestJob?.cancel()

        currentRequestJob =
            viewModelScope.launch {

                try {

                    val result =
                        askAurixUseCase(
                            question = userMessage.content,
                            conversationHistory =
                                conversationHistory
                        )

                    _messages.value =
                        _messages.value.map { message ->

                            if (
                                message.id ==
                                aurixMessage.id
                            ) {
                                message.copy(
                                    content = result,
                                    status =
                                        ChatMessageStatus.SENT
                                )
                            } else {
                                message
                            }
                        }

                } catch (
                    exception: CancellationException
                ) {

                    throw exception

                } catch (
                    exception: Exception
                ) {

                    val errorMessage =
                        getUserFriendlyErrorMessage(
                            exception
                        )

                    _messages.value =
                        _messages.value.map { message ->

                            if (
                                message.id ==
                                aurixMessage.id
                            ) {
                                message.copy(
                                    content = errorMessage,
                                    status =
                                        ChatMessageStatus.ERROR
                                )
                            } else {
                                message
                            }
                        }

                } finally {

                    _isLoading.value = false
                    currentRequestJob = null
                }
            }
    }

    /**
     * Clears the current AURIX conversation.
     *
     * Any active Gemini request is cancelled so that an old
     * response cannot appear after the conversation is cleared.
     */
    fun clearConversation() {

        currentRequestJob?.cancel()
        currentRequestJob = null

        _messages.value = emptyList()
        _isLoading.value = false
    }

    // Error exception:

    private fun getUserFriendlyErrorMessage(
        exception: Exception
    ): String {

        return when (exception) {

            AurixException.Network ->
                "AURIX couldn't connect right now. Please check your internet connection and try again."

            AurixException.PermissionDenied ->
                "AURIX doesn't currently have permission to use the AI service."

            AurixException.EmptyResponse ->
                "AURIX received an empty response. Please try again."

            AurixException.Unknown ->
                "AURIX couldn't process your request right now. Please try again."

            else ->
                "AURIX couldn't process your request right now. Please try again."
        }
    }

    /**
     * Generates a unique ID for each chat message.
     *
     * System.currentTimeMillis() is intentionally avoided because
     * two messages can be created during the same millisecond.
     */
    private fun generateMessageId(): Long {
        return UUID.randomUUID().mostSignificantBits
    }

    override fun onCleared() {
        currentRequestJob?.cancel()
        currentRequestJob = null

        super.onCleared()
    }
}