package com.finance.lumora.data.remote.ai


import com.finance.lumora.domain.repository.GeminiService
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import javax.inject.Inject

class GeminiServiceImpl @Inject constructor() : GeminiService {

    private val model = Firebase.ai(
        backend = GenerativeBackend.googleAI()
    ).generativeModel(
        "gemini-3.7-flash"
    )

    override suspend fun generateResponse(
        prompt: String
    ): String {

        val response = model.generateContent(prompt)

        return response.text
            ?: throw IllegalStateException(
                "Gemini returned an empty response."
            )
    }
}