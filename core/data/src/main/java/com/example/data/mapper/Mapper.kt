package com.example.data.mapper

import com.example.data.remote.dto.BookResponse
import com.example.domain.model.Book

fun BookResponse.toDomainModel(): Book {
    return Book(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors,
        description = this.volumeInfo.description,
        thumbnail = this.volumeInfo.imageLinks?.thumbnail ?: "",
        smallThumbnail = this.volumeInfo.imageLinks?.smallThumbnail ?: "",
        pageCount = this.volumeInfo.pageCount,
        averageRating = this.volumeInfo.averageRating,
    )
}