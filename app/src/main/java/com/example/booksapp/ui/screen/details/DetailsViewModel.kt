package com.example.booksapp.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Book
import com.example.domain.usecase.DeleteBookFromFavouritesUseCase
import com.example.domain.usecase.GetAllFavouritesUseCase
import com.example.domain.usecase.GetBookDetailsUseCase
import com.example.domain.usecase.SaveBookToFavouritesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val saveBookToFavouritesUseCase: SaveBookToFavouritesUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val deleteBookFromFavouritesUseCase: DeleteBookFromFavouritesUseCase,
    @Assisted("bookId") private val bookId: String
): ViewModel(){
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    val favouriteBooks = getAllFavouritesUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    init {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            val result = getBookDetailsUseCase(bookId)
            if(result.isSuccess) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        bookItem = result.getOrNull()
                    )
                }
            } else {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun processCommand(command: DetailsCommand) {
        when (command) {
            is DetailsCommand.SaveBookToFavourites -> {
                viewModelScope.launch {
                    saveBookToFavouritesUseCase(command.book)
                }
            }

            is DetailsCommand.DeleteBookFromFavourites -> {
                viewModelScope.launch {
                    deleteBookFromFavouritesUseCase(command.bookId)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("bookId") bookId: String
        ): DetailsViewModel
    }
}

sealed interface DetailsCommand {
    data class SaveBookToFavourites(val book: Book): DetailsCommand
    data class DeleteBookFromFavourites(val bookId: String): DetailsCommand
}


data class DetailsState(
    val isLoading: Boolean = false,
    val bookItem: Book? = null,
    val errorMessage: String? = null
)