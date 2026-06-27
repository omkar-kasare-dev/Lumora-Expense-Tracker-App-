package com.finance.lumora.core.exception



class CategoryValidationException(
    override val message: String
) : ValidationException(message)