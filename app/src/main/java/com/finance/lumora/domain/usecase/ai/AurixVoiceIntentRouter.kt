package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.AurixVoiceIntent
import javax.inject.Inject

class AurixVoiceIntentRouter @Inject constructor() {

    operator fun invoke(transcript: String): AurixVoiceIntent {

        val text = transcript
            .trim()
            .lowercase()

        if (text.isBlank()) {
            return AurixVoiceIntent.FINANCIAL_QUERY
        }

        if (isTransactionRequest(text)) {
            return AurixVoiceIntent.ADD_TRANSACTION
        }

        // Whether or not this matches a known query pattern, unmatched
        // speech falls through to FINANCIAL_QUERY either way, since
        // AurixVoiceIntent has no third "unrecognized" option. Kept as
        // an explicit check (rather than just returning FINANCIAL_QUERY
        // directly) so query-pattern matching stays meaningful if a
        // third intent is ever added later.
        isFinancialQuery(text)

        return AurixVoiceIntent.FINANCIAL_QUERY
    }

    private fun isTransactionRequest(text: String): Boolean {

        val transactionPatterns = listOf(
            "i spent",
            "i paid",
            "i bought",
            "i purchased",
            "spent ",
            "paid ",
            "bought ",
            "purchased ",
            "add an expense",
            "add expense",
            "record an expense",
            "record expense",
            "log an expense",
            "log expense",

            // Income-indicating phrases
            "i received",
            "i got paid",
            "i earned",
            "i was paid",
            "received a refund",
            "got a refund",
            "got refunded",
            "credited to my account",
            "salary credited",
            "payment received",
            "add income",
            "add an income",
            "record income",
            "log income"
        )

        return transactionPatterns.any { pattern ->
            text.contains(pattern)
        }
    }

    private fun isFinancialQuery(text: String): Boolean {

        val queryPatterns = listOf(
            "how much did i spend",
            "how much have i spent",
            "how much do i spend",
            "what did i spend",
            "what have i spent",
            "show my spending",
            "show my expenses",
            "how much is my spending",
            "how much are my expenses",
            "expense trend",
            "spending trend",
            "compare my expenses",
            "compare my spending",
            "how is my spending",
            "how are my expenses",
            "am i over budget",
            "how much is my budget",
            "what is my budget",
            "highest spending",
            "largest spending",
            "highest expense",
            "largest expense",
            "most expensive category",
            "show my transaction history",
            "show my transaction list",
            "show my recent transactions",
            "show recent transactions"
        )

        return queryPatterns.any { pattern ->
            text.contains(pattern)
        }
    }
}