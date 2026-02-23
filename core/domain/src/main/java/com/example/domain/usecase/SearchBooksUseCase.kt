package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String): Result<List<Book>> {
        return repository.searchBooks(query)
    }
}