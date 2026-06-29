package com.finance.lumora.di


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