package com.example.booksapp.ui.screen.favourite

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Book
import com.example.domain.usecase.GetAllFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()

    init {
        _state.update { state ->
            state.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            getAllFavouritesUseCase().collect { books ->
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        books = books
                    )
                }

            }

        }
    }
}




data class FavouriteState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList()
)