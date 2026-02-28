package com.example.data.mapper

import com.example.data.local.BookDataModel
import com.example.data.remote.dto.BookResponse
import com.example.domain.model.Book

fun BookResponse.toDomainModel(): Book {
    return Book(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors,
        description = this.volumeInfo.description,
        thumbnail = volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://") ?: "",
        smallThumbnail = this.volumeInfo.imageLinks?.smallThumbnail ?: "",
        pageCount = this.volumeInfo.pageCount,
        averageRating = this.volumeInfo.averageRating,
    )
}

fun Book.toDataModel(): BookDataModel {
    return BookDataModel(
        id = id,
        title = title,
        authors = authors?.joinToString(", "),
        description = description,
        thumbnailUrl = thumbnail,
        pageCount = pageCount,
    )
}

fun BookDataModel.toDomainModel(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors?.split(", "),
        description = description,
        thumbnail = thumbnailUrl,
        smallThumbnail = thumbnailUrl,
        pageCount = pageCount,
        averageRating = null
    )
}