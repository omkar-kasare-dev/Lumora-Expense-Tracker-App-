package com.finance.lumora.data.repository

import com.finance.lumora.BuildConfig
import com.finance.lumora.data.api.MarketauxApiService
import com.finance.lumora.data.local.dao.NewsDao
import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.NewsScope
import com.finance.lumora.domain.repository.NewsRepository
import kotlinx.coroutines.flow.emitAll

import javax.inject.Inject
import com.finance.lumora.domain.model.Result
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val api: MarketauxApiService,
    private val dao: NewsDao,
    @Named("marketauxApiKey") private val apiKey: String
) : NewsRepository {

    companion object { private const val CACHE_TTL_MS = 30 * 60 * 1000L } // 30 min

    override fun getFinanceNews(scope: NewsScope, forceRefresh: Boolean) = flow {
        emit(Result.Loading)
        val scopeKey = scope.name
        val lastFetch = dao.lastFetchTime(scopeKey) ?: 0L
        val isStale = System.currentTimeMillis() - lastFetch > CACHE_TTL_MS

        if (isStale || forceRefresh) {
            try {
                val country = if (scope == NewsScope.LOCAL) "in" else null
                val response = api.getNews(apiKey = BuildConfig.MARKETAUX_API_KEY, countries = country)
                val entities = response.data.map { it.toEntity(scopeKey) }
                dao.clearScope(scopeKey)
                dao.insertAll(entities)
            } catch (e: Exception) {
                // Network failed — fall through to whatever's cached, don't crash the flow
            }
        }
        emitAll(dao.observeNews(scopeKey).map { list ->
            Result.Success(list.map { it.toDomain() })
        })
    }
}