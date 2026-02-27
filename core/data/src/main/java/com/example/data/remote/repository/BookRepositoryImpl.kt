package com.example.data.remote.repository

import com.example.data.mapper.toDomainModel
import com.example.data.remote.api.BooksApiService
import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import javax.inject.Inject
import kotlin.math.max

class BookRepositoryImpl @Inject constructor(
    private val booksApiService: BooksApiService
): BookRepository{
    override suspend fun searchBooks(
        query: String,
        startIndex: Int,
        maxResults: Int
    ): Result<List<Book>> {
        return try {
            val response = booksApiService.searchBooks(query, maxResults, startIndex)
            val books = response.items?.map { it.toDomainModel() } ?: emptyList()
            Result.success(books)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getBookDetails(bookId: String): Result<Book> {
        return try {
            val response = booksApiService.getBookDetails(bookId)
            val book = response.toDomainModel()
            Result.success(book)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}