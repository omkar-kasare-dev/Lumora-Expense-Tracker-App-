package com.finance.lumora.domain.usecase.ai
/*
import com.finance.lumora.domain.model.ai.FinancePeriod
import javax.inject.Inject

class FinancePeriodResolver @Inject constructor() {

    operator fun invoke(
        question: String
    ): FinancePeriod {

        val normalizedQuestion =
            question
                .trim()
                .lowercase()

        return when {

            containsCurrentYearPhrase(
                normalizedQuestion
            ) -> {
                FinancePeriod.CURRENT_YEAR
            }

            containsPreviousMonthPhrase(
                normalizedQuestion
            ) -> {
                FinancePeriod.PREVIOUS_MONTH
            }

            else -> {
                FinancePeriod.CURRENT_MONTH
            }
        }
    }

    private fun containsCurrentYearPhrase(
        question: String
    ): Boolean {

        val phrases = listOf(
            "this year",
            "current year",
            "this year's",
            "current year's"
        )

        return phrases.any {
            question.contains(it)
        }
    }

    private fun containsPreviousMonthPhrase(
        question: String
    ): Boolean {

        val phrases = listOf(
            "last month",
            "previous month",
            "prior month",
            "last month's",
            "previous month's",
            "prior month's"
        )

        return phrases.any {
            question.contains(it)
        }
    }
}

 */


import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.FinancePeriod
import javax.inject.Inject

class FinancePeriodResolver @Inject constructor() {

    operator fun invoke(
        question: String,
        conversationHistory: List<ChatMessage> = emptyList()
    ): FinancePeriod {

        val normalizedQuestion =
            question
                .trim()
                .lowercase()

        return when {

            containsCurrentYearPhrase(
                normalizedQuestion
            ) -> {
                FinancePeriod.CURRENT_YEAR
            }

            containsPreviousMonthPhrase(
                normalizedQuestion
            ) -> {
                FinancePeriod.PREVIOUS_MONTH
            }

            containsCurrentMonthPhrase(
                normalizedQuestion
            ) -> {
                FinancePeriod.CURRENT_MONTH
            }

            isFollowUpQuestion(
                normalizedQuestion
            ) -> {
                resolveFromConversationHistory(
                    conversationHistory
                )
            }

            else -> {
                FinancePeriod.CURRENT_MONTH
            }
        }
    }

    private fun containsCurrentYearPhrase(
        question: String
    ): Boolean {

        val phrases = listOf(
            "this year",
            "current year",
            "this year's",
            "current year's"
        )

        return phrases.any {
            question.contains(it)
        }
    }

    private fun containsPreviousMonthPhrase(
        question: String
    ): Boolean {

        val phrases = listOf(
            "last month",
            "previous month",
            "prior month",
            "last month's",
            "previous month's",
            "prior month's"
        )

        return phrases.any {
            question.contains(it)
        }
    }

    private fun containsCurrentMonthPhrase(
        question: String
    ): Boolean {

        val phrases = listOf(
            "this month",
            "current month",
            "this month's",
            "current month's"
        )

        return phrases.any {
            question.contains(it)
        }
    }

    private fun isFollowUpQuestion(
        question: String
    ): Boolean {

        val followUpPhrases = listOf(
            "it",
            "that",
            "this",
            "there",
            "the previous one",
            "the above",
            "that amount",
            "that spending",
            "that expense",
            "those expenses",
            "those transactions"
        )

        return followUpPhrases.any { phrase ->
            question == phrase ||
                    question.contains(" $phrase ") ||
                    question.startsWith("$phrase ") ||
                    question.endsWith(" $phrase")
        }
    }

    private fun resolveFromConversationHistory(
        conversationHistory: List<ChatMessage>
    ): FinancePeriod {

        val previousPeriod =
            conversationHistory
                .asReversed()
                .asSequence()
                .filter {
                    it.role == ChatMessageRole.USER
                }
                .map {
                    it.content.trim().lowercase()
                }
                .mapNotNull {
                    resolveExplicitPeriod(it)
                }
                .firstOrNull()

        return previousPeriod
            ?: FinancePeriod.CURRENT_MONTH
    }

    private fun resolveExplicitPeriod(
        question: String
    ): FinancePeriod? {

        return when {

            containsCurrentYearPhrase(
                question
            ) -> {
                FinancePeriod.CURRENT_YEAR
            }

            containsPreviousMonthPhrase(
                question
            ) -> {
                FinancePeriod.PREVIOUS_MONTH
            }

            containsCurrentMonthPhrase(
                question
            ) -> {
                FinancePeriod.CURRENT_MONTH
            }

            else -> {
                null
            }
        }
    }
}