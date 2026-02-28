package com.example.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.data.local.BookDataModel


@Dao
interface BooksDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveBookToFavourites(bookDataModel: BookDataModel)
}