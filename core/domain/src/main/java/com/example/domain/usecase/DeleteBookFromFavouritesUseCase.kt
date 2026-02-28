package com.example.domain.usecase

import com.example.domain.repository.BookLocalRepository
import javax.inject.Inject

class DeleteBookFromFavouritesUseCase @Inject constructor(
    private val repository: BookLocalRepository
) {
    suspend operator fun invoke(bookId: String) {
        repository.deleteBookFromFavourites(bookId)
    }
}