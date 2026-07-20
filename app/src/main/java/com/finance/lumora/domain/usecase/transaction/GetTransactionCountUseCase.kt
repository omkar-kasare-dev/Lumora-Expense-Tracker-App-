package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke() =
        repository.getTransactionCount()
}