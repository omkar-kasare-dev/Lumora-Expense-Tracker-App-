package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.ChatMessage
import com.finance.lumora.domain.model.ai.ChatMessageRole
import com.finance.lumora.domain.model.ai.FinancePeriod
import java.util.Calendar
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

        resolveExplicitPeriod(normalizedQuestion)?.let {
            return it
        }

        if (isFollowUpQuestion(normalizedQuestion)) {
            return resolveFromConversationHistory(conversationHistory)
        }

        return FinancePeriod.CURRENT_MONTH
    }

    /**
     * Resolves an explicit period directly from question text, or
     * null if none is mentioned. Shared by both the current question
     * and, via [resolveFromConversationHistory], past questions -
     * previously these were two separate, near-duplicate blocks,
     * which is part of how "today"/"this week"/named months ended
     * up supported in neither.
     */
    private fun resolveExplicitPeriod(
        question: String
    ): FinancePeriod? {

        return when {

            containsTodayPhrase(question) -> {
                FinancePeriod.TODAY
            }

            containsThisWeekPhrase(question) -> {
                FinancePeriod.THIS_WEEK
            }

            containsCurrentYearPhrase(question) -> {
                FinancePeriod.CURRENT_YEAR
            }

            containsPreviousMonthPhrase(question) -> {
                FinancePeriod.PREVIOUS_MONTH
            }

            containsCurrentMonthPhrase(question) -> {
                FinancePeriod.CURRENT_MONTH
            }

            else -> {
                resolveSpecificMonth(question)
            }
        }
    }

    private fun containsTodayPhrase(question: String): Boolean {
        val phrases = listOf("today", "today's")
        return phrases.any { question.contains(it) }
    }

    private fun containsThisWeekPhrase(question: String): Boolean {
        val phrases = listOf(
            "this week",
            "current week",
            "this week's",
            "current week's"
        )
        return phrases.any { question.contains(it) }
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

    /**
     * Detects a specific named month (e.g. "August", "in July 2025")
     * so a question about a particular past month resolves to that
     * exact month instead of being denied. An explicit 4-digit year
     * is used if present; otherwise the current year is assumed.
     */
    private fun resolveSpecificMonth(
        question: String
    ): FinancePeriod.SpecificMonth? {

        val monthNames = listOf(
            "january" to Calendar.JANUARY,
            "february" to Calendar.FEBRUARY,
            "march" to Calendar.MARCH,
            "april" to Calendar.APRIL,
            "may" to Calendar.MAY,
            "june" to Calendar.JUNE,
            "july" to Calendar.JULY,
            "august" to Calendar.AUGUST,
            "september" to Calendar.SEPTEMBER,
            "october" to Calendar.OCTOBER,
            "november" to Calendar.NOVEMBER,
            "december" to Calendar.DECEMBER
        )

        val matchedMonth = monthNames.firstOrNull { (name, _) ->
            question.contains(name)
        } ?: return null

        val yearMatch = Regex("""\b(20\d{2})\b""").find(question)

        val year = yearMatch
            ?.value
            ?.toIntOrNull()
            ?: Calendar.getInstance().get(Calendar.YEAR)

        return FinancePeriod.SpecificMonth(
            year = year,
            month = matchedMonth.second
        )
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
}