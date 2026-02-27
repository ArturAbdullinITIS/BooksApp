package com.example.booksapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.booksapp.ui.screen.details.DetailsScreen
import com.example.booksapp.ui.screen.main.MainScreen

@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier
) {
    val backStack = rememberSaveable {
        mutableStateListOf<Route>(Main)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            if(backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<Main> {
                MainScreen(
                    onNavigateToDetails = { bookId ->
                        backStack.add(Details(bookId))
                    }
                )
            }
            entry<Details> { route ->
                DetailsScreen(
                    bookId = route.bookId,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                )
            }
        }
    )
}