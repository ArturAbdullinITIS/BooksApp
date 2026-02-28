package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.repository.BookLocalRepositoryImpl
import com.example.data.local.room.BooksDao
import com.example.data.local.room.Database
import com.example.domain.repository.BookLocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {

    @Binds
    @Singleton
    fun bindBookLocalRepository(
        impl: BookLocalRepositoryImpl
    ) : BookLocalRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): Database {
            return Room.databaseBuilder(
                context = context,
                klass = Database::class.java,
                name = "books.db"
            )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }

        @Provides
        @Singleton
        fun provideBooksDao(
            database: Database
        ): BooksDao {
            return database.booksDao()
        }
    }


}