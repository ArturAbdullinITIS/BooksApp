package com.example.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.BookDataModel


@Database(
    entities = [BookDataModel::class],
    version = 2,
    exportSchema = false
)
abstract class Database:  RoomDatabase(){
    abstract fun booksDao(): BooksDao
}