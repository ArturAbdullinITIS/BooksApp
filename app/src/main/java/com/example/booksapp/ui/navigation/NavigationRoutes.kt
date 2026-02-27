package com.example.booksapp.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface Route

@Serializable
data object Main: Route

@Serializable
data class Details(
    val bookId: String
): Route