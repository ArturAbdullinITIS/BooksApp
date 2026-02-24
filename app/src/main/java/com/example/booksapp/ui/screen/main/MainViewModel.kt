package com.example.booksapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Book
import com.example.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
): ViewModel(){
    private val _state = MutableStateFlow<MainState>(MainState.Initial)

    fun processCommand(command: MainCommand) {
        when(command) {
            is MainCommand.SearchBooks -> {
                viewModelScope.launch {
                    _state.update {
                        MainState.Searching
                    }
                    val result = searchBooksUseCase(command.query)
                    if(result.isSuccess) {
                        _state.update {
                            MainState.Success(result.getOrDefault(emptyList()))
                        }
                    } else {
                        _state.update {
                            MainState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                        }
                    }
                }
            }
        }
    }
}


sealed interface MainCommand {
    data class SearchBooks(val query: String): MainCommand
}

sealed class MainState {
    object Initial: MainState()
    object Searching: MainState()
    data class Success(val books: List<Book>): MainState()
    data class Error(val message: String): MainState()
}
