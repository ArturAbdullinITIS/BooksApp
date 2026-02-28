package com.example.booksapp.ui.screen.details

import android.R.attr.fontWeight
import android.R.attr.text
import android.graphics.Paint
import android.graphics.fonts.Font
import android.net.TetheringManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksapp.R
import kotlinx.coroutines.flow.compose
import kotlin.math.acos


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
        key = bookId,
        creationCallback = { factory: DetailsViewModel.Factory ->
            factory.create(bookId = bookId)
        }
    )
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val favouriteBooks by viewModel.favouriteBooks.collectAsState()
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
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.errorMessage ?: stringResource(R.string.unknown_error),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (state.bookItem != null) {
                state.bookItem?.let { book ->
                    BookImage(
                        imageUrl = book.thumbnail
                    )
                    Text(
                        modifier = Modifier.testTag("title"),
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.testTag("authors"),
                        text = book.authors?.joinToString(", ")
                            ?: stringResource(R.string.unknown_author),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.testTag("description"),
                        text = book.description ?: stringResource(R.string.no_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    AnimatedButton(
                        isFavourite = favouriteBooks.any { it.id == bookId },
                        onAdd = {
                            viewModel.processCommand(DetailsCommand.SaveBookToFavourites(book))
                        },
                        onDelete = {
                            viewModel.processCommand(DetailsCommand.DeleteBookFromFavourites(bookId))
                        }
                    )
                }
            }
        }
    }
}