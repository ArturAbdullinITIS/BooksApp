package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavouritesUseCase @Inject constructor(
    private val repository: BookLocalRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getAllFavourites()
    }
}