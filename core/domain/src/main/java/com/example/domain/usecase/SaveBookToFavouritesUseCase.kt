package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookLocalRepository
import javax.inject.Inject

class SaveBookToFavouritesUseCase @Inject constructor(
    private val repository: BookLocalRepository
) {
    suspend operator fun invoke(book: Book) {
        repository.saveBookToFavourites(book)
    }
}