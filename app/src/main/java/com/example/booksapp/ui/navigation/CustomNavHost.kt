package com.example.booksapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.booksapp.ui.screen.details.DetailsScreen
import com.example.booksapp.ui.screen.favourite.FavouriteScreen
import com.example.booksapp.ui.screen.main.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier
) {
    val backStack = rememberSaveable {
        mutableStateListOf<Route>(Main)
    }

    val currentRoute = backStack.lastOrNull() ?: Main

    val isBottomBarVisible = currentRoute is Main || currentRoute is Favourites
    Scaffold(
        bottomBar = {
            if(isBottomBarVisible) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onSelected = { selectedRoute ->
                        backStack.clear()
                        backStack.add(selectedRoute)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = modifier.padding(innerPadding),
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
                entry<Favourites> { route ->
                    FavouriteScreen()
                }
            }
        )
    }

}
