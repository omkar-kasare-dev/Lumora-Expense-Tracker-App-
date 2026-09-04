package com.finance.lumora.domain.usecase.ai



import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class AskAurixUseCase @Inject constructor(
    private val financeContextBuilder: FinanceContextBuilder,
    private val aurixPromptBuilder: AurixPromptBuilder,
    private val geminiService: GeminiService
) {

    suspend operator fun invoke(
        question: String,
        conversationHistory: List<ChatMessage> = emptyList()
    ): String {

        val financeContext =
            financeContextBuilder()

        val prompt =
            aurixPromptBuilder(
                question = question,
                financeContext = financeContext,
                conversationHistory = conversationHistory
            )

        return geminiService.generateResponse(
            prompt
        )
    }
}