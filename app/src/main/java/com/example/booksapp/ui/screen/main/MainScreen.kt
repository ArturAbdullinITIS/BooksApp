package com.example.booksapp.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.booksapp.R

@Composable
fun MainScreen(
    onNavigateToDetails: (String) -> Unit
) {
    MainContent(onNavigateToDetails)
}


@Composable
private fun MainContent(
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchField(
                value = query,
                onValueChange = {
                    viewModel.processCommand(MainCommand.InputQuery(it))
                },
                onSearch = {
                    viewModel.processCommand(MainCommand.SearchBooks(query))
                }
            )
            when(val currentState = state) {
                is MainState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.testTag("error_message"),
                            text = currentState.message,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                MainState.Initial -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.testTag("initial_message"),
                            text = stringResource(R.string.what_book_are_you_interested_in),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                MainState.Searching -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("progress_indicator")
                        )
                    }
                }
                is MainState.Success -> {
                    if(currentState.books.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.testTag("books_list")
                        ) {
                            itemsIndexed(
                                items = currentState.books,
                                key = { index, book -> book.id }
                            ) { index, book ->
                                BookItem(
                                    title = book.title,
                                    authors = book.authors,
                                    thumbnail = book.thumbnail,
                                    pageCount = book.pageCount,
                                    averageRating = book.averageRating,
                                    onClick = {
                                        onNavigateToDetails(book.id)
                                    }
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.testTag("nothing_found_message"),
                                text = stringResource(R.string.nothing_found),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}