package com.finance.lumora.data.remote.ai


import android.util.Log
import com.finance.lumora.domain.model.ai.AurixException
import com.finance.lumora.domain.repository.GeminiService
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.FirebaseAIException
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.QuotaExceededException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiServiceImpl @Inject constructor(
    private val model: GenerativeModel
) : GeminiService {



    override suspend fun generateResponse(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val response = model.generateContent(prompt)

            response.text
                ?.takeIf { it.isNotBlank() }
                ?: throw AurixException.EmptyResponse

        } catch (exception: AurixException) {
            throw exception

        } catch (exception: QuotaExceededException) {
            Log.w(
                "GeminiServiceImpl",
                "Gemini API quota exceeded. No retry will be attempted.",
                exception
            )
            throw AurixException.QuotaExceeded

        } catch (exception: FirebaseAIException) {
            Log.e("GeminiServiceImpl", "Firebase AI SDK call failed", exception)
            throw exception.toAurixException()

        } catch (exception: Exception) {
            Log.e("GeminiServiceImpl", "Gemini API call failed", exception)
            throw exception.toAurixException()
        }
    }

    private fun Throwable.toAurixException(): AurixException {
        val message = this.message?.lowercase().orEmpty()
        return when {
            message.contains("permission") ||
                    message.contains("permission_denied") ||
                    message.contains("unauthorized") -> {
                AurixException.PermissionDenied
            }

            message.contains("network") ||
                    message.contains("timeout") ||
                    message.contains("unable to resolve host") ||
                    message.contains("connection") ||
                    message.contains("socket") -> {
                AurixException.Network
            }

            else -> AurixException.Unknown
        }
    }
}