package com.example.booksapp.ui.screen.favourite

import android.R.attr.onClick
import android.R.attr.thumbnail
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.booksapp.ui.navigation.Favourites
import com.example.booksapp.ui.screen.details.DetailsViewModel
import com.example.booksapp.ui.screen.main.BookItem
import com.example.booksapp.ui.screen.main.MainCommand


@Composable
fun FavouriteScreen(

) {
    FavouriteContent()
}


@Composable
fun FavouriteContent(
    modifier: Modifier = Modifier,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (state.books.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.testTag("books_list")
        ) {
            itemsIndexed(
                items = state.books,
                key = { index, book -> "${index}_${book.id}" }
            ) { _, book ->
                FavouriteBookItem(
                    title = book.title,
                    authors = book.authors,
                    thumbnail = book.thumbnail,
                    pageCount = book.pageCount,
                    averageRating = book.averageRating,
                    onClick = {},
                    onLongClick = {
                    }
                )
            }
        }
    }
}