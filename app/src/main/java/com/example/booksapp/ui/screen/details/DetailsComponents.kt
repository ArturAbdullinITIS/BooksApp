package com.example.booksapp.ui.screen.details

import android.R.attr.onClick
import android.widget.Button
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.bitmapConfig
import coil3.request.crossfade
import coil3.size.Precision
import coil3.size.Scale
import com.example.booksapp.R

@Composable
fun BookImage(
    imageUrl: String?,
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp),
        model = imageUrl,
        contentDescription = "Book Image",
        contentScale = ContentScale.Fit,
        placeholder = painterResource(R.drawable.ic_no_image_placeholder)
    )
}


@Composable
fun AnimatedButton(
    isFavourite: Boolean,
    onAdd: () -> Unit,
    onDelete: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if(isFavourite) MaterialTheme.colorScheme.error.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "bg_color",
    )
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor
        ),
        onClick = {
            if (isFavourite) {
                onDelete()
            } else {
                onAdd()
            }
        }
    ) {
        Icon(
            imageVector = if(isFavourite) Icons.Default.Clear else Icons.Default.Add,
            contentDescription = stringResource(R.string.add_to_favourites_or_delete_button)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if(isFavourite) stringResource(R.string.delete_from_favourites) else
                stringResource(R.string.add_book_to_favourites)
        )
    }

}