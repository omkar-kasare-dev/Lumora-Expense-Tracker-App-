package com.finance.lumora.di

import com.finance.lumora.data.voice.AndroidVoiceInputProcessor
import com.finance.lumora.domain.voice.VoiceInputProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VoiceInputModule {

    @Binds
    @Singleton
    abstract fun bindVoiceInputProcessor(
        implementation: AndroidVoiceInputProcessor
    ): VoiceInputProcessor
}