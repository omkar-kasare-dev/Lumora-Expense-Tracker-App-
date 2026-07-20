package com.finance.lumora.data.local.enums

/**
 * Represents the type of financial transaction.
 *
 * A transaction can either increase the user's balance
 * (INCOME) or decrease it (EXPENSE).
 *
 * This enum is stored in the Room database using a TypeConverter.
 */
enum class TransactionType {

    /**
     * Money received.
     */
    INCOME,

    /**
     * Money spent.
     */
    EXPENSE
}