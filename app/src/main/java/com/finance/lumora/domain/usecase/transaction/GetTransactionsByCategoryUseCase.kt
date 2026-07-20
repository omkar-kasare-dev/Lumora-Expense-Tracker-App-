package com.finance.lumora.domain.usecase.transaction



import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(
        categoryId: Long
    ) = repository.getTransactionsByCategory(categoryId)
}