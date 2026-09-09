package com.finance.lumora.domain.usecase.transaction

import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.Transaction

import com.finance.lumora.domain.validation.ValidationResult
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class SaveAurixTransactionUseCase @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) {

    suspend operator fun invoke(
        resolvedDraft: ResolvedTransactionDraft
    ): Result<Unit> {

        val category = resolvedDraft.category
            ?: return Result.failure(
                IllegalArgumentException(
                    "A valid transaction category is required."
                )
            )

        val transaction = Transaction(
            amount = resolvedDraft.draft.amount,
            type = TransactionType.EXPENSE,
            categoryId = category.id,
            note = null,
            transactionDate = parseDateToMillis(
                resolvedDraft.draft.transactionDate
            )
        )

        return when (
            val result = addTransactionUseCase(transaction)
        ) {
            is ValidationResult.Error -> {
                Result.failure(
                    IllegalArgumentException(result.message)
                )
            }

            ValidationResult.Success -> {
                Result.success(Unit)
            }
        }
    }

    private fun parseDateToMillis(
        date: String
    ): Long {
        return LocalDate.parse(date)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}