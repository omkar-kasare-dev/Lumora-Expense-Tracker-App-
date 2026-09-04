package com.finance.lumora.di


import com.finance.lumora.data.remote.ai.GeminiServiceImpl
import com.finance.lumora.domain.repository.GeminiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeminiModule {

    @Binds
    @Singleton
    abstract fun bindGeminiService(
        implementation: GeminiServiceImpl
    ): GeminiService
}