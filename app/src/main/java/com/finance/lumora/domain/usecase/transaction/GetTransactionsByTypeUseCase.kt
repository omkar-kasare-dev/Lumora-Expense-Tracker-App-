package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsByTypeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(
        type: TransactionType
    ) = repository.getTransactionsByType(type)
}