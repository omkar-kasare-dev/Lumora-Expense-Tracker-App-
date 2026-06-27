package com.finance.lumora.core.exception



class ExpenseValidationException(
    override val message: String
) : ValidationException(message)