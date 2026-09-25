package com.finance.lumora.data.api

import com.finance.lumora.data.remote.dto.MarketauxNewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketauxApiService {

    /**
     * GET https://api.marketaux.com/v1/news/all
     *
     * - `countries` filters by the exchange country of entities found in the article
     *   (e.g. "in" = articles that mention Indian-listed entities).
     * - `limit` is intentionally omitted: Marketaux defaults it to the maximum
     *   allowed by your plan, so an upgrade needs no code change.
     * - Errors: 402 = daily quota reached, 429 = rate limited, 401 = bad token.
     */
    @GET("v1/news/all")
    suspend fun getNews(
        @Query("api_token") apiKey: String,
        @Query("countries") countries: String? = null,
        @Query("language") language: String = "en"
    ): MarketauxNewsResponseDto
}