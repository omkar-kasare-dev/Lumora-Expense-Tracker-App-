package com.finance.lumora.data.local.database
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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finance.lumora.data.local.converter.NotificationTypeConverter
import com.finance.lumora.data.local.converter.TransactionTypeConverter
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.NewsDao
import com.finance.lumora.data.local.dao.NotificationDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.NewsEntity
import com.finance.lumora.data.local.entity.NotificationEntity
import com.finance.lumora.data.local.entity.SubCategoryEntity
import com.finance.lumora.data.local.entity.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class,
        SubCategoryEntity::class,
        NewsEntity::class,
        NotificationEntity::class
    ],
    version = 3,
    exportSchema = true
)



@TypeConverters(
    TransactionTypeConverter::class,
    NotificationTypeConverter::class
)
abstract class LumoraDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao

    abstract fun subCategoryDao(): SubCategoryDao
    abstract fun newsDao(): NewsDao
    abstract fun notificationDao(): NotificationDao

}