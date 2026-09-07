package com.finance.lumora.di



import com.finance.lumora.data.repository.AurixFinanceRepositoryImpl
import com.finance.lumora.domain.repository.AurixFinanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AurixFinanceModule {

    @Binds
    @Singleton
    abstract fun bindAurixFinanceRepository(
        implementation: AurixFinanceRepositoryImpl
    ): AurixFinanceRepository
}