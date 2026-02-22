package com.example.data.mapper

import android.R.attr.description
import com.example.data.remote.dto.BookResponse
import com.example.data.remote.dto.VolumeInfoResponse
import com.example.domain.model.Book

fun BookResponse.toDomainModel(): Book {
    return Book(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors,
        description = this.volumeInfo.description,
        imageLink = this.volumeInfo.imageLinks?.thumbnail ?: "",
        pageCount = this.volumeInfo.pageCount,
        averageRating = this.volumeInfo.averageRating,
    )
}