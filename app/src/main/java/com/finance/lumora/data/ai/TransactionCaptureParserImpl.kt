package com.finance.lumora.data.ai

import com.finance.lumora.domain.ai.TransactionCaptureValidator
import com.finance.lumora.domain.model.CaptureSource
import com.finance.lumora.domain.model.DraftTransaction
import com.finance.lumora.domain.model.ai.TransactionCaptureParser
import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class TransactionCaptureParserImpl @Inject constructor(
    private val geminiService: GeminiService,
    private val promptBuilder: TransactionCapturePromptBuilder,
    private val jsonParser: TransactionCaptureJsonParser,
    private val validator: TransactionCaptureValidator
) : TransactionCaptureParser {

    override suspend fun parse(
        input: String,
        source: CaptureSource
    ): DraftTransaction {

        require(input.isNotBlank()) {
            "Transaction capture input cannot be empty."
        }

        val prompt = promptBuilder.build(
            input = input,
            source = source
        )

        val response = geminiService.generateResponse(prompt)

        val parsedResponse = jsonParser.parse(response)

        validator.validate(parsedResponse)

        return DraftTransaction(
            amount = parsedResponse.amount!!,
            currency = parsedResponse.currency,
            merchantName = parsedResponse.merchantName,
            categoryName = parsedResponse.categoryName,
            transactionDate = parsedResponse.transactionDate!!,
            source = source
        )
    }
}