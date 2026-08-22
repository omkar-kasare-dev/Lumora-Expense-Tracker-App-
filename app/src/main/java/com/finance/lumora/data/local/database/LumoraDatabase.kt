package com.finance.lumora.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finance.lumora.data.local.converter.TransactionTypeConverter
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.SubCategoryEntity
import com.finance.lumora.data.local.entity.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class,
        SubCategoryEntity::class
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

    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao

    abstract fun subCategoryDao(): SubCategoryDao




}