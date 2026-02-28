package com.example.domain.repository

import com.example.domain.model.Book

interface BookLocalRepository {
    suspend fun saveBookToFavourites(book: Book)
}