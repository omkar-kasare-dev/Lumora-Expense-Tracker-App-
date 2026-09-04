package com.finance.lumora.domain.repository

interface GeminiService {
    suspend fun generateResponse(
        prompt: String
    ): String
}