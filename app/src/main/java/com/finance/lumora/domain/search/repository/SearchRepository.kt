/*
package com.finance.lumora.domain.search.repository

import com.finance.lumora.data.local.relation.TransactionWithCategory

import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchTransactions(
        query: String
    ): Flow<List<TransactionWithCategory>>

}

 */

package com.finance.lumora.domain.search.repository

import com.finance.lumora.data.local.relation.TransactionWithCategory
import com.finance.lumora.domain.search.model.SearchFilters
import kotlinx.coroutines.flow.Flow

interface SearchRepository {


    fun searchTransactions(
        query: String,
        filters: SearchFilters
    ): Flow<List<TransactionWithCategory>>


}
