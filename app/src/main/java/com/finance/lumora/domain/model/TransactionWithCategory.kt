package com.finance.lumora.domain.model


/**
 * Domain model representing a transaction
 * together with its category.
 *
 * This model is independent of Room and is used
 * by the Dashboard and reporting features.
 */
data class TransactionWithCategory(

    val transaction: Transaction,

    val category: Category

)