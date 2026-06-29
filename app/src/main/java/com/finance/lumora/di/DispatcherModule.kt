package com.finance.lumora.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provides Coroutine Dispatchers for dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    /**
     * Provides IO Dispatcher.
     */
    @Provides
    @Singleton
    @Named("IoDispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    /**
     * Provides Default Dispatcher.
     */
    @Provides
    @Singleton
    @Named("DefaultDispatcher")
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    /**
     * Provides Main Dispatcher.
     */
    @Provides
    @Singleton
    @Named("MainDispatcher")
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}