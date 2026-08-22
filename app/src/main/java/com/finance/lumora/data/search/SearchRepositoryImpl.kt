package com.finance.lumora.data.search

import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.relation.TransactionWithCategory
import com.finance.lumora.domain.search.model.SearchFilters
import com.finance.lumora.domain.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : SearchRepository {


    override fun searchTransactions(
        query: String,
        filters: SearchFilters
    ): Flow<List<TransactionWithCategory>> {

        return transactionDao.searchTransactions(

            query = query,

            transactionType = filters.transactionType,

            categoryId = filters.categoryId,

            startDate = filters.startDate,

            endDate = filters.endDate,

            minAmount = filters.minAmount,

            maxAmount = filters.maxAmount

        )
    }


}
