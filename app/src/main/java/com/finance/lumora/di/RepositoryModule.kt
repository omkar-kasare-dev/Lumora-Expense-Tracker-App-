package com.finance.lumora.di

/*
import com.finance.lumora.data.datastore.SettingsPreferences
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.ExpenseDao
import com.finance.lumora.data.repository.CategoryRepositoryImpl
import com.finance.lumora.data.repository.ExpenseRepositoryImpl
import com.finance.lumora.data.repository.SettingsRepositoryImpl
import com.finance.lumora.domain.repository.CategoryRepository
import com.finance.lumora.domain.repository.ExpenseRepository
import com.finance.lumora.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides repository implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides ExpenseRepository.
     */
    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao
    ): ExpenseRepository {

        return ExpenseRepositoryImpl(expenseDao)
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
     * Provides SettingsRepository.
     */
    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsPreferences: SettingsPreferences
    ): SettingsRepository {

        return SettingsRepositoryImpl(settingsPreferences)
    }
}

*/



import com.finance.lumora.data.analytics.repository.AnalyticsRepositoryImpl
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.repository.CategoryRepositoryImpl
import com.finance.lumora.data.repository.DashboardRepositoryImpl
import com.finance.lumora.data.repository.SubCategoryRepositoryImpl
import com.finance.lumora.data.repository.TransactionRepositoryImpl
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import com.finance.lumora.domain.repository.CategoryRepository
import com.finance.lumora.domain.repository.DashboardRepository
import com.finance.lumora.domain.repository.SubCategoryRepository
import com.finance.lumora.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository {

        return CategoryRepositoryImpl(
            categoryDao
        )
    }


    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {

        return TransactionRepositoryImpl(
            transactionDao
        )
    }

    /**
     * Dashboard Repository
     */
    @Provides
    @Singleton
    fun provideDashboardRepository(

        transactionDao: TransactionDao,

        categoryDao: CategoryDao


    ): DashboardRepository {

        return DashboardRepositoryImpl(

            transactionDao = transactionDao,

            categoryDao = categoryDao,

        )

    }

    @Provides
    @Singleton
    fun provideSubCategoryRepository(
        dao: SubCategoryDao
    ): SubCategoryRepository {

        return SubCategoryRepositoryImpl(dao)
    }


    // * Analytics Repository

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        transactionDao: TransactionDao
    ): AnalyticsRepository {

        return AnalyticsRepositoryImpl(
            transactionDao = transactionDao
        )
    }



}