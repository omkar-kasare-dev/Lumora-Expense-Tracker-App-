package com.finance.lumora.domain.usecase.export


import com.finance.lumora.domain.model.ExportTransaction
import com.finance.lumora.domain.repository.TransactionRepository

class ExportDataUseCase(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(): List<ExportTransaction> {
        return transactionRepository.getTransactionsForExport()
    }
}