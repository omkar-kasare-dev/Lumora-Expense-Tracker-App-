package com.finance.lumora.data.local.converter



import androidx.room.TypeConverter
import com.finance.lumora.data.local.enums.TransactionType

/**
 * Converts TransactionType enum
 * to String and vice versa
 * for Room Database.
 */
class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(
        type: TransactionType
    ): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(
        value: String
    ): TransactionType {
        return TransactionType.valueOf(value)
    }
}