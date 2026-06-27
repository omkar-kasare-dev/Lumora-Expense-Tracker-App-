package com.finance.lumora.data.local.dao



import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lumora.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // -----------------------------
    // INSERT
    // -----------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: CategoryEntity): Long

    // -----------------------------
    // UPDATE
    // -----------------------------

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    // -----------------------------
    // DELETE
    // -----------------------------

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("""
        DELETE FROM categories
        WHERE id = :categoryId
    """)
    suspend fun deleteCategoryById(categoryId: Long)

    // -----------------------------
    // READ
    // -----------------------------

    @Query("""
        SELECT *
        FROM categories
        ORDER BY name ASC
    """)
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("""
        SELECT *
        FROM categories
        WHERE id = :categoryId
    """)
    suspend fun getCategoryById(
        categoryId: Long
    ): CategoryEntity?

    @Query("""
        SELECT *
        FROM categories
        WHERE name = :name
        LIMIT 1
    """)
    suspend fun getCategoryByName(
        name: String
    ): CategoryEntity?

    @Query("""
        SELECT COUNT(*)
        FROM categories
    """)
    suspend fun getCategoryCount(): Int
}