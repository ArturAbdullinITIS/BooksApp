package com.example.data.local.repository

import android.R.attr.description
import com.example.data.local.BookDataModel
import com.example.data.local.room.BooksDao
import com.example.data.mapper.toDataModel
import com.example.domain.model.Book
import com.example.domain.repository.BookLocalRepository
import javax.inject.Inject

class BookLocalRepositoryImpl @Inject constructor(
    private val booksDao: BooksDao
): BookLocalRepository {
    override suspend fun saveBookToFavourites(book: Book) {
        val bookDataModel = book.toDataModel()
        booksDao.saveBookToFavourites(bookDataModel)
    }
}