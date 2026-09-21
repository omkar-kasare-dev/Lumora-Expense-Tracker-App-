package com.finance.lumora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finance.lumora.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE scope = :scope ORDER BY publishedAt DESC")
    fun observeNews(scope: String): Flow<List<NewsEntity>>

    @Query("SELECT MIN(fetchedAt) FROM news WHERE scope = :scope")
    suspend fun lastFetchTime(scope: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<NewsEntity>)

    @Query("DELETE FROM news WHERE scope = :scope")
    suspend fun clearScope(scope: String)
}