package com.finance.lumora.domain.usecase.ai
/*
// Main
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

 */


/*
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.FinancePeriod
import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class AskAurixUseCase @Inject constructor(
    private val financeContextBuilder: FinanceContextBuilder,
    private val aurixPromptBuilder: AurixPromptBuilder,
    private val geminiService: GeminiService
) {

    suspend operator fun invoke(
        question: String,
        period: FinancePeriod = FinancePeriod.CURRENT_MONTH,
        conversationHistory: List<ChatMessage> = emptyList()
    ): String {

        val financeContext =
            financeContextBuilder(
                period = period
            )

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

 */



import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class AskAurixUseCase @Inject constructor(
    private val financeContextBuilder: FinanceContextBuilder,
    private val financePeriodResolver: FinancePeriodResolver,
    private val aurixPromptBuilder: AurixPromptBuilder,
    private val geminiService: GeminiService
) {

    suspend operator fun invoke(
        question: String,
        conversationHistory: List<ChatMessage> = emptyList()
    ): String {

        val period =
            financePeriodResolver(
                question = question,
                conversationHistory = conversationHistory
            )

        val financeContext =
            financeContextBuilder(
                period = period
            )

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