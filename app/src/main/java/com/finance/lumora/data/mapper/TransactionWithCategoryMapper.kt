package com.finance.lumora.data.mapper



import com.finance.lumora.data.local.relation.TransactionWithCategory
import com.finance.lumora.domain.model.TransactionWithCategory as DomainTransactionWithCategory

/**
 * Converts Room relation into Domain model.
 */
fun TransactionWithCategory.toDomain(): DomainTransactionWithCategory {

    return DomainTransactionWithCategory(

        transaction = transaction.toDomain(),

        category = category.toDomain()

    )
}

/**
 * Converts a list of Room relations
 * into Domain models.
 */
fun List<TransactionWithCategory>.toDomainList():
        List<DomainTransactionWithCategory> {

    return map {
        it.toDomain()
    }
}