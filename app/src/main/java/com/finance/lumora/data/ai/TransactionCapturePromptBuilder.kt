package com.finance.lumora.data.ai


import com.finance.lumora.domain.model.CaptureSource
import javax.inject.Inject

class TransactionCapturePromptBuilder @Inject constructor() {

    fun build(
        input: String,
        source: CaptureSource
    ): String {
        val sourceDescription = when (source) {
            CaptureSource.RECEIPT_OCR ->
                "The input was extracted from a shopping receipt using OCR."

            CaptureSource.VOICE ->
                "The input was transcribed from a user's voice command."
        }

        return """
            You are AURIX, a financial transaction extraction assistant
            inside the Lumora expense tracker.

            Your task is to extract transaction information from the
            provided input.

            $sourceDescription

            INPUT:
            $input

            Return ONLY valid JSON.
            Do not include markdown.
            Do not include explanations.
            Do not include ```json fences.

            Required JSON structure:

            {
              "amount": number,
              "currency": "string or null",
              "merchantName": "string or null",
              "categoryName": "string or null",
              "transactionDate": "YYYY-MM-DD"
            }

            Rules:

            1. amount
               - Extract the actual transaction amount.
               - Do not return a formatted currency symbol.
               - Return only the numeric value.
               - If no reliable amount can be identified, return null.

            2. currency
               - Identify the currency if explicitly available.
               - Examples: INR, USD, EUR.
               - If the currency cannot be determined, return null.

            3. merchantName
               - For receipts, extract the store or merchant name.
               - For voice input, extract the merchant if explicitly mentioned.
               - Otherwise return null.

            4. categoryName
               - Infer a sensible expense category from the transaction.
               - Prefer common categories such as:
                 Food, Groceries, Shopping, Transport,
                 Entertainment, Bills, Health, Education,
                 Travel, Personal Care.
               - Do not invent overly specific categories.

            5. transactionDate
               - Return the transaction date as YYYY-MM-DD.
               - If the input says "today", use today's date.
               - If no date is available, use today's date.

            6. Never invent an amount.
            7. Never invent a merchant.
            8. Return null when information cannot be reliably extracted.
        """.trimIndent()
    }
}