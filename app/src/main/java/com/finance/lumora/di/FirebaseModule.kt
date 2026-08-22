package com.finance.lumora.di


import com.finance.lumora.data.remote.auth.FirestoreUserDataSource
import com.finance.lumora.data.repository.UserRepositoryImpl
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