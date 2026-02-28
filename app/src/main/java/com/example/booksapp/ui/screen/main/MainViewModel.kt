package com.example.booksapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.R
import com.example.booksapp.ui.screen.main.MainState.*
import com.example.booksapp.util.ResourceProvider
import com.example.domain.model.Book
import com.example.domain.usecase.GetAllFavouritesUseCase
import com.example.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList
import kotlin.collections.listOf

private const val PAGE_SIZE = 20

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val resourceProvider: ResourceProvider,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(Initial)
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val favouriteBooks = getAllFavouritesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )



    private var currentQuery = ""
    private var currentPage = 0
    private var isLoadingMore = false
    private var hasMorePages = true
    private val allBooks = mutableListOf<Book>()

    fun processCommand(command: MainCommand) {
        when (command) {
            is MainCommand.SearchBooks -> {
                currentQuery = command.query
                currentPage = 0
                hasMorePages = true
                allBooks.clear()
                loadBooks(isFirstPage = true)
            }
            MainCommand.LoadNextPage -> {
                if (!isLoadingMore && hasMorePages) {
                    loadBooks(isFirstPage = false)
                }
            }
            MainCommand.ClearInput -> {
                _query.update { "" }
            }
            is MainCommand.InputQuery -> {
                _query.update { command.query }
            }
        }
    }

    private fun loadBooks(isFirstPage: Boolean) {
        viewModelScope.launch {
            if (isFirstPage) {
                _state.update { Searching }
            } else {
                val current = _state.value
                if (current is Success) {
                    _state.update { current.copy(isLoadingNextPage = true) }
                }
            }

            if (currentQuery.isBlank()) {
                allBooks.clear()
                _state.update { Success(books = emptyList()) }
                return@launch
            }

            isLoadingMore = true
            val startIndex = currentPage * PAGE_SIZE
            val result = searchBooksUseCase(currentQuery, startIndex, PAGE_SIZE)

            if (result.isSuccess) {
                val newBooks = result.getOrDefault(emptyList())
                if (newBooks.size < PAGE_SIZE) hasMorePages = false
                if (newBooks.isNotEmpty()) {
                    currentPage++
                    allBooks.addAll(newBooks)
                }
                _state.update {
                    Success(
                        books = allBooks.toList(),
                        isLoadingNextPage = false,
                        hasMorePages = hasMorePages
                    )
                }
            } else {
                _state.update {
                    Error(
                        result.exceptionOrNull()?.message
                            ?: resourceProvider.getString(R.string.unknown_error)
                    )
                }
            }
            isLoadingMore = false
        }
    }
}

sealed interface MainCommand {
    data class InputQuery(val query: String) : MainCommand
    object ClearInput : MainCommand
    data class SearchBooks(val query: String) : MainCommand
    object LoadNextPage : MainCommand
}

sealed class MainState {
    object Initial : MainState()
    object Searching : MainState()
    data class Success(
        val books: List<Book>,
        val isLoadingNextPage: Boolean = false,
        val hasMorePages: Boolean = true
    ) : MainState()
    data class Error(val message: String) : MainState()
}