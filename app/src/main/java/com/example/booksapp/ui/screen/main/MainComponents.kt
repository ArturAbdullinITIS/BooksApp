package com.example.booksapp.ui.screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.booksapp.R


@Composable
fun BookItem(
    isSavedToFavourites: Boolean,
    title: String,
    authors: List<String>?,
    thumbnail: String?,
    pageCount: Int,
    averageRating: Double?,
    onClick: () -> Unit
) {
    val imageModel = when {
        thumbnail.isNullOrEmpty() -> R.drawable.ic_no_image_placeholder
        else -> thumbnail
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
            .testTag("book_item"),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = if (isSavedToFavourites) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        } else {
            null
        }

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(120.dp)
        ) {
            AsyncImage(
                model = imageModel,
                contentDescription = "BookPreview",
                modifier = Modifier
                    .size(100.dp, 120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_no_image_placeholder)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = authors?.joinToString(", ") ?: "Unknown Author",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$pageCount" + " pages",
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rating: ${averageRating ?: "N/A"}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Icon(
                        imageVector = if (isSavedToFavourites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isSavedToFavourites) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.go_to_details),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("search_field")
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                    onSearch()
                    true
                } else {
                    false
                }
            },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = stringResource(R.string.search)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.type_book_name)
            )
        },
        trailingIcon = {
            IconButton(
                modifier = Modifier.testTag("search_button"),
                onClick = onSearch,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}
