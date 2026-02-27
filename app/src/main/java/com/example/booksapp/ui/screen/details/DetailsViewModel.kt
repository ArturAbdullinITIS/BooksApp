package com.example.booksapp.ui.screen.details

import android.util.Log.e
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Book
import com.example.domain.usecase.GetBookDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    @Assisted("bookId") private val bookId: String
): ViewModel(){
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("bookId") bookId: String
        ): DetailsViewModel
    }
}

sealed interface DetailsCommand {
}


data class DetailsState(
    val isLoading: Boolean = false,
    val bookItem: Book? = null,
    val errorMessage: String? = null
)