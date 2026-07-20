package com.finance.lumora.domain.validation



import com.finance.lumora.domain.model.Transaction
import javax.inject.Inject

/**
 * Contains business validation rules
 * for Transaction operations.
 */
class TransactionValidator @Inject constructor() {

    fun validate(
        transaction: Transaction
    ): ValidationResult {

        if (transaction.amount <= 0) {
            return ValidationResult.Error(
                "Amount must be greater than zero."
            )
        }

        if (transaction.categoryId <= 0L) {
            return ValidationResult.Error(
                "Please select a category."
            )
        }

        if (transaction.transactionDate <= 0L) {
            return ValidationResult.Error(
                "Invalid transaction date."
            )
        }

        if (transaction.note?.length ?: 0 > 500) {
            return ValidationResult.Error(
                "Note cannot exceed 500 characters."
            )
        }

        return ValidationResult.Success
    }
}