package com.finance.lumora.di

import com.finance.lumora.data.ocr.AndroidReceiptImagePreprocessor
import com.finance.lumora.domain.ocr.ReceiptImagePreprocessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReceiptImagePreprocessingModule {

    @Binds
    @Singleton
    abstract fun bindReceiptImagePreprocessor(
        implementation: AndroidReceiptImagePreprocessor
    ): ReceiptImagePreprocessor
}