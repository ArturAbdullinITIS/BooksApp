package com.example.booksapp.ui.screen.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.example.booksapp.R
import com.example.booksapp.ui.screen.main.MainState.*
import com.example.booksapp.util.ResourceProvider
import com.example.domain.model.Book
import com.example.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel(){
    private val _state = MutableStateFlow<MainState>(MainState.Initial)
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun processCommand(command: MainCommand) {
        when(command) {
            is MainCommand.SearchBooks -> {
                viewModelScope.launch {
                    _state.update {
                        Searching
                    }
                    if (_query.value.isBlank()) {
                        _state.update {
                            Success(emptyList())
                        }
                        return@launch
                    }
                    val result = searchBooksUseCase(command.query)
                    if(result.isSuccess) {
                        _state.update {
                            Success(result.getOrDefault(emptyList()))
                        }
                    } else {
                        _state.update {
                            Error(result.exceptionOrNull()?.message ?: resourceProvider.getString(R.string.unknown_error))
                        }
                    }
                }
            }

            MainCommand.ClearInput -> {
                _query.update {
                    ""
                }
            }
            is MainCommand.InputQuery -> {
                _query.update {
                    command.query
                }
            }
        }
    }
}


sealed interface MainCommand {
    data class InputQuery(val query: String): MainCommand
    object ClearInput: MainCommand
    data class SearchBooks(val query: String): MainCommand

}

sealed class MainState {
    object Initial: MainState()
    object Searching: MainState()
    data class Success(val books: List<Book>): MainState()
    data class Error(val message: String): MainState()
}
