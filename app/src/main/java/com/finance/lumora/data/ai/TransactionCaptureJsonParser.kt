package com.finance.lumora.data.ai



import com.finance.lumora.data.ai.model.TransactionCaptureResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TransactionCaptureJsonParser @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun parse(response: String): TransactionCaptureResponse {

        val cleanedResponse = response
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        return json.decodeFromString(cleanedResponse)
    }
}