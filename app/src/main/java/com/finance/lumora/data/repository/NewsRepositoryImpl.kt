package com.finance.lumora.data.repository

import com.finance.lumora.data.api.FinnhubApiService
import com.finance.lumora.data.api.MarketauxApiService
import com.finance.lumora.data.local.dao.NewsDao
import com.finance.lumora.data.local.entity.NewsEntity
import com.finance.lumora.data.local.prefs.NewsFetchMetaStore
import com.finance.lumora.data.mapper.toNewsArticle
import com.finance.lumora.data.mapper.toNewsEntity
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import com.finance.lumora.domain.model.Result
import com.finance.lumora.domain.repository.NewsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * @property ttlMs    how old cached data may get before an automatic refresh
 * @property minGapMs minimum time between two network attempts (also applies to the
 *                    refresh button and to failed attempts, so nothing hammers the API)
 * @property keep     how many articles to keep per scope
 */
private data class NewsPolicy(val ttlMs: Long, val minGapMs: Long, val keep: Int)

// Finnhub free tier allows 60 calls/minute, so GLOBAL can refresh often.
private val GLOBAL_POLICY = NewsPolicy(
    ttlMs = TimeUnit.MINUTES.toMillis(15),
    minGapMs = TimeUnit.SECONDS.toMillis(30),
    keep = 40
)

// Marketaux's free plan is roughly 100 requests per DAY (shared by every install that
// uses this key), so LOCAL is refreshed sparingly.
private val LOCAL_POLICY = NewsPolicy(
    ttlMs = TimeUnit.MINUTES.toMillis(60),
    minGapMs = TimeUnit.MINUTES.toMillis(5),
    keep = 30
)

// Marketaux filters by the exchange country of entities mentioned in an article.
private const val LOCAL_COUNTRY = "in"

class NewsRepositoryImpl @Inject constructor(
    private val marketaux: MarketauxApiService,
    private val finnhub: FinnhubApiService,
    private val dao: NewsDao,
    private val meta: NewsFetchMetaStore,
    @Named("marketauxApiKey") private val marketauxKey: String,
    @Named("finnhubApiKey") private val finnhubKey: String
) : NewsRepository {

    /**
     * Emission order:
     *  1. cache is empty or a refresh was requested -> Loading, otherwise -> Success(cache)
     *  2. network refresh if allowed; a failure emits Error (cached data stays visible)
     *  3. live Success updates straight from Room
     */
    override fun getFinanceNews(
        scope: NewsScope,
        forceRefresh: Boolean
    ): Flow<Result<List<NewsArticle>>> = channelFlow<Result<List<NewsArticle>>> {
        val key = scope.name
        val policy = policyFor(scope)

        dao.deleteLegacyRows()
        val cached = dao.getNews(key, policy.keep)

        if (cached.isEmpty() || forceRefresh) {
            send(Result.Loading)
        } else {
            send(Result.Success(cached.map { it.toNewsArticle() }))
        }

        if (shouldRefresh(scope, policy, forceRefresh, cached.isEmpty())) {
            val failure = refresh(scope, policy)
            if (failure != null) send(Result.Error(failure))
        }

        dao.observeNews(key, policy.keep).collect { rows ->
            send(Result.Success(rows.map { it.toNewsArticle() }))
        }
    }.flowOn(Dispatchers.IO)

    private fun policyFor(scope: NewsScope): NewsPolicy = when (scope) {
        NewsScope.GLOBAL -> GLOBAL_POLICY
        NewsScope.LOCAL -> LOCAL_POLICY
    }

    private fun shouldRefresh(
        scope: NewsScope,
        policy: NewsPolicy,
        forceRefresh: Boolean,
        cacheEmpty: Boolean
    ): Boolean {
        val now = System.currentTimeMillis()

        // Respect the minimum gap (a negative value means the clock moved back: allow).
        val sinceAttempt = now - meta.lastAttempt(scope)
        if (sinceAttempt in 0 until policy.minGapMs) return false

        if (forceRefresh || cacheEmpty) return true

        val age = now - meta.lastSuccess(scope)
        return age < 0 || age > policy.ttlMs
    }

    /** Returns a user-facing error message, or null on success. */
    private suspend fun refresh(scope: NewsScope, policy: NewsPolicy): String? {
        val apiKey = if (scope == NewsScope.GLOBAL) finnhubKey else marketauxKey
        if (apiKey.isBlank()) {
            return "The ${label(scope)} news API key is missing. Add it to local.properties and rebuild."
        }

        val now = System.currentTimeMillis()
        meta.recordAttempt(scope, now)

        return try {
            val entities: List<NewsEntity> = when (scope) {
                NewsScope.GLOBAL ->
                    finnhub.getMarketNews(apiKey = finnhubKey)
                        .mapNotNull { it.toNewsEntity(scope, now) }
                        .sortedByDescending { it.publishedAt }
                        .take(policy.keep)

                NewsScope.LOCAL ->
                    marketaux.getNews(apiKey = marketauxKey, countries = LOCAL_COUNTRY)
                        .data.orEmpty()
                        .mapNotNull { it.toNewsEntity(scope, now) }
            }

            // An empty response never wipes the cache.
            if (entities.isNotEmpty()) {
                dao.upsertAndPrune(scope.name, entities, policy.keep)
            }
            meta.recordSuccess(scope, now)
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            describe(scope, e)
        }
    }

    private fun label(scope: NewsScope): String =
        if (scope == NewsScope.GLOBAL) "global" else "local"

    private fun describe(scope: NewsScope, e: Exception): String {
        val label = label(scope)
        return when (e) {
            is HttpException -> when (e.code()) {
                401, 403 -> "The $label news service rejected the API key."
                402 -> "The daily $label news limit is used up. Showing saved stories."
                429 -> "Too many news requests. Showing saved stories."
                in 500..599 -> "The $label news service is having problems. Showing saved stories."
                else -> "Couldn't refresh $label news (error ${e.code()})."
            }
            is IOException -> "You appear to be offline. Showing saved stories."
            else -> "Couldn't refresh $label news."
        }
    }
}