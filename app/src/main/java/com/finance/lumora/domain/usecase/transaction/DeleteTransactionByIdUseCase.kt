package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        id: Long
    ) {
        repository.deleteTransactionById(id)
    }
}