package com.finance.lumora.core.exception



/**
 * Base exception for all domain validation failures.
 */
open class ValidationException(
    override val message: String
) : Exception(message)