package com.finance.lumora.domain.ai

import com.finance.lumora.data.ai.model.TransactionCaptureResponse
import javax.inject.Inject

class TransactionCaptureValidator @Inject constructor() {

    fun validate(response: TransactionCaptureResponse) {

        val amount = response.amount
            ?: throw IllegalArgumentException(
                "Transaction amount could not be identified."
            )

        require(amount > 0.0) {
            "Transaction amount must be greater than zero."
        }

        require(amount.isFinite()) {
            "Transaction amount must be a valid number."
        }

        val date = response.transactionDate
            ?: throw IllegalArgumentException(
                "Transaction date could not be identified."
            )

        require(
            Regex("""\d{4}-\d{2}-\d{2}""").matches(date)
        ) {
            "Transaction date must use YYYY-MM-DD format."
        }
    }
}