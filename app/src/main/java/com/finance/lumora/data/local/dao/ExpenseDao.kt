package com.finance.lumora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.finance.lumora.data.local.entity.ExpenseEntity
import com.finance.lumora.data.local.relation.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // ----------------------------------------------------------------
    // INSERT
    // ----------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query(
        """
        DELETE FROM expenses
        WHERE id = :expenseId
        """
    )
    suspend fun deleteExpenseById(expenseId: Long)

    // ----------------------------------------------------------------
    // READ
    // ----------------------------------------------------------------

    @Transaction
    @Query(
        """
        SELECT *
        FROM expenses
        ORDER BY date DESC
        """
    )
    fun getAllExpenses(): Flow<List<ExpenseWithCategory>>

    @Transaction
    @Query(
        """
        SELECT *
        FROM expenses
        WHERE id = :expenseId
        """
    )
    suspend fun getExpenseById(
        expenseId: Long
    ): ExpenseWithCategory?

    @Transaction
    @Query(
        """
        SELECT *
        FROM expenses
        WHERE category_id = :categoryId
        ORDER BY date DESC
        """
    )
    fun getExpensesByCategory(
        categoryId: Long
    ): Flow<List<ExpenseWithCategory>>

    // ----------------------------------------------------------------
    // SEARCH
    // ----------------------------------------------------------------

    @Transaction
    @Query(
        """
        SELECT *
        FROM expenses
        WHERE title LIKE '%' || :query || '%'
           OR note LIKE '%' || :query || '%'
        ORDER BY date DESC
        """
    )
    fun searchExpenses(
        query: String
    ): Flow<List<ExpenseWithCategory>>

    // ----------------------------------------------------------------
    // DASHBOARD
    // ----------------------------------------------------------------

    @Query(
        """
        SELECT IFNULL(SUM(amount), 0)
        FROM expenses
        """
    )
    suspend fun getTotalExpense(): Double

    @Query(
        """
        SELECT IFNULL(SUM(amount), 0)
        FROM expenses
        WHERE date BETWEEN :startDate AND :endDate
        """
    )
    suspend fun getExpenseBetweenDates(
        startDate: Long,
        endDate: Long
    ): Double

    @Query(
        """
        SELECT COUNT(*)
        FROM expenses
        """
    )
    suspend fun getExpenseCount(): Int

    @Transaction
    @Query(
        """
        SELECT *
        FROM expenses
        ORDER BY date DESC
        LIMIT 5
        """
    )
    fun getRecentExpenses(): Flow<List<ExpenseWithCategory>>
}