package com.finance.lumora.di

import com.finance.lumora.BuildConfig
import com.finance.lumora.data.api.MarketauxApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.marketaux.com/") // real Marketaux domain
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMarketauxApiService(retrofit: Retrofit): MarketauxApiService {
        return retrofit.create(MarketauxApiService::class.java)
    }

    // di/NetworkModule.kt (or wherever you put it)
    @Provides
    @Singleton
    @Named("marketauxApiKey")
    fun provideMarketauxApiKey(): String = BuildConfig.MARKETAUX_API_KEY
}