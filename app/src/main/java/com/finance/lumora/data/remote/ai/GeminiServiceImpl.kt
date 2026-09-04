package com.finance.lumora.data.remote.ai


import com.finance.lumora.domain.model.ai.AurixException
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

        return try {

            val response =
                model.generateContent(prompt)

            response.text
                ?.takeIf { it.isNotBlank() }
                ?: throw AurixException.EmptyResponse

        } catch (exception: AurixException) {

            throw exception

        } catch (exception: Exception) {

            val message =
                exception.message
                    ?.lowercase()
                    .orEmpty()

            when {

                message.contains("permission") ||
                        message.contains("permission_denied") ||
                        message.contains("unauthorized") -> {

                    throw AurixException.PermissionDenied
                }

                message.contains("network") ||
                        message.contains("timeout") ||
                        message.contains("unable to resolve host") ||
                        message.contains("connection") ||
                        message.contains("socket") -> {

                    throw AurixException.Network
                }

                else -> {

                    throw AurixException.Unknown
                }
            }
        }
    }
}