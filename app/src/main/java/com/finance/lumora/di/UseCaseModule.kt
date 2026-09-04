package com.finance.lumora.di

import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import com.finance.lumora.domain.analytics.usecase.AnalyticsUseCases
import com.finance.lumora.domain.analytics.usecase.GetCategorySummaryUseCase
import com.finance.lumora.domain.analytics.usecase.GetIncomeExpenseSummaryUseCase
import com.finance.lumora.domain.analytics.usecase.GetMonthlySummaryUseCase
import com.finance.lumora.domain.repository.DashboardRepository
import com.finance.lumora.domain.repository.SubCategoryRepository
import com.finance.lumora.domain.repository.TransactionRepository
import com.finance.lumora.domain.usecase.category.AddCategoryUseCase
import com.finance.lumora.domain.usecase.category.CategoryUseCases
import com.finance.lumora.domain.usecase.category.DeleteCategoryUseCase
import com.finance.lumora.domain.usecase.category.GetCategoriesUseCase
import com.finance.lumora.domain.usecase.category.UpdateCategoryUseCase
import com.finance.lumora.domain.usecase.dashboard.DashboardUseCases
import com.finance.lumora.domain.usecase.dashboard.GetDashboardSummaryUseCase
import com.finance.lumora.domain.usecase.subcategory.AddSubCategoryUseCase
import com.finance.lumora.domain.usecase.subcategory.GetSubCategoriesUseCase
import com.finance.lumora.domain.usecase.subcategory.SubCategoryUseCases
import com.finance.lumora.domain.usecase.transaction.AddTransactionUseCase
import com.finance.lumora.domain.usecase.transaction.DeleteTransactionByIdUseCase
import com.finance.lumora.domain.usecase.transaction.DeleteTransactionUseCase
import com.finance.lumora.domain.usecase.transaction.GetAllTransactionsUseCase
import com.finance.lumora.domain.usecase.transaction.GetTotalExpenseUseCase
import com.finance.lumora.domain.usecase.transaction.GetTotalIncomeUseCase
import com.finance.lumora.domain.usecase.transaction.GetTransactionByIdUseCase
import com.finance.lumora.domain.usecase.transaction.GetTransactionCountUseCase
import com.finance.lumora.domain.usecase.transaction.GetTransactionsBetweenDatesUseCase
import com.finance.lumora.domain.usecase.transaction.GetTransactionsByCategoryUseCase
import com.finance.lumora.domain.usecase.transaction.GetTransactionsByTypeUseCase
import com.finance.lumora.domain.usecase.transaction.TransactionUseCases
import com.finance.lumora.domain.usecase.transaction.UpdateTransactionUseCase
// Setting Imports:
import com.finance.lumora.domain.usecase.settings.GetBiometricUseCase
import com.finance.lumora.domain.usecase.settings.GetBudgetAlertsUseCase
import com.finance.lumora.domain.usecase.settings.GetBudgetUseCase
import com.finance.lumora.domain.usecase.settings.GetCurrencyUseCase
import com.finance.lumora.domain.usecase.settings.GetNotificationsUseCase
import com.finance.lumora.domain.usecase.settings.GetThemeUseCase
import com.finance.lumora.domain.usecase.settings.SaveBiometricUseCase
import com.finance.lumora.domain.usecase.settings.SaveBudgetAlertsUseCase
import com.finance.lumora.domain.usecase.settings.SaveBudgetUseCase
import com.finance.lumora.domain.usecase.settings.SaveCurrencyUseCase
import com.finance.lumora.domain.usecase.settings.SaveNotificationsUseCase
import com.finance.lumora.domain.usecase.settings.SaveThemeUseCase
import com.finance.lumora.domain.usecase.settings.SettingsUseCases
import com.finance.lumora.domain.usecase.transaction.GetMonthlyExpenseUseCase

// Export usecase:
import com.finance.lumora.domain.usecase.export.ExportDataUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Provides Category UseCases.
     */
    @Provides
    @Singleton
    fun provideCategoryUseCases(
        addCategory: AddCategoryUseCase,
        updateCategory: UpdateCategoryUseCase,
        deleteCategory: DeleteCategoryUseCase,
        getCategories: GetCategoriesUseCase
    ): CategoryUseCases {

        return CategoryUseCases(
            addCategory = addCategory,
            updateCategory = updateCategory,
            deleteCategory = deleteCategory,
            getCategories = getCategories
        )
    }

    /**
     * Provides Transaction UseCases.
     */
    @Provides
    @Singleton
    fun provideTransactionUseCases(
        addTransaction: AddTransactionUseCase,
        updateTransaction: UpdateTransactionUseCase,
        deleteTransaction: DeleteTransactionUseCase,
        deleteTransactionById: DeleteTransactionByIdUseCase,
        getAllTransactions: GetAllTransactionsUseCase,
        getTransactionById: GetTransactionByIdUseCase,
        getTransactionsByType: GetTransactionsByTypeUseCase,
        getTransactionsByCategory: GetTransactionsByCategoryUseCase,
        getTransactionsBetweenDates: GetTransactionsBetweenDatesUseCase,
        getTotalIncome: GetTotalIncomeUseCase,
        getTotalExpense: GetTotalExpenseUseCase,
        getTransactionCount: GetTransactionCountUseCase,
        getMonthlyExpense: GetMonthlyExpenseUseCase
    ): TransactionUseCases {

        return TransactionUseCases(
            addTransaction = addTransaction,
            updateTransaction = updateTransaction,
            deleteTransaction = deleteTransaction,
            deleteTransactionById = deleteTransactionById,
            getAllTransactions = getAllTransactions,
            getTransactionById = getTransactionById,
            getTransactionsByType = getTransactionsByType,
            getTransactionsByCategory = getTransactionsByCategory,
            getTransactionsBetweenDates = getTransactionsBetweenDates,
            getTotalIncome = getTotalIncome,
            getTotalExpense = getTotalExpense,
            getTransactionCount = getTransactionCount,
            getMonthlyExpense= getMonthlyExpense
        )
    }

    //--------------------------------------
    @Provides
    @Singleton
    fun provideDashboardUseCases(

        repository: DashboardRepository

    ): DashboardUseCases {

        return DashboardUseCases(

            getDashboardSummary = GetDashboardSummaryUseCase(
                repository
            )

        )

    }

    @Provides
    @Singleton
    fun provideSubCategoryUseCases(
        repository: SubCategoryRepository
    ): SubCategoryUseCases {

        return SubCategoryUseCases(
            addSubCategory = AddSubCategoryUseCase(repository),
            getSubCategories = GetSubCategoriesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideAnalyticsUseCases(
        repository: AnalyticsRepository
    ): AnalyticsUseCases {

        return AnalyticsUseCases(
            getMonthlySummary = GetMonthlySummaryUseCase(repository),
            getCategorySummary = GetCategorySummaryUseCase(repository),
            getIncomeExpenseSummary = GetIncomeExpenseSummaryUseCase(repository)
        )
    }

    // Settings Usecases:

    /**
     * Provides Settings UseCases.
     */
    @Provides
    @Singleton
    fun provideSettingsUseCases(
        getBudget: GetBudgetUseCase,
        saveBudget: SaveBudgetUseCase,

        getTheme: GetThemeUseCase,
        saveTheme: SaveThemeUseCase,

        getCurrency: GetCurrencyUseCase,
        saveCurrency: SaveCurrencyUseCase,

        getNotifications: GetNotificationsUseCase,
        saveNotifications: SaveNotificationsUseCase,

        getBudgetAlerts: GetBudgetAlertsUseCase,
        saveBudgetAlerts: SaveBudgetAlertsUseCase,

        getBiometric: GetBiometricUseCase,
        saveBiometric: SaveBiometricUseCase
    ): SettingsUseCases {

        return SettingsUseCases(
            getBudget = getBudget,
            saveBudget = saveBudget,

            getTheme = getTheme,
            saveTheme = saveTheme,

            getCurrency = getCurrency,
            saveCurrency = saveCurrency,

            getNotifications = getNotifications,
            saveNotifications = saveNotifications,

            getBudgetAlerts = getBudgetAlerts,
            saveBudgetAlerts = saveBudgetAlerts,

            getBiometric = getBiometric,
            saveBiometric = saveBiometric
        )
    }

    @Provides
    fun provideExportDataUseCase(
        transactionRepository: TransactionRepository
    ): ExportDataUseCase {
        return ExportDataUseCase(
            transactionRepository
        )
    }
}