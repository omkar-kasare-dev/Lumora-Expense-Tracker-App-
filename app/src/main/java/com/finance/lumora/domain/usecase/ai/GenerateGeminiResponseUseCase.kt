package com.finance.lumora.domain.usecase.ai


import com.finance.lumora.domain.repository.GeminiService
import javax.inject.Inject

class GenerateGeminiResponseUseCase @Inject constructor(
    private val geminiService: GeminiService
) {

    suspend operator fun invoke(
        prompt: String
    ): String {
        return geminiService.generateResponse(prompt)
    }
}