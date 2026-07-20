package com.finance.lumora.data.mapper



import com.finance.lumora.data.local.entity.TransactionEntity
import com.finance.lumora.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction {

    return Transaction(

        id = id,

        amount = amount,

        type = type,

        categoryId = categoryId,

        note = note,

        transactionDate = transactionDate,

        createdAt = createdAt,

        updatedAt = updatedAt
    )
}

fun Transaction.toEntity(): TransactionEntity {

    return TransactionEntity(

        id = id,

        amount = amount,

        type = type,

        categoryId = categoryId,

        note = note,

        transactionDate = transactionDate,

        createdAt = createdAt,

        updatedAt = updatedAt
    )
}

fun List<TransactionEntity>.toDomainList(): List<Transaction> {

    return map {
        it.toDomain()
    }
}