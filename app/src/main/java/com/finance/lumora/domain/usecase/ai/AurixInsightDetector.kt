package com.finance.lumora.domain.usecase.ai

/**
 * Main Use of purpose :
 * identifies the required insight
 */
import com.finance.lumora.domain.model.ai.AurixInsightType
import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import javax.inject.Inject

class AurixInsightDetector @Inject constructor() {

    operator fun invoke(
        question: String,
        conversationHistory: List<ChatMessage> = emptyList()
    ): AurixInsightType {

        val normalizedQuestion =
            normalizeQuestion(question)

        if (isExpenseTrendQuestion(normalizedQuestion)) {
            return AurixInsightType.EXPENSE_TREND
        }

        if (isBudgetQuestion(normalizedQuestion)) {
            return AurixInsightType.BUDGET
        }

        if (isSpendingConcentrationQuestion(normalizedQuestion)) {
            return AurixInsightType.SPENDING_CONCENTRATION
        }

        if (
            isTrendFollowUpQuestion(
                question = normalizedQuestion,
                conversationHistory = conversationHistory
            )
        ) {
            return AurixInsightType.EXPENSE_TREND
        }

        return AurixInsightType.NONE
    }

    private fun isExpenseTrendQuestion(
        question: String
    ): Boolean {

        return EXPENSE_TREND_PHRASES.any {
            question.contains(it)
        }
    }

    private fun isBudgetQuestion(
        question: String
    ): Boolean {

        return BUDGET_PHRASES.any {
            question.contains(it)
        }
    }

    private fun isSpendingConcentrationQuestion(
        question: String
    ): Boolean {
        return SPENDING_CONCENTRATION_PHRASES.any {
            question.contains(it)
        }
    }

    private fun isTrendFollowUpQuestion(
        question: String,
        conversationHistory: List<ChatMessage>
    ): Boolean {

        if (conversationHistory.isEmpty()) {
            return false
        }

        val isFollowUp =
            TREND_FOLLOW_UP_PHRASES.any {
                question.contains(it)
            }

        if (!isFollowUp) {
            return false
        }

        val latestUserQuestion =
            conversationHistory
                .asReversed()
                .firstOrNull {
                    it.role == ChatMessageRole.USER
                }
                ?.content
                ?.let(::normalizeQuestion)

        if (latestUserQuestion == null) {
            return false
        }

        return EXPENSE_TREND_FOLLOW_UP_CONTEXT_PHRASES.any {
            latestUserQuestion.contains(it)
        }
    }

    companion object {

        private val EXPENSE_TREND_PHRASES =
            listOf(

                // Direct spending comparison
                "spend more",
                "spent more",
                "spending more",
                "spend less",
                "spent less",
                "spending less",

                // Expense change
                "expense increased",
                "expenses increased",
                "expense decreased",
                "expenses decreased",
                "expense change",
                "expenses change",

                // Trend
                "expense trend",
                "expenses trend",
                "spending trend",
                "spending increasing",
                "spending decreasing",
                "expenses increasing",
                "expenses decreasing",

                // Comparison
                "compare my expenses",
                "compare my spending",
                "compared to last month",
                "compared with last month",
                "compared to previous month",
                "compared with previous month",
                "more than last month",
                "less than last month",

                // Natural language
                "how has my spending changed",
                "how has my expenses changed",
                "how much has my spending changed",
                "how much have my expenses changed",
                "has my spending changed",
                "have my expenses changed",
                "what happened to my spending",
                "what happened to my expenses",
                "how different is my spending",
                "how different are my expenses",
                "am i spending differently",
                "is my spending changing",
                "is my spending increasing",
                "is my spending decreasing",
                "are my expenses increasing",
                "are my expenses decreasing",
                "am i spending more",
                "am i spending less",
                "is my spending going up",
                "is my spending going down",
                "are my expenses going up",
                "are my expenses going down",
                "is my spending going up or down",
                "are my expenses going up or down"
            )

        private val TREND_FOLLOW_UP_PHRASES =
            listOf(
                "how much was the difference",
                "what was the difference",
                "what is the difference",
                "how big was the difference",
                "how much did it change",
                "what changed",
                "how much did it decrease",
                "how much did it increase",
                "by how much",
                "what about the change",
                "what about the trend",
                "tell me more about that",
                "explain that change",
                "explain the difference",
                "by what percentage",
                "what percentage",
                "what was the percentage",
                "what is the percentage",
                "how much percentage",
                "how many percent"
            )

        private val EXPENSE_TREND_FOLLOW_UP_CONTEXT_PHRASES =
            listOf(
                "spend more",
                "spent more",
                "spending more",
                "spend less",
                "spent less",
                "spending less",
                "expense increased",
                "expenses increased",
                "expense decreased",
                "expenses decreased",
                "expense change",
                "expenses change",
                "expense trend",
                "expenses trend",
                "spending trend",
                "spending increasing",
                "spending decreasing",
                "expenses increasing",
                "expenses decreasing",
                "how has my spending changed",
                "how has my expenses changed",
                "spend more",
                "spend less",
                "compare my expenses",
                "compare my spending",
                "compared to last month",
                "compared with last month",
                "more than last month",
                "less than last month",
                // Percentage change
                "percentage change",
                "percentage of change",
                "percent change",
                "percentage did my spending change",
                "percentage did my expenses change",
                "percentage did my spending increase",
                "percentage did my spending decrease",
                "percentage did my expenses increase",
                "percentage did my expenses decrease",
                "percent did i spend more",
                "percent did i spend less",
                "percentage did i spend more",
                "percentage did i spend less",
                "how many percent did i spend more",
                "how many percent did i spend less"
            )

        private val BUDGET_PHRASES =
            listOf(

                // Budget amount
                "my budget",
                "monthly budget",
                "budget amount",

                // Remaining budget
                "budget left",
                "budget remaining",
                "remaining budget",
                "how much budget do i have left",
                "how much of my budget is left",
                "how much money is left in my budget",

                // Budget usage
                "budget usage",
                "budget used",
                "how much of my budget have i used",
                "how much of my budget did i use",
                "how much budget have i used",

                // Budget comparison
                "spent compared to my budget",
                "spending compared to my budget",
                "expense compared to my budget",
                "expenses compared to my budget",
                "how much have i spent compared to my budget",

                // Budget status
                "am i over my budget",
                "am i over budget",
                "have i exceeded my budget",
                "did i exceed my budget",
                "is my budget exceeded",
                "am i within my budget",

                // Budget progress
                "how close am i to my budget",
                "how close am i to reaching my budget",
                "how much of my budget is used",
                "what percentage of my budget have i used",
                "what percentage of my budget did i use"
            )

        private val SPENDING_CONCENTRATION_PHRASES =
            listOf(
                "largest spending category",
                "largest spend category",
                "biggest spending category",
                "biggest spend category",
                "highest spending category",
                "highest spend category",
                "category i spend the most on",
                "category do i spend the most on",
                "where do i spend the most",
                "where am i spending the most",
                "what do i spend the most on",
                "what category do i spend the most on",
                "which category do i spend the most on",
                "which category has the highest expense",
                "which category has the highest spending",
                "which category costs me the most",
                "what is my biggest expense category",
                "what is my highest expense category",
                "what is my largest expense category"
            )
    }
    private fun normalizeQuestion(
        question: String
    ): String {
        return question
            .trim()
            .lowercase()
    }
}