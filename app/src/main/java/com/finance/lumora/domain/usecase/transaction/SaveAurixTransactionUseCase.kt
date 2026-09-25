package com.finance.lumora.domain.usecase.transaction
/*
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

 */



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
    ): Result<Transaction> {

        val category = resolvedDraft.category
            ?: return Result.failure(
                IllegalArgumentException(
                    "A valid transaction category is required."
                )
            )

        val transaction = Transaction(
            amount = resolvedDraft.draft.amount,

            // NOTE: Aurix capture (voice + receipt OCR) currently has
            // no way to detect income vs. expense intent - DraftTransaction
            // has no `type` field, so every Aurix-captured transaction is
            // saved as EXPENSE. This is a known scope limitation, not a
            // bug: extending TransactionCaptureParser/DraftTransaction to
            // detect income phrasing (e.g. "salary", "received", "credited")
            // would be a separate feature, not a fix to this use case.
            type = TransactionType.EXPENSE,

            categoryId = category.id,

            // Preserve the merchant/vendor name the parser extracted
            // (e.g. "Starbucks", "Uber") as the transaction note, instead
            // of discarding it - this was previously hardcoded to null.
            note = resolvedDraft.draft.merchantName,

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
                // Return the saved transaction so callers (e.g.
                // AurixPersistenceViewModel) can trigger the same
                // in-app notification / budget-alert pipeline that
                // manually-entered transactions already use.
                Result.success(transaction)
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