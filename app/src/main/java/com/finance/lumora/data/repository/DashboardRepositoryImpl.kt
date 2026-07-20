package com.finance.lumora.data.repository

import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.domain.model.DashboardSummary

import com.finance.lumora.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import com.finance.lumora.domain.model.DashboardFinancialSummary
import com.finance.lumora.domain.model.DashboardStatistics

import com.finance.lumora.domain.model.DashboardCategorySummary
import com.finance.lumora.domain.model.TopExpenseCategory
import com.finance.lumora.data.mapper.toDomain

import com.finance.lumora.domain.model.TransactionWithCategory

/**
 * Repository implementation responsible for
 * aggregating all dashboard-related information.
 */
class DashboardRepositoryImpl @Inject constructor(

    private val transactionDao: TransactionDao,

    private val categoryDao: CategoryDao

) : DashboardRepository {

//------------------------------------------------------------------
    /**
     * Returns financial information used
     * on the Dashboard.
     */
    private fun financialSummaryFlow():
            Flow<DashboardFinancialSummary> {

        return combine(

            transactionDao.getTotalIncome(),

            transactionDao.getTotalExpense(),

            transactionDao.getMonthlyIncome(),

            transactionDao.getMonthlyExpense()

        ) {

                totalIncome,
                totalExpense,
                monthlyIncome,
                monthlyExpense ->

            DashboardFinancialSummary(

                totalIncome = totalIncome,

                totalExpense = totalExpense,

                totalBalance = totalIncome - totalExpense,

                monthlyIncome = monthlyIncome,

                monthlyExpense = monthlyExpense

            )

        }

    }
//-------------------------------------------------------------------
    /**
     * Returns dashboard statistics.
     */
    private fun statisticsFlow():
            Flow<DashboardStatistics> {

        return combine(

            transactionDao.observeTransactionCount(),

            transactionDao.getLargestIncome(),

            transactionDao.getLargestExpense()

        ) {

                transactionCount,
                largestIncome,
                largestExpense ->

            DashboardStatistics(

                transactionCount = transactionCount,

                largestIncome = largestIncome,

                largestExpense = largestExpense

            )

        }

    }
//-------------------------------------------------------------------
    /**
     * Returns category analytics used on
     * the Dashboard.
     */
    private fun categorySummaryFlow():
            Flow<DashboardCategorySummary> {

        return combine(

            transactionDao.getTopExpenseCategory(),

            categoryDao.getAllCategories()

        ) { topExpenseProjection, categories ->

            val categoryMap = categories.associateBy { it.id }

            val topExpenseCategory = topExpenseProjection?.let { projection ->

                categoryMap[projection.categoryId]
                    ?.toDomain()
                    ?.let { category ->

                        TopExpenseCategory(

                            category = category,

                            amount = projection.totalExpense

                        )

                    }

            }

            DashboardCategorySummary(

                topExpenseCategory = topExpenseCategory

            )

        }

    }
//-------------------------------------------------------------------
    /**
     * Returns the latest transactions displayed
     * on the Dashboard.
     */
    private fun recentTransactionsFlow():
            Flow<List<TransactionWithCategory>> {

        return transactionDao
            .getRecentTransactionsWithCategory()
            .combine(categoryDao.getAllCategories()) { transactions, categories ->

                val categoryMap = categories.associateBy { it.id }

                transactions.map { relation ->

                    TransactionWithCategory(

                        transaction = relation.transaction.toDomain(),

                        category = categoryMap[relation.category.id]
                            ?.toDomain()
                            ?: relation.category.toDomain()

                    )

                }

            }

    }
//-------------------------------------------------------------------

//-------------------------------------------------------------------

//-------------------------------------------------------------------

    override fun getDashboardSummary(): Flow<DashboardSummary> {

        return combine(

            financialSummaryFlow(),

            statisticsFlow(),

            categorySummaryFlow(),

            recentTransactionsFlow()

        ) {

                financial,

                statistics,

                category,

                recentTransactions ->

            DashboardSummary(

                financial = financial,

                statistics = statistics,

                category = category,

                recentTransactions = recentTransactions

            )

        }

    }

}