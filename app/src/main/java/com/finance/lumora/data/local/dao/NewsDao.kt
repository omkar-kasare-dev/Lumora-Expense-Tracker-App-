package com.finance.lumora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.finance.lumora.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

/**
 * An abstract class (not an interface) so @Transaction can wrap a Kotlin method body.
 * NewsEntity and LumoraDatabase are unchanged: no version bump and no migration needed.
 */
@Dao
abstract class NewsDao {

    @Query("SELECT * FROM news WHERE scope = :scope ORDER BY publishedAt DESC LIMIT :limit")
    abstract fun observeNews(scope: String, limit: Int): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE scope = :scope ORDER BY publishedAt DESC LIMIT :limit")
    abstract suspend fun getNews(scope: String, limit: Int): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<NewsEntity>)

    /** Keeps only the newest [keep] rows of a scope. */
    @Query(
        "DELETE FROM news WHERE scope = :scope AND id NOT IN " +
                "(SELECT id FROM news WHERE scope = :scope ORDER BY publishedAt DESC LIMIT :keep)"
    )
    abstract suspend fun pruneToNewest(scope: String, keep: Int)

    /** Rows written by the old code have ids without the "SCOPE|" prefix. */
    @Query("DELETE FROM news WHERE id NOT LIKE '%|%'")
    abstract suspend fun deleteLegacyRows()

    @Query("DELETE FROM news WHERE scope = :scope")
    abstract suspend fun clearScope(scope: String)

    @Query("SELECT MIN(fetchedAt) FROM news WHERE scope = :scope")
    abstract suspend fun lastFetchTime(scope: String): Long?

    /**
     * Adds new articles and trims old ones in ONE transaction, so observers never see an
     * empty list in between and a failed insert never wipes the existing cache.
     */
    @Transaction
    open suspend fun upsertAndPrune(scope: String, items: List<NewsEntity>, keep: Int) {
        insertAll(items)
        pruneToNewest(scope, keep)
    }
}