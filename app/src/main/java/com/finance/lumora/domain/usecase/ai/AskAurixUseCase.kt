package com.finance.lumora.domain.usecase.ai

//orchestrates everything
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class AskAurixUseCase @Inject constructor(
    private val financeContextBuilder: FinanceContextBuilder,
    private val financePeriodResolver: FinancePeriodResolver,
    private val aurixInsightDetector: AurixInsightDetector,
    private val resolveAurixInsightUseCase: ResolveAurixInsightUseCase,
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

        val insightType =
            aurixInsightDetector(
                question = question,
                conversationHistory = conversationHistory
            )

        val insightResult =
            resolveAurixInsightUseCase(
                insightType = insightType
            )

        val prompt =
            aurixPromptBuilder(
                question = question,
                financeContext = financeContext,
                conversationHistory = conversationHistory,
                expenseTrendInsight = insightResult.expenseTrendInsight,
                budgetInsight = insightResult.budgetInsight
            )

        return geminiService.generateResponse(
            prompt
        )
    }
}