package com.finance.lumora.data.local.database
/*
import androidx.room.Database
import androidx.room.RoomDatabase
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.ExpenseDao
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.ExpenseEntity

@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class
    ],
    version = 1,
    exportSchema = true
)
/*
@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class
    ],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = true
)

 */
abstract class LumoraDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun categoryDao(): CategoryDao
}

 */



import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finance.lumora.data.local.converter.TransactionTypeConverter
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class
    ],
    version = 2,
    exportSchema = true
)

/*
@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class
    ],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = true
)
*/

@TypeConverters(
    TransactionTypeConverter::class
)
abstract class LumoraDatabase : RoomDatabase() {

    /**
     * Category DAO
     */
    abstract fun categoryDao(): CategoryDao

    /**
     * Transaction DAO
     */
    abstract fun transactionDao(): TransactionDao
}