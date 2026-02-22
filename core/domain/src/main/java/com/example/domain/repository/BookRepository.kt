package com.example.domain.repository

import com.example.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>>
    suspend fun getBookDetails(bookId: String): Result<Book>
}