package com.finance.lumora.di

import com.finance.lumora.data.ocr.MlKitReceiptOcrProcessor
import com.finance.lumora.domain.ocr.ReceiptOcrProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReceiptOcrModule {

    @Binds
    @Singleton
    abstract fun bindReceiptOcrProcessor(
        implementation: MlKitReceiptOcrProcessor
    ): ReceiptOcrProcessor
}