package com.example.domain.model

import kotlinx.coroutines.internal.OpDescriptor

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLink: String?,
    val pageCount: Integer,
    val averageRating: Double?
)