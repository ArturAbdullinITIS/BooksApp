package com.example.booksapp.ui.screen.details

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.booksapp.R


@Composable
fun DetailsScreen(bookId: String, onBack: () -> Unit) {
    DetailsContent(bookId = bookId, onBack = onBack)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsContent(
    modifier: Modifier = Modifier,
    bookId: String,
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(
        creationCallback = { factory: DetailsViewModel.Factory ->
            factory.create(bookId = bookId)
        }
    )
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Book Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            modifier = Modifier.clickable(
                                onClick = onBack
                            ),
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.arrow_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else if(state.errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.errorMessage?: "Unknown Error",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if(state.bookItem != null) {
                BookImage(
                    imageUrl = state.bookItem?.thumbnail
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.bookItem?.title ?: "Unknown Title",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.bookItem?.authors?.joinToString(", ") ?: "Unknown Author",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.bookItem?.description ?: "Unknown Descriptions",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}