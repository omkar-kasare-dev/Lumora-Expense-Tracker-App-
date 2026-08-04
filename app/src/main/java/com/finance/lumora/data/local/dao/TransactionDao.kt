package com.finance.lumora.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finance.lumora.data.local.entity.TransactionEntity
import com.finance.lumora.data.local.enums.TransactionType
import kotlinx.coroutines.flow.Flow
import androidx.room.Transaction
import com.finance.lumora.data.local.projection.CategoryTotalProjection
import com.finance.lumora.data.local.projection.MonthlyIncomeExpenseProjection
import com.finance.lumora.data.local.relation.TransactionWithCategory
import com.finance.lumora.data.local.projection.TopExpenseCategoryProjection

@Dao
interface TransactionDao {
    // INSERT
    // --------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransaction(
        transaction: TransactionEntity
    ): Long

    // UPDATE
    // --------------------------------------------------

    @Update
    suspend fun updateTransaction(
        transaction: TransactionEntity
    )

    // DELETE
    // --------------------------------------------------

    @Delete
    suspend fun deleteTransaction(
        transaction: TransactionEntity
    )

    @Query("""
        DELETE FROM transactions
        WHERE id = :transactionId
    """)
    suspend fun deleteTransactionById(
        transactionId: Long
    )

    // READ : ALL
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        ORDER BY transaction_date DESC
    """)
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    // READ : BY ID
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        WHERE id = :transactionId
    """)
    suspend fun getTransactionById(
        transactionId: Long
    ): TransactionEntity?

    // READ : EXPENSES
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        WHERE type = :type
        ORDER BY transaction_date DESC
    """)
    fun getTransactionsByType(
        type: TransactionType
    ): Flow<List<TransactionEntity>>

    // READ : CATEGORY
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        WHERE category_id = :categoryId
        ORDER BY transaction_date DESC
    """)
    fun getTransactionsByCategory(
        categoryId: Long
    ): Flow<List<TransactionEntity>>

    // READ : DATE RANGE
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        WHERE transaction_date BETWEEN :startDate AND :endDate
        ORDER BY transaction_date DESC
    """)
    fun getTransactionsBetweenDates(
        startDate: Long,
        endDate: Long
    ): Flow<List<TransactionEntity>>

    // READ : CATEGORY + TYPE
    // --------------------------------------------------

    @Query("""
        SELECT *
        FROM transactions
        WHERE category_id = :categoryId
        AND type = :type
        ORDER BY transaction_date DESC
    """)
    fun getTransactionsByCategoryAndType(
        categoryId: Long,
        type: TransactionType
    ): Flow<List<TransactionEntity>>

    // READ : COUNT
    // --------------------------------------------------

    @Query("""
        SELECT COUNT(*)
        FROM transactions
    """)
    suspend fun getTransactionCount(): Int

    // READ : TOTAL INCOME
    // --------------------------------------------------

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE type = 'INCOME'
    """)
    fun getTotalIncome(): Flow<Double>

    // READ : TOTAL EXPENSE
    // --------------------------------------------------

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE type = 'EXPENSE'
    """)
    fun getTotalExpense(): Flow<Double>
//===========***************
    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    ORDER BY transaction_date DESC
""")
    fun getAllTransactionsWithCategory():
            Flow<List<TransactionWithCategory>>

    //==================
    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    WHERE id = :transactionId
""")
    suspend fun getTransactionWithCategoryById(
        transactionId: Long
    ): TransactionWithCategory?

    //=======================================
    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    WHERE type = :type
    ORDER BY transaction_date DESC
""")
    fun getTransactionsWithCategoryByType(
        type: TransactionType
    ): Flow<List<TransactionWithCategory>>


    //================

    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    WHERE category_id = :categoryId
    ORDER BY transaction_date DESC
""")
    fun getTransactionsWithCategoryByCategory(
        categoryId: Long
    ): Flow<List<TransactionWithCategory>>

    //===================

    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    WHERE transaction_date BETWEEN :startDate AND :endDate
    ORDER BY transaction_date DESC
""")
    fun getTransactionsWithCategoryBetweenDates(
        startDate: Long,
        endDate: Long
    ): Flow<List<TransactionWithCategory>>
    //=====================

    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    WHERE category_id = :categoryId
      AND type = :type
    ORDER BY transaction_date DESC
""")
    fun getTransactionsWithCategoryByCategoryAndType(
        categoryId: Long,
        type: TransactionType
    ): Flow<List<TransactionWithCategory>>

    //=====================================================================

// DASHBOARD : RECENT TRANSACTIONS
// --------------------------------------------------

    /**
     * Returns the latest transactions for the Dashboard.
     */
    @Query("""
    SELECT *
    FROM transactions
    ORDER BY transaction_date DESC
    LIMIT 10
""")
    fun getRecentTransactions(): Flow<List<TransactionEntity>>

//-----------------------------------------------------------------------------------------
// DASHBOARD : RECENT TRANSACTIONS WITH CATEGORY
// --------------------------------------------------

    @Transaction
    @Query("""
    SELECT *
    FROM transactions
    ORDER BY transaction_date DESC
    LIMIT 10
""")
    fun getRecentTransactionsWithCategory():
            Flow<List<TransactionWithCategory>>

// DASHBOARD : OBSERVE TRANSACTION COUNT
// --------------------------------------------------

    /**
     * Observes the total number of transactions.
     *
     * Unlike getTransactionCount(), this method automatically
     * emits a new value whenever the transactions table changes.
     */
    @Query("""
    SELECT COUNT(*)
    FROM transactions
""")
    fun observeTransactionCount(): Flow<Int>

//----------------------------------------------------------------------------------------------

// DASHBOARD : LARGEST INCOME
// --------------------------------------------------

    @Query("""
    SELECT COALESCE(MAX(amount), 0)
    FROM transactions
    WHERE type = 'INCOME'
""")
    fun getLargestIncome(): Flow<Double>
//-------------------------------------------------------------------------------

// DASHBOARD : LARGEST EXPENSE
// --------------------------------------------------

    @Query("""
    SELECT COALESCE(MAX(amount), 0)
    FROM transactions
    WHERE type = 'EXPENSE'
""")
    fun getLargestExpense(): Flow<Double>

//-------------------------------------------------------------------------------

// DASHBOARD : MONTHLY INCOME
// --------------------------------------------------

    @Query("""
    SELECT COALESCE(SUM(amount),0)
    FROM transactions
    WHERE type = 'INCOME'
      AND strftime('%Y-%m', transaction_date / 1000, 'unixepoch')
          = strftime('%Y-%m','now')
""")
    fun getMonthlyIncome(): Flow<Double>

//-------------------------------------------------------------------------------

// DASHBOARD : MONTHLY EXPENSE
// --------------------------------------------------

    @Query("""
    SELECT COALESCE(SUM(amount),0)
    FROM transactions
    WHERE type = 'EXPENSE'
      AND strftime('%Y-%m', transaction_date / 1000, 'unixepoch')
          = strftime('%Y-%m','now')
""")
    fun getMonthlyExpense(): Flow<Double>

//-----------------------------------------------------------------------------------
// DASHBOARD : TOP EXPENSE CATEGORY
// --------------------------------------------------

    @Query("""
    SELECT
        category_id AS categoryId,
        SUM(amount) AS totalExpense
    FROM transactions
    WHERE type = 'EXPENSE'
    GROUP BY category_id
    ORDER BY totalExpense DESC
    LIMIT 1
""")
    fun getTopExpenseCategory():
            Flow<TopExpenseCategoryProjection?>

    //---------------------------------------------------------------------
    /*
      ** Analytics Dao section Start

     */
    @Query(
        """
    SELECT COALESCE(SUM(amount), 0)
    FROM transactions
    WHERE type = :incomeType
      AND transaction_date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalIncome(
        incomeType: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Double>

    //---------------------------------------------------------------
    @Query(
        """
    SELECT COALESCE(SUM(amount), 0)
    FROM transactions
    WHERE type = :expenseType
      AND transaction_date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalExpense(
        expenseType: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Double>

    //------------------------------------------------------------------
    @Query(
        """
    SELECT COUNT(*)
    FROM transactions
    WHERE transaction_date BETWEEN :startDate AND :endDate
    """
    )
    fun getTransactionCount(
        startDate: Long,
        endDate: Long
    ): Flow<Int>

    //-----------------------------------------------------------------------
    @Query(
        """
    SELECT
        c.id AS categoryId,
        c.name AS categoryName,
        c.icon AS icon,
        c.color AS color,
        COALESCE(SUM(t.amount), 0) AS totalAmount
    FROM transactions t
    INNER JOIN categories c
        ON t.category_id = c.id
    WHERE t.type = :type
      AND t.transaction_date BETWEEN :startDate AND :endDate
    GROUP BY c.id, c.name ,c.icon,c.color
    ORDER BY totalAmount DESC
    """
    )
    fun getCategoryTotals(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryTotalProjection>>

    //--------------------------------------------------------------------------

    @Query(
        """
    SELECT
        CAST(strftime('%Y', transaction_date / 1000, 'unixepoch') AS INTEGER) AS year,
        CAST(strftime('%m', transaction_date / 1000, 'unixepoch') AS INTEGER) AS month,

        COALESCE(
            SUM(
                CASE
                    WHEN type = 'INCOME'
                    THEN amount
                    ELSE 0
                END
            ),
            0
        ) AS income,

        COALESCE(
            SUM(
                CASE
                    WHEN type = 'EXPENSE'
                    THEN amount
                    ELSE 0
                END
            ),
            0
        ) AS expense

    FROM transactions

    WHERE transaction_date BETWEEN :startDate
                              AND :endDate

    GROUP BY year, month

    ORDER BY year, month
    """
    )
    fun getMonthlyIncomeExpense(
        startDate: Long,
        endDate: Long
    ): Flow<List<MonthlyIncomeExpenseProjection>>

    //------------------------------------------------------------------------

    //---------------------------------------------------------------------
    /*
      * Analytics Dao section END:
     */



}