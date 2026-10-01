package com.finance.lumora.domain.usecase.ai
/* main
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
                insightType = insightType,
                financeContext = financeContext,
                period = period
            )

        val prompt =
            aurixPromptBuilder(
                question = question,
                financeContext = financeContext,
                conversationHistory = conversationHistory,
                expenseTrendInsight = insightResult.expenseTrendInsight,
                budgetInsight = insightResult.budgetInsight,
                spendingConcentrationInsight =
                    insightResult.spendingConcentrationInsight
            )

        return geminiService.generateResponse(
            prompt
        )
    }
}

 */



import com.finance.lumora.data.local.prefs.AurixPreferences
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.repository.GeminiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AskAurixUseCase @Inject constructor(
    private val financeContextBuilder: FinanceContextBuilder,
    private val financePeriodResolver: FinancePeriodResolver,
    private val aurixInsightDetector: AurixInsightDetector,
    private val resolveAurixInsightUseCase: ResolveAurixInsightUseCase,
    private val aurixPromptBuilder: AurixPromptBuilder,
    private val geminiService: GeminiService,
    private val aurixPreferences: AurixPreferences
) {

    suspend operator fun invoke(
        question: String,
        conversationHistory: List<ChatMessage> = emptyList()
    ): String {

        // "Auto Financial Context" toggle: when off, Aurix answers
        // using only the question and conversation history, with no
        // transaction/budget data attached to the prompt at all.
        val autoContextEnabled = aurixPreferences.isAutoContextEnabled.first()

        if (!autoContextEnabled) {
            val prompt = aurixPromptBuilder(
                question = question,
                financeContext = null,
                conversationHistory = conversationHistory,
                expenseTrendInsight = null,
                budgetInsight = null,
                spendingConcentrationInsight = null
            )
            return geminiService.generateResponse(prompt)
        }

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
                insightType = insightType,
                financeContext = financeContext,
                period = period
            )

        val prompt =
            aurixPromptBuilder(
                question = question,
                financeContext = financeContext,
                conversationHistory = conversationHistory,
                expenseTrendInsight = insightResult.expenseTrendInsight,
                budgetInsight = insightResult.budgetInsight,
                spendingConcentrationInsight =
                    insightResult.spendingConcentrationInsight
            )

        return geminiService.generateResponse(
            prompt
        )
    }
}