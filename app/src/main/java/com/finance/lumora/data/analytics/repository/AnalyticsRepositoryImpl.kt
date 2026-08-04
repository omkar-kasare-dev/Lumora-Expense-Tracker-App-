package com.finance.lumora.data.analytics.repository

import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.analytics.model.CategorySummary
import com.finance.lumora.domain.analytics.model.DateRange
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import com.finance.lumora.domain.analytics.model.MonthlySummary
import com.finance.lumora.domain.analytics.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AnalyticsRepositoryImpl @Inject constructor(

    private val transactionDao: TransactionDao

) : AnalyticsRepository {



    override fun getMonthlySummary(
        dateRange: DateRange
    ): Flow<MonthlySummary> {


        return combine(

            transactionDao.getTotalIncome(

                TransactionType.INCOME,

                dateRange.startDate,

                dateRange.endDate

            ),


            transactionDao.getTotalExpense(

                TransactionType.EXPENSE,

                dateRange.startDate,

                dateRange.endDate

            ),


            transactionDao.getTransactionCount(

                dateRange.startDate,

                dateRange.endDate

            )


        ) { income, expense, count ->


            MonthlySummary(

                totalIncome = income,

                totalExpense = expense,

                balance = income - expense,

                transactionCount = count

            )

        }

    }




    override fun getCategorySummary(
        dateRange: DateRange
    ): Flow<List<CategorySummary>> {


        return transactionDao

            .getCategoryTotals(

                TransactionType.EXPENSE,

                dateRange.startDate,

                dateRange.endDate

            )


            .map { categories ->


                val totalExpense =

                    categories.sumOf {

                        it.totalAmount

                    }



                categories.map { item ->


                    CategorySummary(

                        categoryId =
                            item.categoryId,


                        categoryName =
                            item.categoryName,


                        icon =
                            item.icon,


                        color =
                            item.color,


                        totalAmount =
                            item.totalAmount,


                        percentage =

                            if(totalExpense == 0.0)

                                0f

                            else

                                (

                                        item.totalAmount /
                                                totalExpense

                                        )
                                    .toFloat() * 100f

                    )

                }

            }

    }




    override fun getIncomeExpenseSummary(
        dateRange: DateRange
    ): Flow<List<IncomeExpenseSummary>> {


        return transactionDao

            .getMonthlyIncomeExpense(

                dateRange.startDate,

                dateRange.endDate

            )


            .map { result ->


                result.map {


                    IncomeExpenseSummary(

                        year =
                            it.year,


                        month =
                            it.month,


                        income =
                            it.income,


                        expense =
                            it.expense

                    )


                }


            }

    }
}