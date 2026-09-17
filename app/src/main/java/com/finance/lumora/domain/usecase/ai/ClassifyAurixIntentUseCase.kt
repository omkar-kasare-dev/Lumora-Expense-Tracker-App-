package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.AurixIntent
import com.finance.lumora.domain.model.ai.QueryType
import com.google.firebase.ai.GenerativeModel
import javax.inject.Inject
import org.json.JSONObject

class ClassifyAurixIntentUseCase @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    suspend operator fun invoke(input: String): AurixIntent {
        val fullPrompt = """
            You are an intent classifier for a personal finance app.
            Analyze the following user input and determine whether the user is trying to record a transaction or ask a financial question.

            CLASSIFICATION RULES:
            1. 'ADD_TRANSACTION': Select ONLY IF the user explicitly states they spent, paid, sent, received, or earned money with a numeric amount (e.g., "Spent 500 on coffee", "Paid rent 12000", "Received 1000 salary").
            2. 'QUERY_FINANCE': Select IF the user is asking a question, asking for totals, summaries, budget status, transaction history, or category breakdown (e.g., "How much did I spend?", "Show me my expenses", "Total spent on food", "How much budget left?").

            QUERY TYPES (if intent is 'QUERY_FINANCE'):
            - 'TOTAL_EXPENSE': Overall spend questions with NO target category specified.
            - 'TOTAL_INCOME': Overall income questions.
            - 'CATEGORY_SPENDING': Questions asking about spend/expense in a specific category (e.g., food, rent, education, transport, travel, shopping).
            - 'RECENT_TRANSACTIONS': Requests to list past transactions or recent spending.

            CATEGORY EXTRACTION:
            - If the query mentions a target category or item type (e.g., "food", "education", "groceries", "rent"), extract it into "categoryName" and set "queryType" to "CATEGORY_SPENDING".

            OUTPUT FORMAT:
            Respond STRICTLY with a raw JSON object and no additional Markdown formatting or text:
            {
              "intent": "ADD_TRANSACTION" | "QUERY_FINANCE",
              "queryType": "TOTAL_EXPENSE" | "TOTAL_INCOME" | "CATEGORY_SPENDING" | "RECENT_TRANSACTIONS" | "UNKNOWN",
              "categoryName": "extracted category or item name (or null if not applicable)",
              "startDateIso": "YYYY-MM-DD or null",
              "endDateIso": "YYYY-MM-DD or null"
            }

            USER INPUT: "$input"
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(fullPrompt)
            val jsonText = response.text?.trim()
                ?.removePrefix("```json")
                ?.removePrefix("```")
                ?.removeSuffix("```")
                ?.trim() ?: ""

            val json = JSONObject(jsonText)
            val intentStr = json.optString("intent", "")

            if (intentStr == "QUERY_FINANCE") {
                val typeStr = json.optString("queryType", "TOTAL_EXPENSE")
                val extractedCategory = json.optString("categoryName", null)
                    ?.takeIf { it.isNotBlank() && !it.equalsIgnoreCase("null") }

                val queryType = mapQueryType(typeStr, extractedCategory)

                AurixIntent.QueryFinance(
                    rawQuery = input,
                    queryType = queryType,
                    categoryName = extractedCategory,
                    startDateIso = json.optString("startDateIso", null)?.takeIf { it.isNotBlank() && !it.equalsIgnoreCase("null") },
                    endDateIso = json.optString("endDateIso", null)?.takeIf { it.isNotBlank() && !it.equalsIgnoreCase("null") }
                )
            } else if (intentStr == "ADD_TRANSACTION") {
                AurixIntent.AddTransaction(rawText = input)
            } else {
                inferFallbackIntent(input)
            }
        } catch (e: Exception) {
            inferFallbackIntent(input)
        }
    }

    private fun mapQueryType(typeStr: String, categoryName: String?): QueryType {
        if (!categoryName.isNullOrBlank()) {
            return QueryType.CATEGORY_SPENDING
        }
        return try {
            when (typeStr.uppercase()) {
                "CATEGORY_SPEND", "CATEGORY_SPENDING" -> QueryType.CATEGORY_SPENDING
                "TOTAL_INCOME" -> QueryType.TOTAL_INCOME
                "RECENT_TRANSACTIONS", "TRANSACTION_HISTORY" -> QueryType.RECENT_TRANSACTIONS
                "TOTAL_EXPENSE" -> QueryType.TOTAL_EXPENSE
                else -> QueryType.valueOf(typeStr)
            }
        } catch (e: Exception) {
            QueryType.TOTAL_EXPENSE
        }
    }

    private fun inferFallbackIntent(input: String): AurixIntent {
        val lower = input.lowercase()
        val isQuestion = lower.contains("how much") ||
                lower.contains("total") ||
                lower.contains("show") ||
                lower.contains("what") ||
                lower.contains("detail") ||
                lower.contains("expense") ||
                lower.endsWith("?")

        return if (isQuestion) {
            val category = extractCategoryHeuristic(input)
            val queryType = if (category != null) QueryType.CATEGORY_SPENDING else QueryType.TOTAL_EXPENSE

            AurixIntent.QueryFinance(
                rawQuery = input,
                queryType = queryType,
                categoryName = category
            )
        } else {
            AurixIntent.AddTransaction(rawText = input)
        }
    }

    private fun extractCategoryHeuristic(input: String): String? {
        val pattern = Regex("""(?i)\b(?:on|on to|for|in|about)\s+([a-zA-Z]+)""")
        val match = pattern.find(input)
        val extracted = match?.groupValues?.getOrNull(1)

        val excludedWords = setOf("this", "the", "my", "current", "last", "month", "year", "week", "today")
        return if (extracted != null && !excludedWords.contains(extracted.lowercase())) {
            extracted
        } else {
            null
        }
    }

    private fun String.equalsIgnoreCase(other: String): Boolean = this.equals(other, ignoreCase = true)
}