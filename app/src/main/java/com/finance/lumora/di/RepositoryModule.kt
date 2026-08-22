package com.finance.lumora.di

import com.finance.lumora.data.analytics.repository.AnalyticsRepositoryImpl
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.repository.CategoryRepositoryImpl
import com.finance.lumora.data.repository.DashboardRepositoryImpl
import com.finance.lumora.data.repository.SubCategoryRepositoryImpl
import com.finance.lumora.data.repository.TransactionRepositoryImpl
import com.finance.lumora.data.search.SearchRepositoryImpl
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import com.finance.lumora.domain.repository.CategoryRepository
import com.finance.lumora.domain.repository.DashboardRepository
import com.finance.lumora.domain.repository.SubCategoryRepository
import com.finance.lumora.domain.repository.TransactionRepository
import com.finance.lumora.domain.search.repository.SearchRepository
import com.finance.lumora.data.repository.SettingsRepositoryImpl
import com.finance.lumora.domain.repository.SettingsRepository
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

    /**
     * Search Repository
     */
    @Provides
    @Singleton
    fun provideSearchRepository(
        transactionDao: TransactionDao
    ): SearchRepository {

        return SearchRepositoryImpl(
            transactionDao = transactionDao
        )
    }

    //Setting Repository:
    /**
     * Settings Repository
     */
    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository {

        return settingsRepositoryImpl
    }





}