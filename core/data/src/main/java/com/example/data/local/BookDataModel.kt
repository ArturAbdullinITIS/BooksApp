package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookDataModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val pageCount: Int
)