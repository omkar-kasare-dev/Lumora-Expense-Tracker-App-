package com.finance.lumora.di

import com.finance.lumora.data.remote.auth.FirebaseAuthDataSource
import com.finance.lumora.data.repository.AuthRepositoryImpl
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.usecase.auth.AuthUseCases
import com.finance.lumora.domain.usecase.auth.GetCurrentUserUseCase
import com.finance.lumora.domain.usecase.auth.LoginUseCase
import com.finance.lumora.domain.usecase.auth.LogoutUseCase
import com.finance.lumora.domain.usecase.auth.ObserveAuthStateUseCase
import com.finance.lumora.domain.usecase.auth.ChangePasswordUseCase
import com.finance.lumora.domain.usecase.auth.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    /**
     * Firebase Auth Data Source
     */
    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(

        firebaseAuth: FirebaseAuth,

        firestore: FirebaseFirestore

    ): FirebaseAuthDataSource {

        return FirebaseAuthDataSource(

            firebaseAuth,

            firestore

        )

    }

    /**
     * Authentication Repository
     */
    @Provides
    @Singleton
    fun provideAuthRepository(

        dataSource: FirebaseAuthDataSource

    ): AuthRepository {

        return AuthRepositoryImpl(

            dataSource

        )

    }

    /**
     * Authentication UseCases
     */
    @Provides
    @Singleton
    fun provideAuthUseCases(

        repository: AuthRepository

    ): AuthUseCases {

        return AuthUseCases(

            login = LoginUseCase(repository),

            register = RegisterUseCase(repository),

            logout = LogoutUseCase(repository),

            getCurrentUser = GetCurrentUserUseCase(repository),

            observeAuthState = ObserveAuthStateUseCase(repository),

            changePassword = ChangePasswordUseCase(repository)

        )

    }

}