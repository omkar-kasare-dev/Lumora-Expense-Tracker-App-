package com.finance.lumora.core.exception

open class ValidationException(
    override val message: String
) : Exception(message)