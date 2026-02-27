package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import javax.inject.Inject
import kotlin.math.max

class SearchBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(
        query: String,
        startIndex: Int = 0,
        maxResults: Int = 20
    ): Result<List<Book>> {
        return repository.searchBooks(query, startIndex, maxResults)
    }
}