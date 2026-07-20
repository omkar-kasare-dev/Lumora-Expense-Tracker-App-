package com.finance.lumora.domain.usecase.transaction


import com.finance.lumora.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsBetweenDatesUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(
        startDate: Long,
        endDate: Long
    ) = repository.getTransactionsBetweenDates(
        startDate,
        endDate
    )
}