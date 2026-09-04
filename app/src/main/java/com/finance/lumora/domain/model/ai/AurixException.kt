package com.finance.lumora.domain.model.ai


sealed class AurixException(
    message: String
) : Exception(message) {

    data object Network : AurixException(
        "AURIX is unable to connect right now."
    )

    data object PermissionDenied : AurixException(
        "AURIX does not currently have permission to access the AI service."
    )

    data object EmptyResponse : AurixException(
        "AURIX received an empty response."
    )

    data object Unknown : AurixException(
        "AURIX couldn't process the request right now."
    )
}