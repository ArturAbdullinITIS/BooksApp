package com.example.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.data.local.BookDataModel
import com.example.domain.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BooksDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveBookToFavourites(bookDataModel: BookDataModel)


    @Query("Select * from books")
    fun getAllFavourites(): Flow<List<BookDataModel>>
}