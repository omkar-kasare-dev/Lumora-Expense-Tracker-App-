package com.finance.lumora.di

/*
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

 */

/*
import android.content.Context
import androidx.room.Room
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.database.LumoraDatabase
import com.finance.lumora.data.repository.CategoryRepositoryImpl
import com.finance.lumora.data.repository.TransactionRepositoryImpl
import com.finance.lumora.domain.repository.CategoryRepository
import com.finance.lumora.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides Room Database, DAO and Repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides Room Database instance.
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
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides CategoryDao.
     */
    @Provides
    @Singleton
    fun provideCategoryDao(
        database: LumoraDatabase
    ): CategoryDao {

        return database.categoryDao()
    }

    /**
     * Provides TransactionDao.
     */
    @Provides
    @Singleton
    fun provideTransactionDao(
        database: LumoraDatabase
    ): TransactionDao {

        return database.transactionDao()
    }

    /**
     * Provides CategoryRepository.
     */
    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository {

        return CategoryRepositoryImpl(categoryDao)
    }

    /**
     * Provides TransactionRepository.
     */
    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {

        return TransactionRepositoryImpl(transactionDao)
    }
}
 */




import android.content.Context
import androidx.room.Room
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.database.LumoraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


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
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCategoryDao(
        database: LumoraDatabase
    ): CategoryDao {

        return database.categoryDao()
    }


    @Provides
    @Singleton
    fun provideTransactionDao(
        database: LumoraDatabase
    ): TransactionDao {

        return database.transactionDao()
    }

    @Provides
    fun provideSubCategoryDao(
        database: LumoraDatabase
    ): SubCategoryDao = database.subCategoryDao()

}