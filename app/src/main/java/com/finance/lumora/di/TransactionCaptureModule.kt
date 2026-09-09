package com.finance.lumora.di

import com.finance.lumora.data.ai.TransactionCaptureParserImpl
import com.finance.lumora.domain.model.ai.TransactionCaptureParser

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionCaptureModule {

    @Binds
    @Singleton
    abstract fun bindTransactionCaptureParser(
        implementation: TransactionCaptureParserImpl
    ): TransactionCaptureParser
}