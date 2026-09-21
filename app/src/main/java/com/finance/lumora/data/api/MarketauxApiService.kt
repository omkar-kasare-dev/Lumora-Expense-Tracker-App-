package com.finance.lumora.data.api

import com.finance.lumora.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketauxApiService {
    @GET("v1/news/all")
    suspend fun getNews(
        @Query("api_token") apiKey: String,
        @Query("countries") countries: String? = null, // e.g. "in" for local, null/omit for global
        @Query("filter_entities") filterEntities: Boolean = true,
        @Query("language") language: String = "en",
        @Query("limit") limit: Int = 3 // free tier cap
    ): NewsResponseDto
}