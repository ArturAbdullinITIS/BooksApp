package com.example.domain.repository

import com.example.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookLocalRepository {
    suspend fun saveBookToFavourites(book: Book)
    fun getAllFavourites(): Flow<List<Book>>
}