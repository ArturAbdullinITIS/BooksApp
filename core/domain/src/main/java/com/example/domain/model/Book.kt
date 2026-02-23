package com.example.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val thumbnail: String?,
    val smallThumbnail: String?,
    val pageCount: Int,
    val averageRating: Double?
)