package com.example.booksapp.ui.screen.details

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.booksapp.R

@Composable
fun BookImage(
    imageUrl: String?,
) {
    AsyncImage(
        modifier = Modifier.size(200.dp),
        model = imageUrl,
        contentDescription = "Book Image",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_no_image_placeholder)
    )
}