package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke() =
        repository.getAllTransactions()
}