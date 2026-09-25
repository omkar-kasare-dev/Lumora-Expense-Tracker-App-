package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.AurixVoiceIntent
import javax.inject.Inject

class AurixVoiceIntentRouter @Inject constructor() {

    operator fun invoke(transcript: String): AurixVoiceIntent {

        val text = transcript
            .trim()
            .lowercase()

        if (text.isBlank()) {
            return AurixVoiceIntent.ADD_TRANSACTION
        }

        if (isTransactionRequest(text)) {
            return AurixVoiceIntent.ADD_TRANSACTION
        }

        return if (isTransactionRequest(text)) {  // move this check first, unchanged
            AurixVoiceIntent.ADD_TRANSACTION
        } else {
            AurixVoiceIntent.FINANCIAL_QUERY  // changed default
        }
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
            "log expense"
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
            "most expensive category"
        )

        return queryPatterns.any { pattern ->
            text.contains(pattern)
        }
    }
}