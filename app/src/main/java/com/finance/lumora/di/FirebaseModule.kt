package com.finance.lumora.di


import com.finance.lumora.data.remote.auth.FirebaseAuthDataSource
import com.finance.lumora.data.remote.auth.FirestoreUserDataSource
import com.finance.lumora.data.repository.AuthRepositoryImpl
import com.finance.lumora.data.repository.UserRepositoryImpl
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /**
     * Firebase Authentication
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()

    }

    /**
     * Cloud Firestore
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {

        return FirebaseFirestore.getInstance()

    }

    /**
     * Auth Repository
     */
    /*
    @Provides
    @Singleton
    fun provideAuthRepository(

        dataSource: FirebaseAuthDataSource

    ): AuthRepository {

        return AuthRepositoryImpl(dataSource)

    }

     */

    /**
     * User Repository
     */
    @Provides
    @Singleton
    fun provideUserRepository(

        dataSource: FirestoreUserDataSource

    ): UserRepository {

        return UserRepositoryImpl(dataSource)

    }

}