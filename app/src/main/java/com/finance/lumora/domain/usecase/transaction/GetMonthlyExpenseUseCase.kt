package com.finance.lumora.domain.usecase.transaction

import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

// GetMonthlyExpenseUseCase.kt
class GetMonthlyExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Double> {
        val (startOfMonth, endOfMonth) = getMonthRange()

        return repository
            .getTransactionsBetweenDates(startOfMonth, endOfMonth)
            .map { transactions ->
                transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }
            }
    }

    private fun getMonthRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val start = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val end = calendar.timeInMillis

        return Pair(start, end)
    }
}