package com.example.booksapp.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomBarRoute(
    val label: String,
    val icon: ImageVector,
    val route: Route
)

val bottomBarRoutes = listOf(
    BottomBarRoute(
        label = "Main Screen",
        icon = Icons.Default.Search,
        route = Main
    ),
    BottomBarRoute(
        label = "Favourites Screen",
        icon = Icons.Default.Favorite,
        route = Favourites
    )
)

@Composable
fun BottomNavBar(
    currentRoute: Route,
    onSelected: (Route) -> Unit
) {
    NavigationBar {
        bottomBarRoutes.forEach { item ->
            NavigationBarItem(
                selected = currentRoute::class == item.route::class,
                onClick = {
                    onSelected(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}