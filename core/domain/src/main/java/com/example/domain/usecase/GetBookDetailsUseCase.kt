package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: String): Result<Book> {
        return repository.getBookDetails(bookId)
    }
}