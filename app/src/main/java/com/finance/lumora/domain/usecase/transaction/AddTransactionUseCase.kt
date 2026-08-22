package com.finance.lumora.domain.usecase.transaction

import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.repository.TransactionRepository
import com.finance.lumora.domain.validation.TransactionValidator
import com.finance.lumora.domain.validation.ValidationResult
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val validator: TransactionValidator
) {

    suspend operator fun invoke(
        transaction: Transaction
    ): ValidationResult {

        return when (
            val result = validator.validate(transaction)
        ) {

            is ValidationResult.Error -> result

            ValidationResult.Success -> {

                repository.addTransaction(transaction)

                ValidationResult.Success
            }
        }
    }
}