package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        transaction: Transaction
    ) {
        repository.deleteTransaction(transaction)
    }
}