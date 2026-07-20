package com.finance.lumora.domain.validation



/**
 * Represents the result of validating
 * business rules before executing a use case.
 */
sealed class ValidationResult {

    /**
     * Validation passed.
     */
    data object Success : ValidationResult()

    /**
     * Validation failed.
     */
    data class Error(
        val message: String
    ) : ValidationResult()
}