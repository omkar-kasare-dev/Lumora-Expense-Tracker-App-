package com.finance.lumora.domain.usecase.expense

import com.finance.lumora.domain.model.Expense
import com.finance.lumora.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    operator fun invoke(query: String): Flow<List<Expense>> {

        return if (query.isBlank()) {
            repository.getAllExpenses()
        } else {
            repository.searchExpenses(query.trim())
        }
    }
}