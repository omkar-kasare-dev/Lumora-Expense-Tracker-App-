package com.finance.lumora.data.api

import com.finance.lumora.data.remote.dto.FinnhubArticleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApiService {

    /**
     * GET https://finnhub.io/api/v1/news?category=general&token=...
     *
     * Categories: general, forex, crypto, merger.
     * Returns a plain JSON array (newest first), so the return type is a List.
     * Free tier: 60 calls/minute. Errors: 401 = bad key, 403 = plan restriction,
     * 429 = rate limited.
     */
    @GET("api/v1/news")
    suspend fun getMarketNews(
        @Query("token") apiKey: String,
        @Query("category") category: String = "general"
    ): List<FinnhubArticleDto>
}