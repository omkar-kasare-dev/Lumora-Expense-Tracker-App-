package com.finance.lumora.core.exception

class BudgetValidationException(
    override val message: String
) : ValidationException(message)