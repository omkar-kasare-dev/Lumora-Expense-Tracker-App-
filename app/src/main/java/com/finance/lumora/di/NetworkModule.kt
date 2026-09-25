package com.finance.lumora.di

import com.finance.lumora.BuildConfig
import com.finance.lumora.data.api.FinnhubApiService
import com.finance.lumora.data.api.MarketauxApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Replaces the old NetworkModule (same file, same object name).
 *
 * News sources:
 *  - Finnhub   -> GLOBAL market news
 *  - Marketaux -> LOCAL (India) news
 *
 * Everything is qualified with @Named so it can't clash with other Retrofit /
 * OkHttp bindings elsewhere in the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("newsHttpClient")
    fun provideNewsHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("marketauxRetrofit")
    fun provideMarketauxRetrofit(
        @Named("newsHttpClient") client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.marketaux.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("finnhubRetrofit")
    fun provideFinnhubRetrofit(
        @Named("newsHttpClient") client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://finnhub.io/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMarketauxApiService(
        @Named("marketauxRetrofit") retrofit: Retrofit
    ): MarketauxApiService = retrofit.create(MarketauxApiService::class.java)

    @Provides
    @Singleton
    fun provideFinnhubApiService(
        @Named("finnhubRetrofit") retrofit: Retrofit
    ): FinnhubApiService = retrofit.create(FinnhubApiService::class.java)

    @Provides
    @Singleton
    @Named("marketauxApiKey")
    fun provideMarketauxApiKey(): String = BuildConfig.MARKETAUX_API_KEY

    @Provides
    @Singleton
    @Named("finnhubApiKey")
    fun provideFinnhubApiKey(): String = BuildConfig.FINNHUB_API_KEY
}