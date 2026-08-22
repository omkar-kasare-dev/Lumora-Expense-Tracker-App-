package com.finance.lumora.di

import android.content.Context
import androidx.room.Room
import com.finance.lumora.data.local.dao.CategoryDao
import com.finance.lumora.data.local.dao.SubCategoryDao
import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.database.LumoraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideLumoraDatabase(
        @ApplicationContext context: Context
    ): LumoraDatabase {

        return Room.databaseBuilder(
            context,
            LumoraDatabase::class.java,
            "lumora_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCategoryDao(
        database: LumoraDatabase
    ): CategoryDao {

        return database.categoryDao()
    }


    @Provides
    @Singleton
    fun provideTransactionDao(
        database: LumoraDatabase
    ): TransactionDao {

        return database.transactionDao()
    }

    @Provides
    fun provideSubCategoryDao(
        database: LumoraDatabase
    ): SubCategoryDao = database.subCategoryDao()

}