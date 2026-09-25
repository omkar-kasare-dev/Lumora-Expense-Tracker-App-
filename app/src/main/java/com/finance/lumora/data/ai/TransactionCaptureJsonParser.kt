package com.finance.lumora.data.ai
/*
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

 */

import com.finance.lumora.data.ai.model.TransactionCaptureResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TransactionCaptureJsonParser @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun parse(response: String): TransactionCaptureResponse {

        val cleanedResponse = extractJsonObject(response)

        return json.decodeFromString(cleanedResponse)
    }

    /**
     * Extracts the JSON object from a model response, tolerating
     * markdown code fences and any stray commentary the model may
     * add before/after the JSON, despite being asked to return only
     * JSON. Falls back to simple fence-stripping if no braces are
     * found, preserving the previous behavior for already-clean input.
     */
    private fun extractJsonObject(response: String): String {

        val stripped = response
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        val start = stripped.indexOf('{')
        val end = stripped.lastIndexOf('}')

        return if (start != -1 && end != -1 && end > start) {
            stripped.substring(start, end + 1)
        } else {
            stripped
        }
    }
}