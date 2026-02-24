package com.example.data.di

import com.example.data.remote.repository.BookRepositoryImpl
import com.example.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindBookRepository(
        impl: BookRepositoryImpl
    ): BookRepository
}