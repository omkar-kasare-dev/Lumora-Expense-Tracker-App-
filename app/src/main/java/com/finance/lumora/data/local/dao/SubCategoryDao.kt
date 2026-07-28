package com.finance.lumora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finance.lumora.data.local.entity.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSubCategory(
        subCategory: SubCategoryEntity
    )

    @Query("""
        SELECT *
        FROM sub_categories
        WHERE categoryId = :categoryId
        ORDER BY name ASC
    """)
    fun getSubCategoriesByCategoryId(
        categoryId: Long
    ): Flow<List<SubCategoryEntity>>

    @Query("""
        SELECT *
        FROM sub_categories
        WHERE categoryId = :categoryId
        AND LOWER(name) = LOWER(:name)
        LIMIT 1
    """)
    suspend fun getSubCategoryByName(
        categoryId: Long,
        name: String
    ): SubCategoryEntity?
}