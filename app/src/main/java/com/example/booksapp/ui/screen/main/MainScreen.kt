package com.example.booksapp.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MainScreen() {
    MainContent()
}


@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()
    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchField(
                value = query,
                onValueChange = {
                    viewModel.processCommand(MainCommand.InputQuery(it))
                },
                onClearIconClick = {
                    viewModel.processCommand(MainCommand.ClearInput)
                },
                onSearch = {
                    viewModel.processCommand(MainCommand.SearchBooks(query))
                }
            )
            when(val currentState = state) {
                is MainState.Error -> {
                    Text(
                        text = "error",
                    )
                }
                MainState.Initial -> {
                    Text(
                        text = "Input query",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                MainState.Searching -> {
                    CircularProgressIndicator()
                }
                is MainState.Success -> {
                    LazyColumn {
                        items(
                            items = currentState.books,
                            key = { book -> book.id }
                        ) { book ->
                            BookItem(
                                title = book.title,
                                authors = book.authors,
                                thumbnail = book.thumbnail,
                                pageCount = book.pageCount,
                                averageRating = book.averageRating
                            )
                        }
                    }
                }
            }
        }
    }
}