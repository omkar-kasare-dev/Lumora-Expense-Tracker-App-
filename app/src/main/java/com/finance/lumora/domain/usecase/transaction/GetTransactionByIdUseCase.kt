package com.finance.lumora.domain.usecase.transaction



import javax.inject.Inject
import com.finance.lumora.domain.repository.TransactionRepository

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        id: Long
    ) = repository.getTransactionById(id)
}