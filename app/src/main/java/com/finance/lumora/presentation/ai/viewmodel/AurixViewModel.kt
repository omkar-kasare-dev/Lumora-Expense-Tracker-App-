package com.finance.lumora.presentation.ai.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.ai.AurixException
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

@HiltViewModel
class AurixViewModel @Inject constructor(
    private val askAurixUseCase: AskAurixUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var currentRequestJob: Job? = null

    /**
     * Sends a new user question to AURIX.
     */
    fun askQuestion(question: String) {
        val trimmedQuestion = question.trim()

        if (trimmedQuestion.isBlank() || _isLoading.value) {
            return
        }

        val previousMessages = _messages.value

        val userMessage = ChatMessage(
            id = generateMessageId(),
            role = ChatMessageRole.USER,
            content = trimmedQuestion,
            status = ChatMessageStatus.SENT
        )

        val aurixMessageId = generateMessageId()

        val loadingMessage = ChatMessage(
            id = aurixMessageId,
            role = ChatMessageRole.AURIX,
            content = "",
            status = ChatMessageStatus.LOADING
        )

        _messages.value = previousMessages + userMessage + loadingMessage

        // Limit conversation history window
        val conversationHistory = previousMessages.takeLast(10)

        executeAurixRequest(
            aurixMessageId = aurixMessageId,
            question = trimmedQuestion,
            conversationHistory = conversationHistory
        )
    }

    /**
     * Retries the most recent failed AURIX response.
     */
    fun retryLastQuestion() {
        if (_isLoading.value) return

        val currentMessages = _messages.value

        val errorMessageIndex = currentMessages.indexOfLast { message ->
            message.role == ChatMessageRole.AURIX && message.status == ChatMessageStatus.ERROR
        }
        if (errorMessageIndex == -1) return

        val userMessageIndex = currentMessages
            .subList(0, errorMessageIndex)
            .indexOfLast { message -> message.role == ChatMessageRole.USER }
        if (userMessageIndex == -1) return

        val userMessage = currentMessages[userMessageIndex]
        val aurixMessage = currentMessages[errorMessageIndex]
        val conversationHistory = currentMessages.take(userMessageIndex).takeLast(10)

        val loadingMessage = aurixMessage.copy(
            content = "",
            status = ChatMessageStatus.LOADING
        )

        _messages.value = currentMessages.map { message ->
            if (message.id == aurixMessage.id) loadingMessage else message
        }

        executeAurixRequest(
            aurixMessageId = aurixMessage.id,
            question = userMessage.content,
            conversationHistory = conversationHistory
        )
    }

    /**
     * Common helper function to execute request execution, state updates, and error handling.
     */
    private fun executeAurixRequest(
        aurixMessageId: Long,
        question: String,
        conversationHistory: List<ChatMessage>
    ) {
        // Cancel ongoing request before starting a new job
        currentRequestJob?.cancel()
        _isLoading.value = true

        currentRequestJob = viewModelScope.launch {
            try {
                val result = askAurixUseCase(
                    question = question,
                    conversationHistory = conversationHistory
                )

                _messages.value = _messages.value.map { message ->
                    if (message.id == aurixMessageId) {
                        message.copy(
                            content = result,
                            status = ChatMessageStatus.SENT
                        )
                    } else {
                        message
                    }
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                val errorMessage = getUserFriendlyErrorMessage(exception)

                _messages.value = _messages.value.map { message ->
                    if (message.id == aurixMessageId) {
                        message.copy(
                            content = errorMessage,
                            status = ChatMessageStatus.ERROR
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
     */
    fun clearConversation() {
        currentRequestJob?.cancel()
        currentRequestJob = null
        _messages.value = emptyList()
        _isLoading.value = false
    }

    private fun getUserFriendlyErrorMessage(exception: Exception): String {
        return when (exception) {
            is AurixException.Network ->
                "AURIX couldn't connect right now. Please check your internet connection and try again."

            is AurixException.PermissionDenied ->
                "AURIX doesn't currently have permission to use the AI service."

            is AurixException.QuotaExceeded ->
                "AURIX has temporarily reached its AI request limit. Please try again later."

            is AurixException.EmptyResponse ->
                "AURIX received an empty response. Please try again."

            is AurixException.Unknown ->
                "AURIX couldn't process your request right now. Please try again."

            else ->
                "AURIX couldn't process your request right now. Please try again."
        }
    }

    private fun generateMessageId(): Long {
        return UUID.randomUUID().mostSignificantBits
    }

    override fun onCleared() {
        currentRequestJob?.cancel()
        currentRequestJob = null
        super.onCleared()
    }
}