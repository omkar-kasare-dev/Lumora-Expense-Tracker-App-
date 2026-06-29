package com.finance.lumora.di


import android.content.Context
import androidx.room.Room
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.ExpenseDao
import com.finance.lumora.data.local.database.LumoraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides Room database and DAO dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the Room database instance.
     */
    @Provides
    @Singleton
    fun provideLumoraDatabase(
        @ApplicationContext context: Context
    ): LumoraDatabase {

        return Room.databaseBuilder(
            context,
            LumoraDatabase::class.java,
            "lumora_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    /**
     * Provides ExpenseDao.
     */
    @Provides
    fun provideExpenseDao(
        database: LumoraDatabase
    ): ExpenseDao {

        return database.expenseDao()
    }

    /**
     * Provides CategoryDao.
     */
    @Provides
    fun provideCategoryDao(
        database: LumoraDatabase
    ): CategoryDao {

        return database.categoryDao()
    }
}