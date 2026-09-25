package com.finance.lumora.data.local.prefs

import android.content.Context
import com.finance.lumora.domain.model.NewsScope
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Remembers WHEN each feed was last requested and last succeeded.
 *
 * Kept separate from the articles table on purpose: an empty or failed response leaves
 * no rows behind, so row timestamps can't tell us that we already tried. Without this,
 * every screen open on a fresh install would retry the network (and burn quota).
 *
 * Hilt provides this automatically (@Inject constructor + @ApplicationContext); no
 * module changes are needed. It is called from Dispatchers.IO by the repository.
 */
@Singleton
class NewsFetchMetaStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("news_fetch_meta", Context.MODE_PRIVATE)

    fun lastAttempt(scope: NewsScope): Long = prefs.getLong("attempt_${scope.name}", 0L)

    fun lastSuccess(scope: NewsScope): Long = prefs.getLong("success_${scope.name}", 0L)

    fun recordAttempt(scope: NewsScope, at: Long) {
        prefs.edit().putLong("attempt_${scope.name}", at).apply()
    }

    fun recordSuccess(scope: NewsScope, at: Long) {
        prefs.edit().putLong("success_${scope.name}", at).apply()
    }
}